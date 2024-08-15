package com.wentry.wrule.example.case1_colacap;


import com.google.common.collect.Lists;
import com.wentry.wrule.impl.BaseFact;
import com.wentry.wrule.impl.BaseRuleSet;
import com.wentry.wrule.impl.BaseSession;

/**
 * @Description:
 * @Author: tangwc
 * <p>
 * 小明喝汽水问题:
 * <p>
 * 1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：小明有20元钱，最多可以喝到几瓶汽水？
 */
public class Solution {


    public static void main(String[] args) {

        BaseSession session = new BaseSession(
                //定义规则
                //这样写，和硬编码基本没啥区别...
                new BaseRuleSet(
                        Lists.newArrayList(
                                //买汽水
                                new Rule1_BuyCola().setOrder(1),
                                //换瓶盖
                                new Rule2_ExchangeColaCap().setOrder(2),
                                //结果打印，order最大确保最后执行，确保只执行一次，可以抽取一个OnceRuleItem类出来
//                                new RuleItem_ResultPrint().setOrder(100)
                                //最后执行
                                new Rule3_ResultPrint().setOrder(100).setOnce(true)
                        )
                ),
                //定义事实：初始20元
                new BaseFact().set("money", 20));


        session.fire();

    }

}
