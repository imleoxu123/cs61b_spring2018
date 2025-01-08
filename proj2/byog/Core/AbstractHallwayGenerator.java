package byog.Core;

import java.util.ArrayList;
import java.util.Random;

public interface AbstractHallwayGenerator {

    public static int hallwayCount(Random r) {
        int num = RandomUtils.uniform(r, 30, 40);
        return num;
    }

    public static boolean isWithinRoomBounds(ArrayList<Room> roomList, Position position) {
        for (Room room : roomList) {
            int left = room.getPosition().getX();
            int right = left + room.getWeight();
            int bottom = room.getPosition().getY();
            int top = bottom + room.getHeight();

            if (position.getX() >= left && position.getX() < right &&
                    position.getY() >= bottom && position.getY() < top) {
                return true;
            }
        }
        return false;
    }

}
