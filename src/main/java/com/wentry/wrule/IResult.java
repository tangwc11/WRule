package com.wentry.wrule;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IResult {

    Object getRes(String key);

    Object setRes(String key,Object newVal);

}
