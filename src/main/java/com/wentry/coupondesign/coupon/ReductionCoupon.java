package com.wentry.coupondesign.coupon;

import com.wentry.coupondesign.entity.CouponType;
import com.wentry.coupondesign.entity.ExcludeType;

import java.util.Date;
import java.util.List;

/**
 * @Description: 满减券
 * @Author: tangwc
 */
public class ReductionCoupon extends Coupon {

    /**
     * 满足金额
     */
    int baseAmount;

    /**
     * 减去金额
     */
    int reductionAmount;

    /**
     * 排斥类型
     */
    List<ExcludeType> excludes;

    public ReductionCoupon(String name, Date startDate, Date endDate,
                           int baseAmount, int reductionAmount, List<ExcludeType> excludes) {
        super(name, startDate, endDate);
        this.baseAmount = baseAmount;
        this.reductionAmount = reductionAmount;
        this.excludes = excludes;
        setType(CouponType.REDUCTION);
    }

    public int getBaseAmount() {
        return baseAmount;
    }

    public ReductionCoupon setBaseAmount(int baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }

    public int getReductionAmount() {
        return reductionAmount;
    }

    public ReductionCoupon setReductionAmount(int reductionAmount) {
        this.reductionAmount = reductionAmount;
        return this;
    }

    public List<ExcludeType> getExcludes() {
        return excludes;
    }

    public ReductionCoupon setExcludes(List<ExcludeType> excludes) {
        this.excludes = excludes;
        return this;
    }
}
