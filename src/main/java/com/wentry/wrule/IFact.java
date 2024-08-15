package com.wentry.wrule;

import java.util.List;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IFact {

    <T> IFact insert(T fact);

    <T> IFact batchInsert(List<Object> facts);

    List<Object> getFacts();

    <T> IFact set(String key, T fact);

    <T> T getFactByKey(String key);
}
