/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;

import cn.zhengzhaoyu.summerSemester.common.model.MealOrder;
import cn.zhengzhaoyu.summerSemester.common.model.RoomOrder;

/**
 * 订单服务的方法类
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderService_Javadog {
    public static final OrderService_Javadog me=new OrderService_Javadog();
    private static final MealOrder mealOrderDao = new MealOrder().dao();
    private static final RoomOrder roomOrderDao = new RoomOrder().dao();
}
