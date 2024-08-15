package com.wentry.wrule.example.case1_colacap;


import com.wentry.wrule.ISession;
import com.wentry.wrule.impl.BaseRuleItem;

import java.util.Optional;

/**
 * @Description:
 * @Author: tangwc
 * <p>
 * 可乐瓶盖的兑换规则
 */
public class Rule2_ExchangeColaCap extends BaseRuleItem {

    @Override
    public boolean ifHit(ISession session) {
        int capNum = (int) Optional.ofNullable(session.getFact().getFactByKey("capNum")).orElse(0);
        return capNum >= 2;
    }

    @Override
    public void thenDo(ISession session) {
        int capNum = (int) Optional.ofNullable(session.getFact().getFactByKey("capNum")).orElse(0);
        //修改事实：瓶盖数-1
        capNum = capNum - 2;
        session.getFact().set("capNum", capNum);
        //修改结果：已喝数量+1
        int drunk = (int) Optional.ofNullable(session.getFact().getFactByKey("drunk")).orElse(0);
        session.getFact().set("drunk", drunk + 1);
        //修改事实：喝完之后瓶盖数+1
        session.getFact().set("capNum", capNum + 1);
    }

}
