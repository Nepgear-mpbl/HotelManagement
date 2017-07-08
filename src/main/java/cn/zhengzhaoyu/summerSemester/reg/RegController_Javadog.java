/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.reg;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.intrceptor.UserInterceptor_Javadog;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.NoUrlPara;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

/**
 * 注册页面controller
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
@Clear({UserInterceptor_Javadog.class})
@Before({NoUrlPara.class})
public class RegController_Javadog extends BaseController_Javadog {
    private static final RegService_Javadog srv = RegService_Javadog.me;

    /**
     * 渲染注册页面主页
     */
    @Before({RegInterceptor_Javadog.class, GET.class})
    public void index() {
        render("index.html");
    }

    /**
     * 注册页面的注册方法
     */
    @Before({POST.class, RegValidator_Javadog.class})
    public void doReg() {
        String username = getPara("username");
        String password = getPara("password");
        Ret ret = srv.reg(username, password);
        renderJson(ret);
    }

}
