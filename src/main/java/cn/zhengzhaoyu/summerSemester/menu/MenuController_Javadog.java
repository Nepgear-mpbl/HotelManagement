/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.menu;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Meal;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单管理页面的控制器
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class MenuController_Javadog extends BaseController_Javadog{
    private static final MenuService_Javadog rs = new MenuService_Javadog();

    /**
     * 渲染菜单管理主页
     */
    @Before({GET.class})
    public void index() {
        List<Meal> menu = rs.getAllMeals();
        setAttr("menu", menu);
        render("index.html");
    }

    /**
     * 添加新菜品
     */
    @Before({POST.class, AddMealValidator_Javadog.class})
    public void add() {
        String addMealName = getPara("addMealName");
        BigDecimal addMealPrice = new BigDecimal(getPara("addMealPrice"));
        Integer addMealType = new Integer(getPara("addMealType"));
        Integer addMealFloor = new Integer(getPara("addMealRecommend"));
        Ret ret = rs.addMeal(addMealName, addMealPrice, addMealType, addMealFloor);
        renderJson(ret);
    }

    /**
     * 删除菜品
     */
    @Before({POST.class})
    public void remove() {
        Integer removeMealId = getParaToInt();
        Ret ret = rs.removeMeal(removeMealId);
        renderJson(ret);
    }

    /**
     * 编辑菜品信息
     */
    @Before({POST.class, EditMealValidator_Javadog.class})
    public void edit() {
        Integer editMealId = getParaToInt();
        String editMealName = getPara("editMealName");
        BigDecimal editMealPrice = new BigDecimal(getPara("editMealPrice"));
        Integer editMealType = new Integer(getPara("editMealType"));
        Integer editMealRecommend = new Integer(getPara("editMealRecommend"));
        Ret ret = rs.editMeal(editMealId, editMealName, editMealPrice, editMealType, editMealRecommend);
        renderJson(ret);
    }
}
