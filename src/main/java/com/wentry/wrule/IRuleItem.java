package com.wentry.wrule;

/**
 * @Description:
 * @Author: tangwc
 */
public interface IRuleItem {

    /**
     * 是否满足
     */
    boolean ifHit(ISession session);

    /**
     * 执行
     */
    void thenDo(ISession session);

    /**
     * 最基本的属性：顺序，order越小，越先执行
     */
    int order();

}
