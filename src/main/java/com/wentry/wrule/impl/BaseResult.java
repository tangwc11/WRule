package com.wentry.wrule.impl;

import com.wentry.wrule.IResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseResult implements IResult {

    private final Map<String, Object> res = new ConcurrentHashMap<>();

    @Override
    public Object getRes(String key) {
        return res.get(key);
    }

    @Override
    public Object setRes(String key, Object newVal) {
        return res.put(key, newVal);
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "res=" + res +
                '}';
    }
}
