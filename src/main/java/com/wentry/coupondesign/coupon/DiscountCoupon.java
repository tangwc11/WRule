package com.wentry.coupondesign.coupon;

import com.wentry.coupondesign.entity.ExcludeType;
import com.wentry.coupondesign.entity.CouponType;

import java.util.Date;
import java.util.List;

/**
 * @Description: 折扣券
 * @Author: tangwc
 */
public class DiscountCoupon extends Coupon {

    /**
     * 满足金额
     * 0代表无条件
     */
    int baseAmount;

    /**
     * 折扣率
     */
    double rate;

    /**
     * 排斥类型
     */
    List<ExcludeType> excludes;

    public DiscountCoupon(String name, Date startDate, Date endDate, int baseAmount, double rate, List<ExcludeType> excludes) {
        super(name, startDate, endDate);
        if (rate >= 1.0d) {
            throw new RuntimeException("rate should between (0,1), curr rate is :" + rate);
        }
        this.baseAmount = baseAmount;
        this.rate = rate;
        this.excludes = excludes;
        setType(CouponType.DISCOUNT);
    }

    public int getBaseAmount() {
        return baseAmount;
    }

    public DiscountCoupon setBaseAmount(int baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }

    public double getRate() {
        return rate;
    }

    public DiscountCoupon setRate(double rate) {
        this.rate = rate;
        return this;
    }

    public List<ExcludeType> getExcludes() {
        return excludes;
    }

    public DiscountCoupon setExcludes(List<ExcludeType> excludes) {
        this.excludes = excludes;
        return this;
    }
}
