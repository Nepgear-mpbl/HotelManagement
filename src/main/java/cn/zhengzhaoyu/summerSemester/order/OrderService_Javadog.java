/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;


import cn.zhengzhaoyu.summerSemester.common.model.MealOrder;
import cn.zhengzhaoyu.summerSemester.common.model.RoomOrder;
import cn.zhengzhaoyu.summerSemester.common.model.Table;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单服务的方法类
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class OrderService_Javadog {
    public static final OrderService_Javadog me = new OrderService_Javadog();
    private static final MealOrder mealOrderDao = new MealOrder().dao();
    private static final RoomOrder roomOrderDao = new RoomOrder().dao();
    private static final Table tableDao = new Table().dao();

    /**
     * 添加一个新的菜品订单
     *
     * @param type  订单类型（ 0 或 1 ）
     * @param place 订单位置
     * @return 是否成功
     */
    public Ret addMealOrder(Integer type, Integer place) {
        MealOrder mealOrder = new MealOrder();
        Table table = tableDao.findFirst(tableDao.getSqlPara("table.findById", place));
        if (null == table) {
            return Ret.by("status", false);
        }
        boolean b = Db.tx(4, () -> mealOrder.setType(type).setPlace(place).setState(0).save() && table.setBelong(mealOrder.getId()).update());
        if (b) {
            return Ret.by("status", true).set("orderId", mealOrder.getId());
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 设置菜品订单的内容
     *
     * @param text        订单内容
     * @param mealOrderId 查找订单id
     * @return 是否成功
     */
    public Ret setMealOrderText(String text, Integer mealOrderId) {
        MealOrder mealOrder = mealOrderDao.findFirst(mealOrderDao.getSqlPara("mealOrder.findById", mealOrderId));
        if (mealOrder.setOrderText(text).setState(1).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 添加包间订单
     *
     * @param orderName 订餐人名字
     * @param orderNum  订餐人数
     * @param tel       订餐人电话
     * @return 是否成功
     */
    public Ret addRoomOrder(String orderName, Integer orderNum, String tel) {
        if (new RoomOrder().setOrdername(orderName).setOrdernum(orderNum).setTel(tel).setState(0).save()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    public Ret SetRoomOrderRoom(Integer roomId, Integer roomOrderId) {
        RoomOrder roomOrder = roomOrderDao.findFirst(roomOrderDao.getSqlPara("roomOrder.findById", roomOrderId));
        if (roomOrder.setRoomId(roomId).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    public Ret SetRoomOrderMealOrder(Integer mealOrderId, Integer roomOrderId) {
        RoomOrder roomOrder = roomOrderDao.findFirst(roomOrderDao.getSqlPara("roomOrder.findById", roomOrderId));
        if (roomOrder.setMealOrderId(mealOrderId).setState(1).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    public ArrayList[] loadFromJson(String textJson) {
        ArrayList jsonList = JsonKit.parse(textJson, ArrayList.class);
        List<HashMap> mapList = new ArrayList<HashMap>();
        for (Object str:jsonList
             ) {
           mapList.add(JsonKit.parse(str.toString(), HashMap.class));
        }
        ArrayList<Integer> mealIdList=new ArrayList<>();
        ArrayList<Integer> mealNumList=new ArrayList<>();
        for (HashMap map:mapList
             ) {
            mealIdList.add((Integer) map.get("id"));
            mealNumList.add((Integer) map.get("num"));
        }
        return (new ArrayList[]{mealIdList,mealNumList});
    }
}
