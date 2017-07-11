/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.room;

import cn.zhengzhaoyu.summerSemester.common.controller.BaseController_Javadog;
import cn.zhengzhaoyu.summerSemester.common.model.Room;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.Ret;

import java.math.BigDecimal;
import java.util.List;

/**
 * 包间管理的控制器
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class RoomController_Javadog extends BaseController_Javadog {
    private static final RoomService_Javadog rs = new RoomService_Javadog();

    /**
     * 渲染包间管理主页
     */
    @Before({GET.class})
    public void index() {
        List<Room> roomList = rs.getAllRooms();
        setAttr("roomList", roomList);
        render("index.html");
    }

    /**
     * 添加新包间
     */
    @Before({POST.class, AddRoomValidator_Javadog.class})
    public void add() {
        String addRoomName = getPara("addRoomName");
        BigDecimal addRoomPrice = new BigDecimal(getPara("addRoomPrice"));
        Integer addRoomType = new Integer(getPara("addRoomType"));
        Integer addRoomFloor = new Integer(getPara("addRoomFloor"));
        Ret ret = rs.addRoom(addRoomName, addRoomPrice, addRoomType, addRoomFloor);
        renderJson(ret);
    }

    /**
     * 删除包间
     */
    @Before({POST.class})
    public void remove() {
        Integer removeRoomId = getParaToInt();
        Ret ret = rs.removeRoom(removeRoomId);
        renderJson(ret);
    }

    /**
     * 编辑包间信息
     */
    @Before({POST.class, EditRoomValidator_Javadog.class})
    public void edit() {
        Integer editRoomId = getParaToInt();
        String editRoomName = getPara("editRoomName");
        BigDecimal editRoomPrice = new BigDecimal(getPara("editRoomPrice"));
        Integer editRoomType = new Integer(getPara("editRoomType"));
        Integer editRoomFloor = new Integer(getPara("editRoomFloor"));
        Ret ret = rs.editRoom(editRoomId, editRoomName, editRoomPrice, editRoomType, editRoomFloor);
        renderJson(ret);
    }
}
