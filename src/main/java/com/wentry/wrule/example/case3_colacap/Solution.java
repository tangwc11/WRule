package com.wentry.wrule.example.case3_colacap;

import com.google.common.collect.Lists;
import com.wentry.wrule.ISession;
import com.wentry.wrule.impl.BaseFact;
import com.wentry.wrule.impl.BaseRuleSet;
import com.wentry.wrule.impl.BaseRuleItem;
import com.wentry.wrule.impl.BaseSession;
import com.wentry.wrule.impl.MultiConditionRuleItem;
import com.wentry.wrule.impl.ConditionTriple;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * 使用multiCondition来定义规则
 * * 小明喝汽水问题:
 * * 1元钱一瓶汽水，喝完后两个空瓶换一瓶汽水，问：小明有20元钱，最多可以喝到几瓶汽水？
 */
public class Solution {


    public static void main(String[] args) {

        BaseSession session = new BaseSession(
                //定义规则
                new BaseRuleSet(
                        //这里看起来比较麻烦，自定义drl格式的规则表达式即可
                        Lists.newArrayList(
                                //规则1：买汽水
                                new MultiConditionRuleItem(
                                        Lists.newArrayList(
                                                new ConditionTriple<Xiaoming>()
                                                        .setClz(Xiaoming.class)
                                                        .setPredicate(new BiPredicate<ISession, Xiaoming>() {
                                                            @Override
                                                            public boolean test(ISession session, Xiaoming xiaoming) {
                                                                return xiaoming.getMoney() > 0;
                                                            }
                                                        }).setAction(new BiConsumer<ISession, Xiaoming>() {
                                                            @Override
                                                            public void accept(ISession session, Xiaoming xiaoming) {
                                                                session.getMemory().set("xiaoming", xiaoming);
                                                            }
                                                        })
                                        ),
                                        sn1 -> {
                                            //相当于drools的update操作
                                            Xiaoming xm = sn1.getMemory().get("xiaoming");
                                            System.out.println("小明喝了一瓶水");
                                            xm.setMoney(xm.getMoney() - 1);
                                            xm.setDrink(xm.getDrink() + 1);
                                            xm.setBottle(xm.getBottle() + 1);
                                        }),

                                //规则2：换汽水
                                new MultiConditionRuleItem(
                                        Lists.newArrayList(
                                                new ConditionTriple<Xiaoming>()
                                                        .setClz(Xiaoming.class)
                                                        .setPredicate(new BiPredicate<ISession, Xiaoming>() {
                                                            @Override
                                                            public boolean test(ISession session, Xiaoming xiaoming) {
                                                                return xiaoming.getBottle() >= 2;
                                                            }
                                                        })
                                        ),
                                        sn2 -> {
                                            //相当于drools的update操作
                                            Xiaoming xm = sn2.getMemory().get("xiaoming");
                                            System.out.println("小明兑换一瓶水");
                                            xm.setDrink(xm.getDrink() + 1);
                                            xm.setBottle(xm.getBottle() - 1);
                                        }
                                ),

                                //规则3：输出最后结果
                                new BaseRuleItem() {
                                    @Override
                                    public boolean ifHit(ISession session) {
                                        return true;
                                    }

                                    @Override
                                    public void thenDo(ISession session) {
                                        System.out.println((Xiaoming) session.getMemory().get("xiaoming"));
                                    }
                                }.setOnce(true).setOrder(3)
                        )
                ),
                //定义事实：初始20元
                new BaseFact().insert(new Xiaoming().setMoney(20)));


        //触发规则
        session.fire();

    }

    static class Xiaoming {
        int money;
        int drink;
        int bottle;

        public int getDrink() {
            return drink;
        }

        public Xiaoming setDrink(int drink) {
            this.drink = drink;
            return this;
        }

        public int getBottle() {
            return bottle;
        }

        public Xiaoming setBottle(int bottle) {
            this.bottle = bottle;
            return this;
        }

        public int getMoney() {
            return money;
        }

        public Xiaoming setMoney(int money) {
            this.money = money;
            return this;
        }

        @Override
        public String toString() {
            return "Xiaoming{" +
                    "money=" + money +
                    ", drink=" + drink +
                    ", bottle=" + bottle +
                    '}';
        }
    }

}