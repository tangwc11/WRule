package com.wentry.wrule;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IMemory {

    <T> IMemory set(String key, T fact);

    <T> T get(String key);

}
