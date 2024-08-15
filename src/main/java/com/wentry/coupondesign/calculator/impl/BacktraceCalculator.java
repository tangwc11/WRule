package com.wentry.coupondesign.calculator.impl;

import com.wentry.coupondesign.calculator.Calculator;
import com.wentry.coupondesign.entity.CalculateResult;
import com.wentry.coupondesign.coupon.Coupon;
import com.wentry.coupondesign.coupon.DiscountCoupon;
import com.wentry.coupondesign.coupon.ReductionCoupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 目标：高效的计算最大折扣金额
 * <p>
 * 首先思考一下具体要怎么去匹配
 * 1。 先考虑贪心算法，假如最后的组合为ABC，则券的使用过程一定是A->AB->ABC，能保证A、AB一定是过程中的最优解吗？答案是否定的，所以使用贪心算法不太合适
 * 2。 采用穷举法：每种组合都试一下，保存优惠金额最大的组合，这样好像是可行的，考虑到券数量不会太多，复杂度方面可以忽略
 * 几个使用原则：
 * 1。 优惠金额相同的情况下，能少用券就少用券
 * 2。 优惠金额相同的情况下，快过期的券先用
 * 3。 券的互斥原则要考虑
 * @Author: tangwc
 */
public class BacktraceCalculator implements Calculator {
    @Override
    public CalculateResult calculate(List<Coupon> coupons, int originAmount) {

        List<MiddleRes> res = new ArrayList<>();

        List<Coupon> usedCoupon = new ArrayList<>();
        List<CalculateResult.CouponLog> calculateLog = new ArrayList<>();
        boolean[] used = new boolean[coupons.size()];

        Date now = new Date();
        //回溯填充券
        backtrace(res, coupons, originAmount, usedCoupon, used, calculateLog, now);

        filterRes(res);

        if (res.size() == 0) {
            return null;
        }
        return new CalculateResult()
                .setOriginAmount(originAmount)
                .setFinalAmount(res.get(0).getAmount())
                .setUsedCoupon(res.get(0).getUsedCoupon())
                .setDeductLog(res.get(0).getLog())
                ;
    }

    private void backtrace(List<MiddleRes> res,
                           List<Coupon> coupons, int originAmount,
                           List<Coupon> usedCoupon, boolean[] used,
                           List<CalculateResult.CouponLog> calculateLog, Date now) {

        if (originAmount <= 0) {
            return;
        }

        int argOrigin = originAmount;
        for (int i = 0; i < coupons.size(); i++) {
            if (used[i]) {
                continue;
            }
            Coupon coupon = coupons.get(i);
            if (coupon.getEndDate().before(now)) {
                continue;
            }
            if (canUse(coupon, originAmount, usedCoupon)) {
                used[i] = true;
                usedCoupon.add(coupon);
                int afterAmount = calculateAmount(coupon, originAmount);
                calculateLog.add(generateLog(coupon, originAmount, afterAmount));
                originAmount = afterAmount;

                //记录组合
                res.add(new MiddleRes()
                        .setLog(new ArrayList<>(calculateLog))
                        .setUsedCoupon(new ArrayList<>(usedCoupon))
                        .setAmount(originAmount)
                );
                //加入券，递归
                backtrace(res, coupons, originAmount, usedCoupon, used, calculateLog, now);
                //移除当前券
                usedCoupon.remove(usedCoupon.size() - 1);
                //日志也移除
                calculateLog.remove(calculateLog.size() - 1);
                used[i] = false;
                //金额也要恢复
                originAmount = argOrigin;
            }
        }


    }

    static class MiddleRes {
        int amount;
        List<Coupon> usedCoupon;
        List<CalculateResult.CouponLog> log;

        public int getAmount() {
            return amount;
        }

        public MiddleRes setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public List<Coupon> getUsedCoupon() {
            return usedCoupon;
        }

        public MiddleRes setUsedCoupon(List<Coupon> usedCoupon) {
            this.usedCoupon = usedCoupon;
            return this;
        }

        public List<CalculateResult.CouponLog> getLog() {
            return log;
        }

        public MiddleRes setLog(List<CalculateResult.CouponLog> log) {
            this.log = log;
            return this;
        }
    }

    private CalculateResult.CouponLog generateLog(Coupon coupon, int originAmount, int afterAmount) {
        return new CalculateResult.CouponLog().setCouponName(coupon.getName())
                .setBefore(originAmount)
                .setAfter(afterAmount);
    }

    private int calculateAmount(Coupon coupon, int originAmount) {
        if (coupon instanceof DiscountCoupon) {
            //这里先算整数得了
            return BigDecimal.valueOf(originAmount).multiply(
                            BigDecimal.valueOf(((DiscountCoupon) coupon).getRate())
                    )
                    .intValue()
                    ;
        } else if (coupon instanceof ReductionCoupon) {
            return originAmount - ((ReductionCoupon) coupon).getReductionAmount();
        }
        return 0;
    }

    /**
     * 能够使用：
     * 1。 满足本身的金额
     * 2。 满足互斥条件
     */
    private boolean canUse(Coupon coupon, int originAmount, List<Coupon> usedCoupon) {
        return CouponChecker.meetAmount(coupon, originAmount)
                && CouponChecker.meetExclude(coupon, usedCoupon, true);
    }

    /**
     * 金额相同的，取券少+先到期的
     *
     * @param res
     */
    private void filterRes(List<MiddleRes> res) {
        res.sort((o1, o2) -> {
            if (o1.getAmount() == o2.getAmount()) {
                return o1.getLog().size() - o2.getUsedCoupon().size();
            }
            return o1.getAmount() - o2.getAmount();
        });
        MiddleRes pre = null;

        for (MiddleRes mr : res) {
            //最前的是金额最小的
            if (pre == null) {
                pre = mr;
                continue;
            }
            if (mr.getAmount() > pre.getAmount()) {
                break;
            }

            //同金额，使用券少的
            if (pre.getUsedCoupon().size() > mr.getUsedCoupon().size()) {
                break;
            }

            //同金额，且同券数，保留优先到期的
            if (pre.getUsedCoupon().stream().map(Coupon::getEndDate).min(Date::compareTo).orElse(new Date())
                    .after(
                            mr.getUsedCoupon().stream().map(Coupon::getEndDate).min(Date::compareTo).orElse(new Date())
                    )) {
                pre = mr;
            }
        }
        if (pre == null) {
            return;
        }
        res.clear();
        res.add(pre);
    }
}
