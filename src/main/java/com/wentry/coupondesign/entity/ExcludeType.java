package com.wentry.coupondesign.entity;

/**
 * @Description:
 * @Author: tangwc
 */
public enum ExcludeType {

    /**
     * 同类型互斥
     */
    SAME_COUPON,

    /**
     * 其他类型互斥
     */
    OTHER_COUPON,

    /**
     * 指定满减券互斥
     */
    REDUCTION_EXCLUDE,

    /**
     * 指定折扣券互斥
     */
    DISCOUNT_EXCLUDE


}
