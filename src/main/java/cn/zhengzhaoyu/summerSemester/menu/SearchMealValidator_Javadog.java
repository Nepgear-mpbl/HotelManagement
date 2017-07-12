/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.menu;

import cn.zhengzhaoyu.summerSemester.common.validator.BaseValidator_Javadog;
import com.jfinal.core.Controller;

/**
 * 添加包间的输入验证器
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class SearchMealValidator_Javadog extends BaseValidator_Javadog {
    /**
     * 验证请求格式
     * @param c 调用验证器的controller
     */
    @Override
    protected void validate(Controller c) {
        validateString("searchMealName", 1, 10, "message", "菜名格式错误");
        validateRegex("searchMealName", "^\\S{1,10}$", "message", "菜名格式错误");
    }

    /**
     * 错误处理
     * @param c 调用验证器的controller
     */
    @Override
    protected void handleError(Controller c) {
        c.setAttr("status",false);
        c.renderJson();
    }
}
