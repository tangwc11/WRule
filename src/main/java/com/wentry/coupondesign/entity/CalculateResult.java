package com.wentry.coupondesign.entity;

import com.alibaba.fastjson2.annotation.JSONType;
import com.wentry.coupondesign.coupon.Coupon;

import java.util.List;

/**
 * @Description: 优惠计算结果，记录必要的过程
 * @Author: tangwc
 */
public class CalculateResult {

    List<Coupon> usedCoupon;
    int originAmount;
    List<CouponLog> deductLog;
    int finalAmount;

    @JSONType(orders = {"couponName","before","after"})
    public static class CouponLog{
        String couponName;
        int before;
        int after;

        public String getCouponName() {
            return couponName;
        }

        public CouponLog setCouponName(String couponName) {
            this.couponName = couponName;
            return this;
        }

        public int getBefore() {
            return before;
        }

        public CouponLog setBefore(int before) {
            this.before = before;
            return this;
        }

        public int getAfter() {
            return after;
        }

        public CouponLog setAfter(int after) {
            this.after = after;
            return this;
        }
    }

    public List<Coupon> getUsedCoupon() {
        return usedCoupon;
    }

    public CalculateResult setUsedCoupon(List<Coupon> usedCoupon) {
        this.usedCoupon = usedCoupon;
        return this;
    }

    public int getOriginAmount() {
        return originAmount;
    }

    public CalculateResult setOriginAmount(int originAmount) {
        this.originAmount = originAmount;
        return this;
    }

    public List<CouponLog> getDeductLog() {
        return deductLog;
    }

    public CalculateResult setDeductLog(List<CouponLog> deductLog) {
        this.deductLog = deductLog;
        return this;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public CalculateResult setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
        return this;
    }
}
