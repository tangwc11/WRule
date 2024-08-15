package com.wentry.wrule;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IRuleSet {

    /**
     * 根据事实，返回下一个规则
     */
    IRuleItem next(ISession session);

}
