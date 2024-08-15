package com.wentry.coupondesign.coupon;

import com.wentry.coupondesign.entity.CouponType;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: tangwc
 */
public abstract class Coupon implements Serializable {

    //名称
    String name;
    //开始日期
    Date startDate;
    //结束日期
    Date endDate;
    //类型
    CouponType type;

    public Coupon(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public CouponType getType() {
        return type;
    }

    protected Coupon setType(CouponType type) {
        this.type = type;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Coupon setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Coupon setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public Coupon setName(String name) {
        this.name = name;
        return this;
    }
}
