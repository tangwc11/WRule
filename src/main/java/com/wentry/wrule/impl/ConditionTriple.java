package com.wentry.wrule.impl;

import com.wentry.wrule.ISession;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * @Description:
 * @Author: tangwc
 *
 * RuleItem中的when三元组即：
 * 1。 对什么类的fact
 * 2。 进行什么样的field条件判断
 * 3。 再进行什么操作（一般就是赋值）
 * drl中的定义语法为：
 * $xxx : ClassName(field1 == 1 && field2 == "abc")
 *
 */
public class ConditionTriple<T> {

    private Class<T> clz;
    private BiPredicate<ISession,T> predicate = (o, o2) -> false;
    private BiConsumer<ISession,T> action = (o, o2) -> {
    };

    public Class<T> getClz() {
        return clz;
    }

    public ConditionTriple<T> setClz(Class<T> clz) {
        this.clz = clz;
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BiPredicate getPredicate() {
        return predicate;
    }

    public ConditionTriple<T> setPredicate(BiPredicate<ISession,T> predicate) {
        this.predicate = predicate;
        return this;
    }

    @SuppressWarnings("rawtypes")
    public BiConsumer getAction() {
        return action;
    }

    public ConditionTriple<T> setAction(BiConsumer<ISession,T> action) {
        this.action = action;
        return this;
    }
}
