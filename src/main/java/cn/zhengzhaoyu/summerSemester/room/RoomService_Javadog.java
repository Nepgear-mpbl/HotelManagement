/*
 * Copyright (c) 2017 JavaDog
 */

package cn.zhengzhaoyu.summerSemester.room;

import cn.zhengzhaoyu.summerSemester.common.model.Room;
import com.jfinal.kit.Ret;

import java.math.BigDecimal;
import java.util.List;

/**
 * 对包间的管理方法
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class RoomService_Javadog {
    public static final RoomService_Javadog me = new RoomService_Javadog();
    private static final Room roomDao = new Room().dao();

    /**
     * 添加包间
     * @param roomName 添加的包间名
     * @param roomPrice 添加的包间价格
     * @param roomType 添加的包间类型
     * @param roomFloor 添加的包间楼层
     * @return 返回添加是否成功
     */
    public Ret addRoom(String roomName, BigDecimal roomPrice, int roomType, int roomFloor) {
        if (new Room().setRoomname(roomName).setPrice(roomPrice).setType(roomType).setFloor(roomFloor).save()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 删除包间信息
     * @param roomId 删除的包间id
     * @return 返回删除是否成功
     */
    public Ret removeRoom(Integer roomId) {
        Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", roomId));
        if (null == room) {
            return Ret.by("status", false);
        }
        if(null!=room.getBelong()){
            return Ret.by("status", false).set("message","该包间正在使用中");
        }
        if (room.delete()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 修改包间信息
     * @param roomId 修改包间id
     * @param roomName 修改后的名字
     * @param roomPrice 修改后的价格
     * @param roomType 修改后的类型
     * @param roomFloor 修改后的楼层
     * @return 返回修改是否成功
     */
    public Ret editRoom(Integer roomId, String roomName, BigDecimal roomPrice, int roomType, int roomFloor) {
        Room room = roomDao.findFirst(roomDao.getSqlPara("room.findById", roomId));
        if (null == room) {
            return Ret.by("status", false);
        }
        if (room.setRoomname(roomName).setPrice(roomPrice).setType(roomType).setFloor(roomFloor).update()) {
            return Ret.by("status", true);
        } else {
            return Ret.by("status", false);
        }
    }

    /**
     * 根据名字查找包间信息
     * @param roomName 查找包间名
     * @return 返回是否成功及包间对象
     */
    public Ret searchRoom(String roomName) {
        Room room = roomDao.findFirst(roomDao.getSqlPara("room.findByName", roomName));
        if (null == room) {
            return Ret.by("status", false);
        }
        return Ret.by("retRoom", room).set("status", true);
    }

    /**
     * 获取所有包间list
     * @return 所有包间list
     */
    public List<Room> getAllRooms() {
        List<Room> roomList = roomDao.find(roomDao.getSqlPara("room.getAll"));
        if (0 == roomList.size()) {
            return null;
        }
        return roomList;
    }
    public List<Room> getUnusedRooms(int minSize) {
        int roomSize=(int)(minSize/10);
        List<Room> roomList = roomDao.find(roomDao.getSqlPara("room.getUnusedWithMinSize",roomSize));
        if (0 == roomList.size()) {
            return null;
        }
        return roomList;
    }
}
