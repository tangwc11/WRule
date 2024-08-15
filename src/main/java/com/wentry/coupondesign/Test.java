package com.wentry.coupondesign;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.google.common.collect.Lists;
import com.wentry.coupondesign.calculator.impl.BacktraceCalculator;
import com.wentry.coupondesign.coupon.DiscountCoupon;
import com.wentry.coupondesign.entity.ExcludeType;
import com.wentry.coupondesign.coupon.ReductionCoupon;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @Description:
 * @Author: tangwc
 */
public class Test {


    public static void main(String[] args) {
        BacktraceCalculator backtraceCalculator = new BacktraceCalculator();

        System.out.println(JSON.toJSONString(backtraceCalculator.calculate(
                Lists.newArrayList(
                        new ReductionCoupon("满100-20(仅限使用一张)", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList(ExcludeType.REDUCTION_EXCLUDE)),
                        new ReductionCoupon("满100-20(不与折扣共享)", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList(ExcludeType.DISCOUNT_EXCLUDE)),
                        new ReductionCoupon("满100-10（无限制）", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList()),
                        new ReductionCoupon("满100-10（无限制）", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList()),
                        new DiscountCoupon("8折券(仅限使用一张)", new Date(), DateTime.now().plusDays(1).toDate(), 100, 0.8d, Lists.newArrayList(ExcludeType.DISCOUNT_EXCLUDE)),
                        new DiscountCoupon("8.5折券(不与满减共享)", new Date(), DateTime.now().plusDays(1).toDate(), 100, 0.85d, Lists.newArrayList(ExcludeType.REDUCTION_EXCLUDE)),
                        new DiscountCoupon("9折券", new Date(), DateTime.now().plusDays(1).toDate(), 1000, .9d, Lists.newArrayList()),
                        new DiscountCoupon("9.5折券", new Date(), DateTime.now().plusDays(1).toDate(), 950, .95d, Lists.newArrayList())
                ),
                1000
        ), JSONWriter.Feature.PrettyFormat));

        System.out.println("=====================================================================================================================");

        System.out.println(JSON.toJSONString(backtraceCalculator.calculate(
                Lists.newArrayList(
                        new ReductionCoupon("满100-10（无限制）", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList()),
                        new ReductionCoupon("满100-10（无限制）", new Date(), DateTime.now().plusDays(1).toDate(), 100, 20, Lists.newArrayList()),
                        new DiscountCoupon("9折券", new Date(), DateTime.now().plusDays(1).toDate(), 1000, .9d, Lists.newArrayList()),
                        new DiscountCoupon("9.5折券", new Date(), DateTime.now().plusDays(1).toDate(), 950, .95d, Lists.newArrayList()),
                        new DiscountCoupon("6折券(1天后到期)", new Date(), DateTime.now().plusDays(1).toDate(), 960, .6d, Lists.newArrayList()),
                        new DiscountCoupon("6折券(2天后到期)", new Date(), DateTime.now().plusDays(2).toDate(), 960, .6d, Lists.newArrayList())
                ),
                1000
        ), JSONWriter.Feature.PrettyFormat));

        System.out.println("=====================================================================================================================");

        System.out.println(JSON.toJSONString(backtraceCalculator.calculate(
                Lists.newArrayList(
                        new ReductionCoupon("满1000-10（无限制）", new Date(), DateTime.now().plusDays(1).toDate(), 1000, 10, Lists.newArrayList()),
                        new ReductionCoupon("满1000-100（同类型仅一张）", new Date(), DateTime.now().plusDays(1).toDate(), 1000, 100, Lists.newArrayList(ExcludeType.SAME_COUPON)),
                        new DiscountCoupon("9折券", new Date(), DateTime.now().plusDays(1).toDate(), 9000, .9d, Lists.newArrayList()),
                        new DiscountCoupon("9.5折券", new Date(), DateTime.now().plusDays(1).toDate(), 9500, .95d, Lists.newArrayList()),
                        new DiscountCoupon("6折券(1天后到期)", new Date(), DateTime.now().plusDays(1).toDate(), 9600, .6d, Lists.newArrayList()),
                        new DiscountCoupon("6折券(2天后到期)（同类型仅一张）", new Date(), DateTime.now().plusDays(2).toDate(), 9600, .6d, Lists.newArrayList(ExcludeType.SAME_COUPON))
                ),
                10000
        ), JSONWriter.Feature.PrettyFormat));
    }
}
