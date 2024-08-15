package com.wentry.wrule.example.case1_colacap;

import com.wentry.wrule.IFact;
import com.wentry.wrule.IResult;
import com.wentry.wrule.ISession;
import com.wentry.wrule.impl.BaseRuleItem;

import java.util.Optional;

/**
 * @Description:
 * @Author: tangwc
 */
public class Rule3_ResultPrint extends BaseRuleItem {

    @Override
    public boolean ifHit(ISession session) {
        return true;
    }

    @Override
    public void thenDo(ISession session) {
        int drunk = (int) Optional.ofNullable(session.getFact().getFactByKey("drunk")).orElse(0);
        System.out.println("一共喝了：" + drunk + " 瓶汽水");
        IFact fact = session.getFact();
        System.out.println("Fact:" + fact);
        IResult result = session.getResult();
        System.out.println("Result:" + result);
    }
}
