/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.login;

import com.jfinal.core.Controller;

import javax.xml.validation.Validator;

/**
 * 验证请求格式是否合法
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoginValidator_Javadog extends com.jfinal.validate.Validator {
    @Override
    protected void validate(Controller c) {
        validateCaptcha("captcha", "message", "验证码错误！");
        validateString("username",3,20, "message","用户名或密码错误！");
        validateString("password",6,20, "message","用户名或密码错误！");
        validateRegex("username","^\\S{3,20}$","message","用户名或密码错误！");
        validateRegex("password","^\\S{6,20}$","message","用户名或密码错误！");
    }

    @Override
    protected void handleError(Controller c) {
        c.setAttr("status",false);
        c.renderJson();
    }
}
