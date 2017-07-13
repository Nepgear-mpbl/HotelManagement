/*
 * Copyright (c) 2017 JavaDog
 */
package cn.zhengzhaoyu.summerSemester.common;

import cn.zhengzhaoyu.summerSemester.hall.HallController_Javadog;
import cn.zhengzhaoyu.summerSemester.index.IndexController_Javadog;
import cn.zhengzhaoyu.summerSemester.login.LoginController_Javadog;
import cn.zhengzhaoyu.summerSemester.menu.MenuController_Javadog;
import cn.zhengzhaoyu.summerSemester.order.OrderController_Javadog;
import cn.zhengzhaoyu.summerSemester.reg.RegController_Javadog;
import cn.zhengzhaoyu.summerSemester.room.RoomController_Javadog;
import com.jfinal.config.Routes;

/**
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class FrontRoute_Javadog extends Routes {
    /**
     * @see Routes#config()
     */
    @Override
    public void config() {
        setBaseViewPath("_view");
        add("/", IndexController_Javadog.class, "/index");
        add("login", LoginController_Javadog.class);
        add("reg", RegController_Javadog.class);
        add("room", RoomController_Javadog.class);
        add("menu", MenuController_Javadog.class);
        add("hall", HallController_Javadog.class);
        add("order", OrderController_Javadog.class);
    }
}
