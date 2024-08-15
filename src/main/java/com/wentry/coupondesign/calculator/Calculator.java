package com.wentry.coupondesign.calculator;

import com.wentry.coupondesign.coupon.Coupon;
import com.wentry.coupondesign.entity.CalculateResult;

import java.util.List;

/**
 * @Description:
 * @Author: tangwc
 */
public interface Calculator {

    CalculateResult calculate(List<Coupon> coupons, int originAmount);

}
