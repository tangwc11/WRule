package com.wentry.wrule.example.case1_colacap;

import com.wentry.wrule.ISession;
import com.wentry.wrule.impl.BaseRuleItem;

import java.util.Optional;

/**
 * @Description:
 * @Author: tangwc
 */
public class Rule1_BuyCola extends BaseRuleItem {
    @Override
    public boolean ifHit(ISession session) {
        int money = (int) Optional.ofNullable(session.getFact().getFactByKey("money")).orElse(0);
        return money > 0;
    }

    @Override
    public void thenDo(ISession session) {
        int money = (int) Optional.ofNullable(session.getFact().getFactByKey("money")).orElse(0);
        int capNum = (int) Optional.ofNullable(session.getFact().getFactByKey("capNum")).orElse(0);
        int drunk = (int) Optional.ofNullable(session.getFact().getFactByKey("drunk")).orElse(0);
        //买一瓶水，修改事实：money-1
        session.getFact().set("money", money - 1);
        //修改结果：已喝汽水数量+1
        session.getFact().set("drunk", drunk + 1);
        //修改事实：瓶盖+1
        session.getFact().set("capNum", capNum + 1);
    }

}
