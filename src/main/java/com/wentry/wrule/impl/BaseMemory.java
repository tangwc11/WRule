package com.wentry.wrule.impl;

import com.wentry.wrule.IMemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseMemory implements IMemory {

    private final Map<String, Object> memory = new ConcurrentHashMap<>();

    @Override
    public <T> T get(String key) {
        return (T) memory.get(key);
    }

    @Override
    public <T> IMemory set(String key, T fact) {
        memory.put(key, fact);
        return this;
    }

}
