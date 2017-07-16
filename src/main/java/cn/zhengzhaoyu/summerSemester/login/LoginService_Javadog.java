/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.login;

import cn.zhengzhaoyu.summerSemester.common.model.User;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.redis.Redis;

/**
 * 登录页面的相关操作
 * @author ZZY
 * @version 1.0.1
 * @since  1.0.0
 */
public class LoginService_Javadog {
    public static final LoginService_Javadog me = new LoginService_Javadog();
    public static final String SESSION_ID_NAME = "loginToken";
    private static final User userDao = new User().dao();

    /**
     *验证用户名密码是否匹配并设置token
     * @param username 用户名
     * @param password 密码
     * @return 包含登录状态及token的json
     */
    public Ret login(String username, String password) {
        User user = userDao.findFirst(userDao.getSqlPara("user.findByName", username));
        if (null == user) {
            return Ret.by("status", false).set("message", "用户名或密码错误!");
        }

        String matchPwd = user.getPwd();
        if (!matchPwd.equals(HashKit.sha256(user.getSalt() + password))) {
            return Ret.by("status", false).set("message", "用户名或密码错误!");
        }
        String token = StrKit.getRandomUUID();
        user.setPwd(null).setSalt(null);
        Redis.use().set(token, user);
        return Ret.by("status", true).set("token", token);
    }

    /**
     * 更改用户密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     * @return 是否成功
     */
    public Ret changePwd(Integer userId,String newPassword,String oldPassword){
        User user = userDao.findFirst(userDao.getSqlPara("user.findById", userId));
        if (null == user) {
            return Ret.by("status", false).set("message", "用户不存在!");
        }
        String matchPwd = user.getPwd();
        if (!matchPwd.equals(HashKit.sha256(user.getSalt() + oldPassword))) {
            return Ret.by("status", false).set("message", "旧密码错误!");
        }
        if(user.setPwd(HashKit.sha256(user.getSalt() + newPassword)).update()){
            return Ret.by("status", true);
        }else {
            return Ret.by("status", false).set("message", "未知错误!");
        }
    }
}
