/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.common.intrceptor;

import cn.zhengzhaoyu.summerSemester.common.model.User;
import cn.zhengzhaoyu.summerSemester.login.LoginService_Javadog;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.redis.Redis;

/**
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.2
 */
public class UserInterceptorNoDirect_Javadog implements Interceptor{
    /**
     * 判断页面获取的token是否存在，若不存在则跳转
     */
    @Override
    public void intercept(Invocation inv) {
        Controller c = inv.getController();
        String token = c.getCookie(LoginService_Javadog.SESSION_ID_NAME);
        User user = token == null ? null : Redis.use().get(token);
        if (null != user) {
            c.setAttr("user", user);
        }
        inv.invoke();
    }
}
