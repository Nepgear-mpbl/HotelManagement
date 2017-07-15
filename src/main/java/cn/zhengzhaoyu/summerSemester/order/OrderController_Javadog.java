/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Meal;
import cn.zhengzhaoyu.summerSemester.common.model.Order;
import cn.zhengzhaoyu.summerSemester.common.model.Room;
import cn.zhengzhaoyu.summerSemester.common.model.Table;
import cn.zhengzhaoyu.summerSemester.hall.HallService_Javadog;
import cn.zhengzhaoyu.summerSemester.menu.MenuService_Javadog;
import cn.zhengzhaoyu.summerSemester.room.RoomService_Javadog;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;

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

    /**
     * 渲染订单预定界面
     */
    @Before({GET.class})
    public void index() {
        render("order_1.html");
    }

    /**
     * 渲染订单预定第二页-大厅
     */
    @Before({GET.class})
    public void orderNextHall() {
        Integer orderId = getParaToInt();
        List<Table> unusedList = hs.getUnusedTables(os.getOrderSize(orderId));
        setAttr("orderId", orderId);
        setAttr("unusedList", unusedList);
        render("order_2_hall.html");
    }

    /**
     * 渲染订单预定第二页-包间
     */
    @Before({GET.class})
    public void orderNextRoom() {
        Integer orderId = getParaToInt();
        List<Room> unusedList = rs.getUnusedRooms(os.getOrderSize(orderId));
        setAttr("orderId", orderId);
        setAttr("unusedList", unusedList);
        render("order_2_room.html");
    }

    /**
     * 渲染包间订单第三页
     */
    @Before({GET.class})
    public void orderFinal() {
        Integer orderId = getParaToInt();
        List<Meal> menu = ms.getAllMeals();
        setAttr("menu", menu);
        setAttr("orderId", orderId);
        render("order_3.html");
    }

    /**
     * 渲染订单管理界面
     */
    @Before({GET.class})
    public void admin() {
        List<Record> unfinishedOrderList = os.getOrdersByState(0);
        List<Record> unconfirmedOrderList = os.getOrdersByState(1);
        List<Record> confirmedOrderList = os.getOrdersByState(2);
        List<Record> payedOrderList = os.getOrdersByState(3);
        setAttr("unfinishedOrderList", unfinishedOrderList);
        setAttr("unconfirmedOrderList", unconfirmedOrderList);
        setAttr("confirmedOrderList", confirmedOrderList);
        setAttr("payedOrderList", payedOrderList);
        render("adminOrder.html");
    }

    @Before({GET.class})
    public void orderInfo() {
        int orderId = getParaToInt();
        Ret ret = os.getOrderInfo(orderId);
        setAttr("data", ret);
        render("orderInfo.html");
    }

    /**
     * 添加订单
     */
    @Before({POST.class, OrderValidator_Javadog.class})
    public void addOrder() {
        String orderName = getPara("orderName");
        Integer orderNum = getParaToInt("orderNum");
        String orderTel = getPara("orderTel");
        Ret ret = os.addOrder(orderName, orderNum, orderTel);
        renderJson(ret);
    }

    /**
     * 为订单绑定桌子
     */
    @Before({POST.class})
    public void setOrderTable() {
        Integer tableId = getParaToInt(0);
        Integer orderId = getParaToInt(1);
        Ret ret = os.setOrderPlace(orderId, tableId, 0);
        renderJson(ret);
    }

    /**
     * 为订单绑定包间
     */
    @Before({POST.class})
    public void setOrderRoom() {
        Integer roomId = getParaToInt(0);
        Integer orderId = getParaToInt(1);
        Ret ret = os.setOrderPlace(orderId, roomId, 1);
        renderJson(ret);
    }

    /**
     * 为订单绑定菜品
     */
    @Before({POST.class})
    public void setOrderText() {
        Integer orderId = getParaToInt("orderId");
        String textJson = getPara("data");
        Ret ret = os.setOrderText(orderId, textJson);
        renderJson(ret);
    }

    /**
     * 确认订单
     */
    @Before({POST.class})
    public void confirmOrder() {
        int orderId = getParaToInt();
        Ret ret = os.confirmOrder(orderId);
        renderJson(ret);
    }

    /**
     * 支付订单
     */
    @Before({POST.class})
    public void payOrder() {
        int orderId = getParaToInt();
        Ret ret = os.payOrder(orderId);
        renderJson(ret);
    }

    /**
     * 删除订单
     */
    @Before({POST.class})
    public void removeOrder() {
        int orderId = getParaToInt();
        Ret ret = os.removeOrder(orderId);
        renderJson(ret);
    }

    /**
     * 删除未完成订单
     */
    @Before({POST.class})
    public void removeUnfinishedOrder() {
        int orderId = getParaToInt();
        Ret ret = os.removeUnfinishedOrder(orderId);
        renderJson(ret);
    }
}