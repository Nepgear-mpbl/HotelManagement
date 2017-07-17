/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.order;


import cn.zhengzhaoyu.summerSemester.common.model.*;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 订单服务的方法类
 *
 * @author ZZY
 * @version 1.0.1
 * @since 1.0.0
 */
public class OrderService_Javadog {
    public static final OrderService_Javadog me = new OrderService_Javadog();
    private static final Order orderDao = new Order().dao();
    private static final Table tableDao = new Table().dao();
    private static final Room roomDao = new Room().dao();
    private static final Meal mealDao = new Meal().dao();
    private static final User userDao = new User().dao();

    /**
     * 添加订单
     *
     * @param orderName 订餐人名字
     * @param orderNum  订餐人数
     * @param tel       订餐人电话
     * @return 是否成功
     */
    public Ret addOrder(String orderName, Integer orderNum, String tel, Integer userId) {
        Order order = new Order();
        if (order.setOrdername(orderName).setOrdernum(orderNum).setTel(tel).setOrderuser(userId).setState(0).save()) {
            return Ret.by("status", true).set("orderId", order.getId());
        } else {
            return Ret.by("status", false).set("message", "未知错误");
        }
    }

    /**
     * 为订单绑定包间/桌子号并设置类型
     *
     * @param orderId   订单id
     * @param placeId   绑定的包间/桌子id
     * @param orderType 绑定的订单类型
     * @return 返回是否成功
     */
    public Ret setOrderPlace(Integer orderId, Integer placeId, Integer orderType) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (0 == orderType) {
            Table table = tableDao.findFirst(tableDao.getSqlPara("table.findById", placeId));
            boolean b = Db.tx(4, () -> order.setPlace(placeId).setType(orderType).update() && table.setBelong(orderId).update());
            if (b) {
                return Ret.by("status", true).set("orderId", order.getId());
            } else {
                return Ret.by("status", false).set("message", "未知错误");
            }
        } else if (1 == orderType) {
            Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", placeId));
            boolean b = Db.tx(4, () -> order.setPlace(placeId).setType(orderType).update() && room.setBelong(orderId).update());
            if (b) {
                return Ret.by("status", true).set("orderId", order.getId());
            } else {
                return Ret.by("status", false).set("message", "未知错误");
            }
        } else {
            return Ret.by("status", false).set("message", "订单类型错误");
        }
    }

    /**
     * 为订单绑定菜品
     *
     * @param orderId 绑定的订单
     * @param text    添加的菜品订单内容
     * @return 返回是否成功
     */
    public Ret setOrderText(Integer orderId, String text) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (order.setOrderText(text).setState(1).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false).set("message", "未知错误");
        }
    }

    /**
     * 解析菜品订单的json字符串
     *
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
            mealIdList.add(Integer.parseInt((String) map.get("id")));
            mealNumList.add(Integer.parseInt((String) map.get("num")));
        }
        return (new ArrayList[]{mealIdList, mealNumList});
    }

    /**
     * 根据id获取订单的客人数目
     *
     * @param orderId 订单id
     * @return 数目
     */
    public int getOrderSize(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        return order.getOrdernum();
    }

    /**
     * 根据订单状态获取订单
     *
     * @param state 状态
     * @return 订单list
     */
    public List<Record> getOrderByState(int state) {
        return Db.find(orderDao.getSqlPara("order.getByState", state));
    }

    /**
     * 根据订单状态获取订单
     *
     * @param state 状态
     * @return 订单list
     */
    public List<Record> getOrderByStateWithType(int state) {
        return Db.find(orderDao.getSqlPara("order.getByStateWithType", state));
    }

    /**
     * 根据订单及用户状态获取订单
     *
     * @param state 状态
     * @return 订单list
     */
    public List<Record> getOrdersByStateAndUser(int state, int userId) {
        return Db.find(orderDao.getSqlPara("order.getByStateWithTypeAndUser", state, userId));
    }

    /**
     * 获取 订单详细信息
     *
     * @param orderId 订单id
     * @return 详细信息
     */
    public Ret getOrderInfo(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        Integer userId = order.getOrderuser();
        User user = userDao.findFirst(userDao.getSqlPara("user.findById", userId));
        if (null == user) {
            return Ret.by("status", false).set("message", "订单错误");
        }
        boolean discount = user.getType() == 1;
        String orderText = order.getOrderText();
        Integer orderType = order.getType();
        ArrayList[] menuInfoList = loadFromJson(orderText);
        ArrayList mealIdList = menuInfoList[0];
        ArrayList mealNumList = menuInfoList[1];
        List<String> retMealName = new ArrayList<>();
        List<BigDecimal> retMealPrice = new ArrayList<>();
        List<Integer> retMealNum = new ArrayList<>();
        BigDecimal roomPrice;
        BigDecimal totalPrice = new BigDecimal("0");
        if (0 == orderType) {
            roomPrice = new BigDecimal("0");
        } else if (1 == orderType) {
            Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", order.getPlace()));
            if (null == room) {
                return Ret.by("status", false).set("message", "订单状态错误");
            }
            roomPrice = room.getPrice();
        } else {
            return Ret.by("status", false).set("message", "订单类型错误");
        }
        for (int i = 0; i < mealIdList.size(); i++) {
            Meal meal = mealDao.findFirst(mealDao.getSqlPara("meal.findById", mealIdList.get(i)));
            String mealName = meal.getMealname();
            retMealName.add(mealName);
            BigDecimal mealPrice = meal.getPrice();
            retMealPrice.add(mealPrice);
            retMealNum.add((int) mealNumList.get(i));
            totalPrice = totalPrice.add(mealPrice.multiply(new BigDecimal((Integer) mealNumList.get(i))));
        }
        totalPrice = totalPrice.add(roomPrice);
        if (discount) {
            totalPrice = totalPrice.multiply(new BigDecimal("0.8"));
        }
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

        return Ret.by("status", true).set("mealNameList", retMealName).set("mealPriceList", retMealPrice).set("orderuser", user.getUsername())
                .set("mealNumList", retMealNum).set("roomPrice", roomPrice).set("totalPrice", totalPrice).set("order", order).set("isVip", discount);
    }

    /**
     * 确认订单
     *
     * @param orderId 订单id
     * @return 是否成功
     */
    public Ret confirmOrder(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (order.getState() == 2) {
            return Ret.by("status", false).set("message", "请不要重复确认");
        }
        if (order.getState() != 1) {
            return Ret.by("status", false).set("message", "订单状态错误");
        }
        if (order.setState(2).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false).set("message", "未知错误");
        }

    }

    /**
     * 支付订单
     *
     * @param orderId 订单id
     * @return 是否成功
     */
    public Ret payOrder(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (order.getState() == 3) {
            return Ret.by("status", false).set("message", "请不要重复支付");
        }
        if (order.getState() != 2) {
            return Ret.by("status", false).set("message", "订单状态错误");
        }
        if (0 == order.getType()) {
            Table table = tableDao.findFirst(tableDao.getSqlPara("table.findById", order.getPlace()));
            if (null == table) {
                return Ret.by("status", false).set("message", "订单错误");
            }
            boolean b = Db.tx(4, () -> order.setState(3).update() && table.setBelong(null).update());
            if (b) {
                return Ret.by("status", true);
            } else {
                return Ret.by("status", false).set("message", "未知错误");
            }
        } else if (1 == order.getType()) {
            Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", order.getPlace()));
            if (null == room) {
                return Ret.by("status", false).set("message", "订单错误");
            }
            boolean b = Db.tx(4, () -> order.setState(3).update() && room.setBelong(null).update());
            if (b) {
                return Ret.by("status", true).set("orderId", order.getId());
            } else {
                return Ret.by("status", false).set("message", "未知错误");
            }
        } else {
            return Ret.by("status", false).set("message", "订单类型错误");
        }
    }

    /**
     * 删除订单
     *
     * @param orderId 订单id
     * @return 是否成功
     */
    public Ret removeOrder(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (order.getState() != 3) {
            return Ret.by("status", false).set("message", "订单状态错误");
        }
        if (order.delete()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 删除未完成订单
     *
     * @param orderId 订单id
     * @return 是否成功
     */
    public Ret removeUnfinishedOrder(int orderId) {
        Order order = orderDao.findFirst(orderDao.getSqlPara("order.findById", orderId));
        if (null == order) {
            return Ret.by("status", false).set("message", "订单不存在");
        }
        if (null == order.getPlace()) {
            if (order.delete()) {
                return Ret.by("status", true);
            } else {
                return Ret.by("status", false);
            }
        } else {
            if (0 == order.getType()) {
                Table table = tableDao.findFirst(tableDao.getSqlPara("table.findById", order.getPlace()));
                if (null == table) {
                    return Ret.by("status", false).set("message", "订单错误");
                }
                boolean b = Db.tx(4, () -> order.delete() && table.setBelong(null).update());
                if (b) {
                    return Ret.by("status", true);
                } else {
                    return Ret.by("status", false).set("message", "未知错误");
                }
            } else if (1 == order.getType()) {
                Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", order.getPlace()));
                if (null == room) {
                    return Ret.by("status", false).set("message", "订单错误");
                }
                boolean b = Db.tx(4, () -> order.delete() && room.setBelong(null).update());
                if (b) {
                    return Ret.by("status", true).set("orderId", order.getId());
                } else {
                    return Ret.by("status", false).set("message", "未知错误");
                }
            } else {
                return Ret.by("status", false).set("message", "订单类型错误");
            }
        }
    }
}
