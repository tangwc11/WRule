package com.wentry.coupondesign.calculator.impl;

import com.google.common.collect.Lists;
import com.wentry.coupondesign.coupon.Coupon;
import com.wentry.coupondesign.entity.CouponType;
import com.wentry.coupondesign.entity.ExcludeType;
import com.wentry.coupondesign.coupon.DiscountCoupon;
import com.wentry.coupondesign.coupon.ReductionCoupon;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description: 规则校验
 * @Author: tangwc
 */
public class CouponChecker {
    public static boolean meetAmount(Coupon coupon, int originAmount) {

        if (coupon instanceof DiscountCoupon) {
            return originAmount >= ((DiscountCoupon) coupon).getBaseAmount();
        } else if (coupon instanceof ReductionCoupon) {
            return originAmount >= ((ReductionCoupon) coupon).getBaseAmount();
        } else {
            throw new RuntimeException("unknown coupon:" + coupon.getClass());
        }
    }

    public static boolean meetExclude(Coupon coupon, List<Coupon> usedCoupon, boolean reverseCheck) {

        //先反向校验
        if (reverseCheck) {
            ArrayList<Coupon> checkCoupon = Lists.newArrayList(coupon);
            for (Coupon ucp : usedCoupon) {
                if (!meetExclude(ucp, checkCoupon, false)) {
                    return false;
                }
            }
        }

        List<ExcludeType> excludeTypes = new ArrayList<>();
        if (coupon instanceof DiscountCoupon) {
            excludeTypes = ((DiscountCoupon) coupon).getExcludes();
        } else if (coupon instanceof ReductionCoupon) {
            excludeTypes = ((ReductionCoupon) coupon).getExcludes();
        }

        if (excludeTypes == null || excludeTypes.size() == 0) {
            return true;
        }

        Set<CouponType> usedCType = usedCoupon.stream().map(Coupon::getType).collect(Collectors.toSet());
        for (ExcludeType excludeType : excludeTypes) {
            switch (excludeType) {
                case SAME_COUPON:
                    if (usedCType.contains(coupon.getType())) {
                        return false;
                    }
                    break;
                case OTHER_COUPON:
                    for (CouponType otherType : otherTypes(coupon.getType())) {
                        if (usedCType.contains(otherType)) {
                            return false;
                        }
                    }
                    break;
                case DISCOUNT_EXCLUDE:
                    if (usedCType.contains(CouponType.DISCOUNT)) {
                        return false;
                    }
                    break;
                case REDUCTION_EXCLUDE:
                    if (usedCType.contains(CouponType.REDUCTION)) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    //可以cache
    private static List<CouponType> otherTypes(CouponType type) {
        List<CouponType> res = new ArrayList<>();
        for (CouponType value : CouponType.values()) {
            if (value==type) {
                continue;
            }
            res.add(type);
        }
        return res;
    }
}
