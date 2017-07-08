/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.reg;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册页面的请求拦截器
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegInterceptor_Javadog implements Interceptor{
    /**
     * 拦截所有发往reg的post请求并重定向
     * @param inv 当前的操作
     */
    @Override
    public void intercept(Invocation inv) {
        Controller c=inv.getController();
        HttpServletRequest request=c.getRequest();
        String method=request.getMethod();
        if("POST".equalsIgnoreCase(method)){
            c.forwardAction("/reg/doReg");
        }else {
            inv.invoke();
        }
    }
}
