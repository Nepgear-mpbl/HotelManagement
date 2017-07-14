/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;


import cn.zhengzhaoyu.summerSemester.common.model.MealOrder;
import cn.zhengzhaoyu.summerSemester.common.model.Room;
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
    private static final Room roomDao = new Room().dao();

    /**
     * 添加一个新的菜品订单
     *
     * @param type  订单类型（ 0 或 1 ）
     * @param place 订单位置
     * @return 是否成功
     */
    public Ret addMealOrder(Integer type, Integer place) {
        MealOrder mealOrder = new MealOrder();
        if (type == 0) {
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
        } else if (type == 1) {
            Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", place));
            if (null == room) {
                return Ret.by("status", false);
            }
            boolean b = Db.tx(4, () -> mealOrder.setType(type).setPlace(place).setState(0).save());
            if (b) {
                return Ret.by("status", true).set("orderId", mealOrder.getId());
            } else {
                return Ret.by("status", false);
            }
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
        RoomOrder roomOrder = new RoomOrder();
        if (roomOrder.setOrdername(orderName).setOrdernum(orderNum).setTel(tel).setState(0).save()) {
            return Ret.by("status", true).set("roomOrderId", roomOrder.getId());
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 为包间订单绑定包间号并创建对应菜品订单
     *
     * @param roomId 绑定的包间id
     * @param roomOrderId 绑定的包间订单id
     * @return 返回是否成功
     */
    public Ret setRoomOrderRoom(Integer roomId, Integer roomOrderId) {
        MealOrder mealOrder = new MealOrder();
        Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", roomId));
        if (null == room) {
            return Ret.by("status", false);
        }
        RoomOrder roomOrder = roomOrderDao.findFirst(roomOrderDao.getSqlPara("roomOrder.findById", roomOrderId));
        if (null == roomOrder) {
            return Ret.by("status", false);
        }
        boolean b = Db.tx(4, () -> mealOrder.setType(1).setPlace(roomId).setState(0).save() && roomOrder.setRoomId(roomId).setState(0).update() && room.setBelong(roomOrder.getId()).update());
        if (b) {
            return Ret.by("status", true).set("mealOrderId", mealOrder.getId());
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 为包间订单绑定菜品订单并添加菜品订单内容
     *
     * @param mealOrderId 绑定的菜品订单
     * @param roomOrderId 绑定的包间订单
     * @param text 添加的菜品订单内容
     * @return 返回是否成功
     */
    public Ret setRoomOrderMealOrder(Integer mealOrderId, Integer roomOrderId, String text) {
        RoomOrder roomOrder = roomOrderDao.findFirst(roomOrderDao.getSqlPara("roomOrder.findById", roomOrderId));
        if (null == roomOrder) {
            return Ret.by("status", false);
        }
        MealOrder mealOrder = mealOrderDao.findFirst(mealOrderDao.getSqlPara("mealOrder.findById", mealOrderId));
        if (null == mealOrder) {
            return Ret.by("status", false);
        }
        boolean b = Db.tx(4, () -> mealOrder.setOrderText(text).setState(1).update() && roomOrder.setMealOrderId(mealOrderId).setState(1).update() && mealOrder.setPlace(roomOrder.getRoomId()).update());
        if (b) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 解析菜品订单的json字符串
     * @param textJson json字符串
     * @return 包含解析信息的list数组
     */
    public ArrayList[] loadFromJson(String textJson) {
        ArrayList jsonList = JsonKit.parse(textJson, ArrayList.class);
        List<HashMap> mapList = new ArrayList<HashMap>();
        for (Object str : jsonList
                ) {
            mapList.add(JsonKit.parse(str.toString(), HashMap.class));
        }
        ArrayList<Integer> mealIdList = new ArrayList<>();
        ArrayList<Integer> mealNumList = new ArrayList<>();
        for (HashMap map : mapList
                ) {
            mealIdList.add((Integer) map.get("id"));
            mealNumList.add((Integer) map.get("num"));
        }
        return (new ArrayList[]{mealIdList, mealNumList});
    }

    /**
     * 根据id获取包间订单的客人数目
     * @param roomOrderId 包间订单id
     * @return 数目
     */
    public int getRoomOrderSize(int roomOrderId) {
        RoomOrder roomOrder = roomOrderDao.findFirst(roomOrderDao.getSqlPara("roomOrder.findById", roomOrderId));
        return roomOrder.getOrdernum();
    }
}
