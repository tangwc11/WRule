package com.wentry.wrule;


import com.wentry.wrule.impl.BaseFact;
import com.wentry.wrule.impl.BaseMemory;
import com.wentry.wrule.impl.BaseResult;

/**
 * @Description:
 * @Author: tangwc
 */
public interface ISession {

    default IFact getFact(){
        return new BaseFact();
    };

    default IMemory getMemory(){
        return new BaseMemory();
    }

    default IResult getResult(){
        return new BaseResult();
    }

    void fire();

}
