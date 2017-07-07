/*
 * Copyright (c) 2017 JavaDog
 */
package cn.zhengzhaoyu.summerSemester.common;

import cn.zhengzhaoyu.summerSemester.index.IndexController_Javadog;
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
    }
}
