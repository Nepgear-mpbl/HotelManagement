/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.room;

import cn.zhengzhaoyu.summerSemester.common.validator.BaseValidator_Javadog;
import com.jfinal.core.Controller;

/**
 * 添加包间的输入验证器
 * Created by Nepge on 2017/7/11.
 */
public class SearchRoomValidator_Javadog extends BaseValidator_Javadog {
    /**
     * 验证请求格式
     * @param c 调用验证器的controller
     */
    @Override
    protected void validate(Controller c) {
        validateString("searchRoomName", 1, 10, "message", "包间名格式错误");
        validateRegex("searchRoomName", "^\\S{1,10}$", "message", "包间名格式错误");
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
