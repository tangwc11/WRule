package com.wentry.wrule.impl;

import com.wentry.wrule.ISession;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Description:
 * @Author: tangwc
 */
public class MultiConditionRuleItem extends BaseRuleItem {

    public MultiConditionRuleItem(List<ConditionTriple<?>> conditions, Consumer<ISession> thenDo) {
        this.conditions = conditions;
        this.thenDo = thenDo;
    }

    private final List<ConditionTriple<?>> conditions;
    private final Consumer<ISession> thenDo;

    @Override
    public boolean ifHit(ISession session) {

        if (CollectionUtils.isEmpty(conditions)) {
            return false;
        }

        /**
         * 这里就是模式匹配的过程：
         * 从一堆facts中，找到符合规则的，再触发规则
         * 周而复始，直至所有规则执行完毕
         *
         * 如果一个rule有多个conditions，每个condition对应多个满足条件的fact
         * 模式匹配的过程就是遍历所有fact，直至所有的condition满足，最后触发rule的动作
         */

        return slideMatchCondition(session,0);
    }

    private boolean slideMatchCondition(ISession session, int cdtIdx) {
        if (cdtIdx == -1) {
            return false;
        }

        if (cdtIdx >= conditions.size()) {
            return true;
        }

        ConditionTriple<?> conditions = this.conditions.get(cdtIdx);

        for (int i = 0; i < session.getFact().getFacts().size(); i++) {
            Object fact = session.getFact().getFacts().get(i);
            //noinspection unchecked
            if (conditions.getClz().equals(fact.getClass())
                    && conditions.getPredicate().test(session, fact)) {
                //noinspection unchecked
                conditions.getAction().accept(session, fact);
                if (slideMatchCondition(session, cdtIdx + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void thenDo(ISession session) {
        if (thenDo != null) {
            thenDo.accept(session);
        }
    }

}
