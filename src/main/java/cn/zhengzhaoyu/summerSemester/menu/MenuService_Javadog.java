/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.menu;

import cn.zhengzhaoyu.summerSemester.common.model.Meal;
import com.jfinal.kit.Ret;

import java.math.BigDecimal;
import java.util.List;

/**
 * 菜单管理的相关方法
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class MenuService_Javadog {
    public static final MenuService_Javadog me = new MenuService_Javadog();
    private static final Meal mealDao = new Meal().dao();

    /**
     * 添加包间
     * @param mealName 添加的菜品名
     * @param mealPrice 添加的菜品价格
     * @param mealType 添加的菜品类型
     * @param mealRecommend 添加的菜品推荐指数
     * @return 返回添加是否成功
     */
    public Ret addMeal(String mealName, BigDecimal mealPrice, int mealType, int mealRecommend) {
        if (new Meal().setMealname(mealName).setPrice(mealPrice).setType(mealType).setRecommend(mealRecommend).save()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 删除菜品
     * @param mealId 删除的菜品id
     * @return 返回删除是否成功
     */
    public Ret removeMeal(Integer mealId) {
        Meal meal = mealDao.findFirst(mealDao.getSqlPara("meal.findById", mealId));
        if (null == meal) {
            return Ret.by("status", false);
        }
        if (meal.delete()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 修改包间信息
     * @param mealId 修改菜品id
     * @param mealName 修改后的名字
     * @param mealPrice 修改后的价格
     * @param mealType 修改后的类型
     * @param mealRecommend 修改后的推荐指数
     * @return 返回修改是否成功
     */
    public Ret editMeal(Integer mealId, String mealName, BigDecimal mealPrice, int mealType, int mealRecommend) {
        Meal meal = mealDao.findFirst(mealDao.getSqlPara("meal.findById", mealId));
        if (null == meal) {
            return Ret.by("status", false);
        }
        if (meal.setMealname(mealName).setPrice(mealPrice).setType(mealType).setRecommend(mealRecommend).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 根据名字查找包间信息
     * @param mealName 查找菜品名
     * @return 返回是否成功及菜品对象
     */
    public Ret searchMeal(String mealName) {
        Meal meal = mealDao.findFirst(mealDao.getSqlPara("meal.findByName", mealName));
        if (null == meal) {
            return Ret.by("status", false);
        }
        return Ret.by("retMeal", meal).set("status", true);
    }

    /**
     * 获取所有菜品list
     * @return 所有菜品list
     */
    public List<Meal> getAllMeals() {
        List<Meal> menu = mealDao.find(mealDao.getSqlPara("meal.getAll"));
        if (0 == menu.size()) {
            return null;
        }
        return menu;
    }
}
