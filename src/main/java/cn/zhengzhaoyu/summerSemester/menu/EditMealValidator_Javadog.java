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
public class EditMealValidator_Javadog extends BaseValidator_Javadog {
    /**
     * 验证请求格式
     * @param c 调用验证器的controller
     */
    @Override
    protected void validate(Controller c) {
        validateString("editMealName", 1, 10, "message", "菜名格式错误");
        validateDouble("editMealPrice",0.0,10000.0,"message","非法菜品价格");
        validateRegex("editMealName", "^\\S{1,10}$", "message", "菜名格式错误");
        validateInteger("editMealRecommend",0,3,",message","推荐格式错误");
        validateInteger("editMealType",0,4,",message","类型格式错误");
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
