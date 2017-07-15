/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;

import cn.zhengzhaoyu.summerSemester.common.validator.BaseValidator_Javadog;
import com.jfinal.core.Controller;

/**
 * 验证添加包间订单信息
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderValidator_Javadog extends BaseValidator_Javadog{
    /**
     * 验证请求内容
     * @param c 当前控制器
     */
    @Override
    protected void validate(Controller c) {
        validateString("orderName",1,30,"message","订单人姓名不合法");
        validateString("orderTel",6,11,"message","联系方式不合法");
        validateInteger("orderNum",1,50,"message","用餐人数应在0~50之间");
    }

    /**
     * 错误处理
     * @param c 当前控制器
     */
    @Override
    protected void handleError(Controller c) {
        c.setAttr("status",false);
        c.renderJson();
    }
}
