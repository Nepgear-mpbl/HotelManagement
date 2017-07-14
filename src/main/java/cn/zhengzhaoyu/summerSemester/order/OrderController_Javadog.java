/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Meal;
import cn.zhengzhaoyu.summerSemester.common.model.Room;
import cn.zhengzhaoyu.summerSemester.common.model.Table;
import cn.zhengzhaoyu.summerSemester.hall.HallService_Javadog;
import cn.zhengzhaoyu.summerSemester.menu.MenuService_Javadog;
import cn.zhengzhaoyu.summerSemester.room.RoomService_Javadog;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * 订单管理的控制器
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderController_Javadog extends BaseController_Javadog {
    private static final OrderService_Javadog os = new OrderService_Javadog();
    private static final HallService_Javadog hs = new HallService_Javadog();
    private static final MenuService_Javadog ms = new MenuService_Javadog();
    private static final RoomService_Javadog rs = new RoomService_Javadog();

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

    @Before({GET.class})
    public void mealNextStep() {
        Integer orderId = getParaToInt();
        List<Meal> menu = ms.getAllMeals();
        setAttr("menu", menu);
        setAttr("orderId", orderId);
        render("orderMeal_2.html");
    }

    @Before({GET.class})
    public void room() {
        render("orderRoom_1.html");
    }

    @Before({GET.class})
    public void roomNextStep() {
        Integer roomOrderId=getParaToInt();
        List<Room> unusedList = rs.getUnusedRooms(os.getRoomOrderSize(roomOrderId));
        setAttr("roomOrderId",roomOrderId);
        setAttr("unusedList", unusedList);
        render("orderRoom_2.html");
    }

    @Before({GET.class})
    public void roomFinalStep() {
        Integer roomOrderId = getParaToInt(0);
        Integer mealOrderId = getParaToInt(1);
        List<Meal> menu = ms.getAllMeals();
        setAttr("menu", menu);
        setAttr("roomOrderId", roomOrderId);
        setAttr("mealOrderId", mealOrderId);
        render("orderRoom_3.html");
    }

    @Before({POST.class})
    public void addMeal() {
        Integer tableId = getParaToInt();
        Ret ret = os.addMealOrder(0, tableId);
        renderJson(ret);
    }

    @Before({POST.class})
    public void setMealText() {
        Integer orderId = getParaToInt("orderId");
        String textJson = getPara("data");
        Ret ret = os.setMealOrderText(textJson, orderId);
        renderJson(ret);
    }

    @Before({POST.class})
    public void addRoom() {
        String orderName=getPara("orderName");
        Integer orderNum = getParaToInt("orderNum");
        String orderTel=getPara("orderTel");
        Ret ret = os.addRoomOrder(orderName,orderNum,orderTel);
        renderJson(ret);
    }

    @Before({POST.class})
    public void setRoomRoom() {
        Integer roomId = getParaToInt(0);
        Integer roomOrderId=getParaToInt(1);
        Ret ret=os.setRoomOrderRoom(roomId,roomOrderId);
        renderJson(ret);
    }

    @Before({POST.class})
    public void setRoomMeal() {
        Integer mealOrderId = getParaToInt("mealOrderId");
        Integer roomOrderId = getParaToInt("roomOrderId");
        String textJson = getPara("data");
        Ret ret=os.setRoomOrderMealOrder(mealOrderId,roomOrderId,textJson);
        renderJson(ret);
    }
}
