package com.wentry.wrule.impl;


import com.wentry.wrule.IRuleItem;
import com.wentry.wrule.ISession;

/**
 * @Description:
 * @Author: tangwc
 */
public abstract class BaseRuleItem implements IRuleItem {

    private int order;
    private boolean once;
    private boolean triggered;

    public BaseRuleItem setOrder(int order) {
        this.order = order;
        return this;
    }

    @Override
    public abstract boolean ifHit(ISession session);

    @Override
    public abstract void thenDo(ISession session);

    @Override
    public int order() {
        return order;
    }

    public int getOrder() {
        return order;
    }

    public boolean isOnce() {
        return once;
    }

    public BaseRuleItem setOnce(boolean once) {
        this.once = once;
        return this;
    }

    public boolean isTriggered() {
        return triggered;
    }

    public BaseRuleItem setTriggered(boolean triggered) {
        this.triggered = triggered;
        return this;
    }
}
