/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.login;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.intrceptor.UserInterceptor_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.ActionKey;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.NoUrlPara;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.redis.Redis;

/**
 * 登录页面controller
 *
 * @author ZZY
 * @version 1.0.1
 * @since 1.0.0
 */
@Before({NoUrlPara.class})
public class LoginController_Javadog extends BaseController_Javadog {
    private static final LoginService_Javadog srv = LoginService_Javadog.me;

    /**
     * 渲染登录页面主页
     */
    @Clear({UserInterceptor_Javadog.class})
    @Before({LoginInterceptor_Javadog.class,GET.class})
    public void index() {
        render("index.html");
    }

    /**
     * 渲染更改信息页面主页
     */
    @Before({LoginInterceptor_Javadog.class,GET.class})
    public void changeInfo() {
        render("changeInfo.html");
    }


    /**
     * 登录页面的登录服务
     */
    @Clear({UserInterceptor_Javadog.class})
    @Before({POST.class, LoginValidator_Javadog.class})
    public void doLogin() {
        String username = getPara("username");
        String password = getPara("password");
        Ret ret = srv.login(username, password);
        if (ret.getBoolean("status")) {
            setCookie(LoginService_Javadog.SESSION_ID_NAME, ret.getStr("token"), 60 * 60);
        }
        renderJson(ret);
    }

    /**
     * 更改信息页面更改密码
     */
    @Before({POST.class, ChangePwdValidator_Javadog.class})
    public void changePwd() {
        String token = getCookie(LoginService_Javadog.SESSION_ID_NAME);
        User user = Redis.use().get(token);
        Integer userId=user.getId();
        String oldPassword = getPara("oldPassword");
        String newPassword = getPara("newPassword");
        String repeatNewPassword = getPara("repeatNewPassword");
        if(!newPassword.equals(repeatNewPassword)){
            renderJson(Ret.by("status",false).set("message","两次新密码不匹配！"));
        }else{
            Ret ret = srv.changePwd(userId,newPassword,oldPassword);
            removeCookie(LoginService_Javadog.SESSION_ID_NAME);
            renderJson(ret);
        }
    }

    /**
     * 登录页面的验证码获取服务
     */
    @Clear({UserInterceptor_Javadog.class})
    @Before({GET.class})
    public void captcha() {
        renderCaptcha();
    }

    /**
     * 登出服务
     */
    @ActionKey("/logout")
    public void logout() {
        removeCookie(LoginService_Javadog.SESSION_ID_NAME);
        redirect("/login");
    }
}
