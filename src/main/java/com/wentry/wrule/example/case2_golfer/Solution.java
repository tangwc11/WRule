package com.wentry.wrule.example.case2_golfer;

import com.google.common.collect.Lists;
import com.wentry.wrule.ISession;
import com.wentry.wrule.entity.EndPoint;
import com.wentry.wrule.impl.BaseFact;
import com.wentry.wrule.impl.BaseRuleSet;
import com.wentry.wrule.impl.BaseSession;
import com.wentry.wrule.impl.MultiConditionRuleItem;
import com.wentry.wrule.impl.ConditionTriple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @Description:
 * @Author: tangwc
 * <p>
 * 高尔夫球员站位问题:
 * <p>
 * 已知有四个高尔夫球员，他们的名字是Fred,Joe,Bob,Tom;
 * * 今天他们分别穿着红色，蓝色，橙色，以及格子衣服，并且他们按照从左往右的顺序站成一排。
 * * 我们将最左边的位置定为1，最右边的位置定为4，中间依次是2,3位置。
 * * 现在我们了解的情况是：
 * * 1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
 * * 2.Fred右边紧挨着的球员穿蓝色衣服
 * * 3.Joe排在第2个位置
 * * 4.Bob穿着格子短裤
 * * 5.Tom没有排在第1位或第4位，也没有穿橙色衣服
 * * 请问,这四名球员的位置和衣服颜色。
 */
public class Solution {


