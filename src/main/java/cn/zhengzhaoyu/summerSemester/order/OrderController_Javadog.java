/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Table;
import cn.zhengzhaoyu.summerSemester.hall.HallService_Javadog;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;

import java.util.List;

/**
 * 订单管理的控制器
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderController_Javadog extends BaseController_Javadog{
    private static final OrderService_Javadog os=new OrderService_Javadog();
    private static final HallService_Javadog hs=new HallService_Javadog();

    @Before({GET.class})
    public void index() {
        render("adminOrder.html");
    }

    @Before({GET.class})
    public void meal() {
        List<Table> unusedList = hs.getTables(false);
        setAttr("unusedList", unusedList);
        render("orderMeal_1.html");
    }
}
