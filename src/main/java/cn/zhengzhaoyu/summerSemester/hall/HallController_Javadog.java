/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.hall;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Table;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * 大厅管理的控制器
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class HallController_Javadog extends BaseController_Javadog {
    private static final HallService_Javadog hs = new HallService_Javadog();

    /**
     * 渲染主页页面
     */
    public void index() {
        List<Table> usedList = hs.getTables(true);
        List<Table> unusedList = hs.getTables(false);
        setAttr("usedList", usedList);
        setAttr("unusedList", unusedList);
        render("index.html");
    }

    /**
     * 添加桌子
     */
    @Before({POST.class})
    public void add() {
        Integer tableNum=getParaToInt("addTableNum");
        Ret ret = hs.addTable(tableNum);
        renderJson(ret);
    }

    /**
     * 删除对应id桌子
     */
    @Before({POST.class})
    public void remove() {
        Integer removeMealId = getParaToInt();
        Ret ret = hs.removeTable(removeMealId);
        renderJson(ret);
    }
}