    /**
     * 要想解决此类的问题，除了使用类似chat-gpt那样模拟神经网络的人工智能，普通的规则引擎实际使用的是穷举法。
     * 针对这个问题：
     * 1。针对 名字、颜色、位置，每种四个选择，一共4*4*4=64中组合
     * 2。针对每个组合，进行规则校验
     * 3。如果最终能够满足所有的规则，则输出结果，否则：
     * 4。调到下一个组合，直至组合遍历完毕，引擎执行结束
     * <p>
     * 需要注意的是，针对此类问题：
     * 1、一定存在合适的解，否则引擎就不会终止，或是在错误的状态终止（废话文学）
     * 2、规模不能太大，本质还是if-else的各种判断，只是逻辑抽象层次高了一点，少了一点代码，性能损耗一点不少
     */
    public static void main(String[] args) {

        new BaseSession(
                new BaseRuleSet(
                        //在drools中，这里都由drl文件定义
                        Lists.newArrayList(
                                /**
                                 * 依次定义下列规则：
                                 ** 1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
                                 ** 2.Fred右边紧挨着的球员穿蓝色衣服
                                 ** 3.Joe排在第2个位置
                                 ** 4.Bob穿着格子短裤
                                 ** 5.Tom没有排在第1位或第4位，也没有穿橙色衣服
                                 */
                                new MultiConditionRuleItem(
                                        Lists.newArrayList(
                                                //1.高尔夫球员Fred,目前不知道他的位置和衣服颜色
                                                new ConditionTriple<Golfer>()
                                                        .setClz(Golfer.class)
                                                        .setPredicate(new BiPredicate<ISession, Golfer>() {
                                                            @Override
                                                            public boolean test(ISession session, Golfer golfer) {
                                                                return "Fred".equals(golfer.getName());
                                                            }
                                                        }).setAction(new BiConsumer<ISession, Golfer>() {
                                                            @Override
                                                            public void accept(ISession session, Golfer golfer) {
                                                                session.getMemory().set("Fred", golfer);
                                                            }
                                                        }),
                                                //3. Joe排在第2个位置
                                                new ConditionTriple<Golfer>()
                                                        .setClz(Golfer.class)
                                                        .setPredicate(new BiPredicate<ISession, Golfer>() {
                                                            @Override
                                                            public boolean test(ISession session, Golfer golfer) {
                                                                return "Joe".equals(golfer.getName())
                                                                        && golfer.getPosition() == 2
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Fred")).getPosition()
                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Fred")).getColor())
                                                                        ;
                                                            }

                                                        }).setAction(new BiConsumer<ISession, Golfer>() {
                                                            @Override
                                                            public void accept(ISession session, Golfer golfer) {
                                                                session.getMemory().set("Joe", golfer);
                                                            }
                                                        }),
                                                //4. Bob穿着格子短裤
                                                new ConditionTriple<Golfer>()
                                                        .setClz(Golfer.class)
                                                        .setPredicate(new BiPredicate<ISession, Golfer>() {
                                                            @Override
                                                            public boolean test(ISession session, Golfer golfer) {
                                                                return "Bob".equals(golfer.getName())
                                                                        && "cell".equals(golfer.getColor())
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Joe")).getPosition()
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Fred")).getPosition()

                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Joe")).getColor())
                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Fred")).getColor())
                                                                        ;
                                                            }
                                                        }).setAction(new BiConsumer<ISession, Golfer>() {
                                                            @Override
                                                            public void accept(ISession session, Golfer golfer) {
                                                                session.getMemory().set("Bob", golfer);
                                                            }
                                                        }),
                                                //5. Tom没有排在第1位或第4位，也没有穿橙色衣服
                                                new ConditionTriple<Golfer>()
                                                        .setClz(Golfer.class)
                                                        .setPredicate(new BiPredicate<ISession, Golfer>() {
                                                            @Override
                                                            public boolean test(ISession session, Golfer golfer) {
                                                                return "Tom".equals(golfer.getName())
                                                                        && (golfer.getPosition() != 1 && golfer.getPosition() != 4)
                                                                        && !"orange".equals(golfer.getColor())
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Bob")).getPosition()
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Joe")).getPosition()
                                                                        && golfer.getPosition() != ((Golfer) session.getMemory().get("Fred")).getPosition()

                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Bob")).getColor())
                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Joe")).getColor())
                                                                        && !golfer.getColor().equals(((Golfer) session.getMemory().get("Fred")).getColor())
                                                                        ;
                                                            }

                                                        }).setAction(new BiConsumer<ISession, Golfer>() {
                                                            @Override
                                                            public void accept(ISession session, Golfer golfer) {
                                                                session.getMemory().set("Tom", golfer);
                                                            }
                                                        }),
                                                //2. Fred右边紧挨着的球员穿蓝色衣服
                                                //这个需要写到最后，在所有的人员都赋值到fact之后，才校验
                                                new ConditionTriple<EndPoint>()
                                                        .setClz(EndPoint.class)
                                                        .setPredicate(new BiPredicate<ISession, EndPoint>() {
                                                            @Override
                                                            public boolean test(ISession session, EndPoint endPoint) {

                                                                Object fred = session.getMemory().get("Fred");
                                                                if (fred == null) {
                                                                    return false;
                                                                }
                                                                Golfer castFred = (Golfer) fred;

                                                                //这三个人，在fred的右边，且穿蓝色衣服
                                                                for (String name : Lists.newArrayList("Joe", "Bob", "Tom")) {
                                                                    Object right = session.getMemory().get(name);
                                                                    if (right != null) {
                                                                        Golfer cast = (Golfer) right;
                                                                        //其余三人中，有位置在fred的右边，且穿的蓝色衣服
                                                                        if (((Golfer) right).getPosition() == castFred.getPosition() + 1
                                                                                && "blue".equals(cast.getColor())
                                                                        ) {
                                                                            return true;
                                                                        }
                                                                    }
                                                                }
                                                                return false;
                                                            }

                                                        })
                                        ),
                                        session -> {
                                            System.out.println("Fred:" + session.getMemory().get("Fred"));
                                            System.out.println("Joe:" + session.getMemory().get("Joe"));
                                            System.out.println("Bob:" + session.getMemory().get("Bob"));
                                            System.out.println("Tom:" + session.getMemory().get("Tom"));
                                        }
                                ).setOnce(true)
                        )),
                //添加物料，也就是事实
                new BaseFact().batchInsert(buildAllGolfers())
        ).fire();


    }

    private static List<Object> buildAllGolfers() {

        List<Object> golfers = new ArrayList<>();
        for (String color : Lists.newArrayList("red", "blue", "orange", "cell")) {
            for (String name : Lists.newArrayList("Fred", "Joe", "Bob", "Tom")) {
                for (Integer position : Lists.newArrayList(1, 2, 3, 4)) {
                    golfers.add(new Golfer(name, color, position));
                }
            }
        }
        return golfers;

    }

    public static class Golfer {
        private String name;
        private String color;
        private int position;

        public Golfer(String name, String color, int position) {
            this.name = name;
            this.color = color;
            this.position = position;
        }

        public String getName() {
            return name;
        }

        public Golfer setName(String name) {
            this.name = name;
            return this;
        }

        public String getColor() {
            return color;
        }

        public Golfer setColor(String color) {
            this.color = color;
            return this;
        }

        public int getPosition() {
            return position;
        }

        public Golfer setPosition(int position) {
            this.position = position;
            return this;
        }

        @Override
        public String toString() {
            return "Golfer{" +
                    "name='" + name + '\'' +
                    ", color='" + color + '\'' +
                    ", position=" + position +
                    '}';
        }
    }
}
