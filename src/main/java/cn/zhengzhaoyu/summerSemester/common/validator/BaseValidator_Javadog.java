/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.common.validator;

import com.jfinal.validate.Validator;

/**
 * validator的基类
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 * */
public abstract class BaseValidator_Javadog extends Validator{
    /**
     * 遇错直接跳转
     */
    public BaseValidator_Javadog() {
        shortCircuit=true;
    }
}
