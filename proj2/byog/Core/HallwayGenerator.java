package byog.Core;

import java.util.ArrayList;
import java.util.Random;

public class HallwayGenerator {

    public static int hallwayCount(Random r) {
        return RandomUtils.uniform(r, 30, 40);
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

    public static boolean isHallwayNonOverlapping(ArrayList<Hallway> hallwayList, Hallway hallway) {
        if (hallwayList.isEmpty()) {
            return true;
        }

        for (Hallway existingHallway : hallwayList) {
            if (isOverlapping(existingHallway, hallway)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOverlapping(Hallway hallwayA, Hallway hallwayB) {
        return hallwayA.getPosition().getX() == hallwayB.getPosition().getX() &&
                hallwayA.getPosition().getY() == hallwayB.getPosition().getY() &&
                hallwayA.getLength() == hallwayB.getLength();
    }

}
