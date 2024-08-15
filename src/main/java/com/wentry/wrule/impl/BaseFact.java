package com.wentry.wrule.impl;


import com.wentry.wrule.IFact;
import com.wentry.wrule.entity.EndPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseFact implements IFact {

    private final Map<String, Object> kvFacts = new ConcurrentHashMap<>();
    private final List<Object> facts = new ArrayList<>();

    public BaseFact() {
        //放入一些个锚点fact
        insert(new EndPoint());
    }

    @Override
    public String toString() {
        return "BaseFact{" +
                "kvFacts=" + kvFacts +
                ", facts=" + facts +
                '}';
    }

    @Override
    public <T> T getFactByKey(String key) {
        return (T) kvFacts.get(key);
    }

    @Override
    public <T> IFact set(String key, T fact) {
        kvFacts.put(key, fact);
        return this;
    }

    @Override
    public List<Object> getFacts() {
        return facts;
    }

    @Override
    public <T> IFact insert(T fact) {
        facts.add(fact);
        return this;
    }

    @Override
    public <T> IFact batchInsert(List<Object> facts) {
        for (Object fact : facts) {
            insert(fact);
        }
        return this;
    }

}
