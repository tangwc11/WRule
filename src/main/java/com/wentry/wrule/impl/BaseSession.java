package com.wentry.wrule.impl;


import com.wentry.wrule.IFact;
import com.wentry.wrule.IMemory;
import com.wentry.wrule.IResult;
import com.wentry.wrule.IRuleSet;
import com.wentry.wrule.IRuleItem;
import com.wentry.wrule.ISession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseSession implements ISession {

    private IRuleSet rule = null;
    private IFact fact = null;
    private IMemory memory = new BaseMemory();
    private IResult result = null;
    private int exeCount = 1;
    private final Map<String, Object> resMap = new ConcurrentHashMap<>();

    private BaseSession() {
    }

    public BaseSession(IRuleSet rule, IFact fact) {
        this.rule = rule;
        this.fact = fact;
        //初始化，fact需要植入memory
        this.result = ISession.super.getResult();
    }

    public BaseSession(IRuleSet rule, IFact fact, IResult result) {
        this.rule = rule;
        this.fact = fact;
        this.result = result;
    }

    @Override
    public IFact getFact() {
        return fact;
    }

    @Override
    public IMemory getMemory() {
        return memory;
    }

    @Override
    public IResult getResult() {
        return result;
    }

    @Override
    public void fire() {
        try {
            if (rule != null && fact != null) {
                IRuleItem next = rule.next(this);
                while (next != null) {
                    System.out.println("exeCount:" + exeCount++ + ", currItem:" + next);
                    //
                    next.thenDo(this);
                    //
                    next = rule.next(this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
