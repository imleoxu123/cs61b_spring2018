package byog.Core;

import java.util.ArrayList;
import java.util.Random;

public class RoomGenerator {
    /**
     * 生成一个包含随机房间的列表，并确保房间不重叠。
     *
     * @param r 随机数生成器
     * @return 一个包含房间对象的列表
     */
    public static ArrayList<Room> roomGenerator(Random r) {
        ArrayList<Room> roomList = new ArrayList<>();
        int num = RandomUtils.uniform(r, 100, 200);

        for(int i=0; i<num; i++) {
            Room room = Room.roomHelper(r);
            if (isRoomValid(roomList, room)) {
                roomList.add(room);
            }
        }

        return roomList;
    }

    /**
     * 确保房间列表中的所有房间对象不重叠。
     *
     * @param roomList 房间对象的列表
     */
    private static boolean isRoomValid(ArrayList<Room> roomList, Room room) {
        return isInworld(room) && isRoomNonOverlapping(roomList, room);
    }

    /** 检查房间对象是否位于BeautyWorld中 */
    private static boolean isInworld(Room room) {
        return room.getPosition().getX() + room.getWeight() <= BeautyWorld.getWEIGTH() && room.getPosition().getY() + room.getHeight() <= BeautyWorld.getHEIGHT();
    }

    /**
     * 检查房间与列表中的房间对象不重叠。
     *
     * @param roomList 房间对象的列表
     * @param room 房间对象
     * @return 如果房间与列表中的所有房间没有重叠，返回true；否则返回false
     */
    private static boolean isRoomNonOverlapping(ArrayList<Room> roomList, Room room) {
        if (roomList.isEmpty()) {
            return true;
        }

        for (Room existingRoom : roomList) {
            if (isOverlapping(existingRoom, room)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查两个房间对象是否重叠。
     *
     * @param roomA 第一个房间对象
     * @param roomB 第二个房间对象
     * @return 如果两个房间重叠，返回true；否则返回false
     */
    private static boolean isOverlapping(Room roomA, Room roomB) {
        // 房间A的边界计算
        int leftA = roomA.getPosition().getX();
        int rightA = leftA + roomA.getWeight();
        int bottomA = roomA.getPosition().getY();
        int topA = bottomA + roomA.getHeight();

        // 房间B的边界计算
        int leftB = roomB.getPosition().getX();
        int rightB = leftB + roomB.getWeight();
        int bottomB = roomB.getPosition().getY();
        int topB = bottomB + roomB.getHeight();

        // 判断两个房间是否重叠
        return !(rightA <= leftB || rightB <= leftA || topA <= bottomB || topB <= bottomA);
    }
}
