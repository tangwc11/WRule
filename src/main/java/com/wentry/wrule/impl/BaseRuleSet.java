package com.wentry.wrule.impl;

import com.wentry.wrule.IRuleSet;
import com.wentry.wrule.IRuleItem;
import com.wentry.wrule.ISession;

import java.util.Comparator;
import java.util.List;

/**
 * @Description:
 * @Author: tangwc
 */
public class BaseRuleSet implements IRuleSet {

    private final List<IRuleItem> items;

    public BaseRuleSet(List<IRuleItem> items) {
        this.items = items;
        items.sort(Comparator.comparingInt(IRuleItem::order));
    }

    @Override
    public IRuleItem next(ISession session) {

        for (IRuleItem item : items) {
            if (item instanceof BaseRuleItem) {
                BaseRuleItem br = ((BaseRuleItem) item);
                if (br.isOnce() && br.isTriggered()) {
                    continue;
                }
            }
            if (item.ifHit(session)) {
                trySetTrigger(item);
                return item;
            }
        }
        return null;
    }

    private void trySetTrigger(IRuleItem item) {
        if (item instanceof BaseRuleItem) {
            BaseRuleItem br = ((BaseRuleItem) item);
            br.setTriggered(true);
        }
    }

}
