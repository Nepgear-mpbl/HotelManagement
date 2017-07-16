/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.index;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;

/**
 * 主页页面的controller
 * @author ZZY
 * @version 1.0.1
 * @since  1.0.0
 */
public class IndexController_Javadog extends BaseController_Javadog{
    /**
     * 渲染主页的页面
     */
    public void index() {
        render("index.html");
    }
    /**
     * 渲染关于的页面
     */
    public void about() {
        render("about.html");
    }
}
