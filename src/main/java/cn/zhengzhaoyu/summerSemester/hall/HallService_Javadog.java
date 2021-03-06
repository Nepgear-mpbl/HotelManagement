/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.hall;

import cn.zhengzhaoyu.summerSemester.common.model.Table;
import com.jfinal.kit.Ret;

import java.util.List;

/**
 * 大厅管理服务.
 * @author ZZY
 * @version 1.0.1
 * @since 1.0.0
 */
public class HallService_Javadog {
    public static final HallService_Javadog me = new HallService_Javadog();
    private static final Table tableDao = new Table().dao();

    /**
     * 添加桌子
     * @param tableNum 添加的桌子编号
     * @param tableSize 添加的桌子座位数
     * @return 返回是否成功
     */
    public Ret addTable(Integer tableNum,Integer tableSize) {
        if (new Table().setTableNum(tableNum).setSeat(tableSize).save()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 删除对应id的桌子
     * @param tableId 删除的table id
     * @return 返回是否成功
     */
    public Ret removeTable(Integer tableId) {
        Table table = tableDao.findFirst(tableDao.getSqlPara("table.findById", tableId));
        if (null == table) {
            return Ret.by("status", false);
        }
        if (null != table.getBelong()) {
            return Ret.by("status", false).set("message","正在使用中，无法删除");
        }
        if (table.delete()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 返回所有已使用/未使用的table list
     * @param isUsed 返回的种类
     * @return 返回是否成功
     */
    public List<Table> getTables(boolean isUsed) {
        List<Table> tables = null;
        if (isUsed) {
            tables = tableDao.find(tableDao.getSqlPara("table.getUsed"));
        } else {
            tables = tableDao.find(tableDao.getSqlPara("table.getUnused"));
        }
        if (0 == tables.size()) {
            return null;
        }
        return tables;
    }
    public List<Table> getUnusedTables(int minSize) {
        List<Table> tableList = tableDao.find(tableDao.getSqlPara("table.getUnusedWithMinSize",minSize));
        if (0 == tableList.size()) {
            return null;
        }
        return tableList;
    }
}
