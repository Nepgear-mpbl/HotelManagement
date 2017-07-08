/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.reg;

import cn.zhengzhaoyu.summerSemester.common.model.User;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

/**
 * 注册页面调用的方法类
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class RegService_Javadog {
    public static final RegService_Javadog me = new RegService_Javadog();
    private static final User userDao = new User().dao();

    /**
     * 注册并保存账号信息到数据库
     * @param username 用户名
     * @param password 密码
     * @return 包含操作是否成功与信息的json
     */
    public Ret reg(String username, String password) {
        User user = userDao.findFirst(userDao.getSqlPara("user.findByName", username));
        if (user == null) {
            String salt = StrKit.getRandomUUID();
            String pwd = HashKit.sha256(salt + password);
            if (new User().setUsername(username).setPwd(pwd).setSalt(salt).save()) {
                return Ret.by("status", true);
            } else {
                return Ret.fail("status", false).set("message", "注册失败!");
            }
        } else {
            return Ret.fail("status", false).set("message", "用户名已经存在!");
        }
    }
}
