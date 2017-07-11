/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.reg;

import cn.zhengzhaoyu.summerSemester.common.validator.BaseValidator_Javadog;
import com.jfinal.core.Controller;

/**
 * 注册页面的请求验证器
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegValidator_Javadog extends BaseValidator_Javadog {
    /**
     * 验证表单内容是否合法
     *
     * @param c 使用这个validator的controller
     */
    @Override
    protected void validate(Controller c) {
        validateString("username", 3, 20, "message", "用户名不能为空！");
        validateString("password", 6, 20, "message", "密码不能为空！");
        validateString("repeatPassword", 6, 12, "message", "密码不能为空！");
        validateRegex("username", "^\\S{3,20}$", "message", "用户名格式错误！");
        validateRegex("password", "^\\S{6,20}$", "message", "密码格式错误！");
        validateRegex("repeatPassword", "^\\S{6,20}$", "message", "密码格式错误！");
        validateEqualString(c.getPara("repeatPassword"), c.getPara("password"), "message", "两次输入的密码不匹配！");
    }

    /**
     * 出现错误时的处理
     *
     * @param c 使用这个validator的controller
     */
    @Override
    protected void handleError(Controller c) {
        c.setAttr("status", false);
        c.renderJson();
    }
}
