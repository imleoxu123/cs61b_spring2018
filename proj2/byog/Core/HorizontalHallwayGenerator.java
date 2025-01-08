package byog.Core;

import java.util.ArrayList;
import java.util.Random;

public class HorizontalHallwayGenerator implements AbstractHallwayGenerator {
    public static ArrayList<Hallway> HallwayGenerator(ArrayList<Room> rooms, Random r) {
        ArrayList<Hallway> HallwayList = new ArrayList<>();
        int num = AbstractHallwayGenerator.hallwayCount(r);

        for (int i=0; i<num; i++) {
            Room startRoom = rooms.get(RandomUtils.uniform(r, rooms.size()));
            if (startRoom.getPosition().getX() + startRoom.getWeight() == BeautyWorld.getWEIGTH()) {
                continue;
            }
            Hallway hallway = HallwayHelper(rooms, startRoom, r);
            //TODO：确保hallway不重复

            HallwayList.add(hallway);
        }

        return HallwayList;
    }

    public static Hallway HallwayHelper(ArrayList<Room> rooms, Room room, Random r) {
        Hallway hallway = new Hallway();

        Position p = new Position(room.getPosition().getX() + room.getWeight(),
                room.getPosition().getY() + RandomUtils.uniform(r, room.getHeight()));
        hallway.setPosition(p);

        if (p.getX() == BeautyWorld.getWEIGTH() - 1) {
            hallway.setLength(1);
            return hallway;
        }

        for (int l=1; l<=BeautyWorld.getWEIGTH() - p.getX(); l++) {
            Position tempPosition = new Position(p.getX() + l, p.getY());
            if (AbstractHallwayGenerator.isWithinRoomBounds(rooms, tempPosition)) {
                hallway.setLength(l);
                return hallway;
            }
        }

        hallway.setLength(RandomUtils.uniform(r, 1, BeautyWorld.getWEIGTH() - p.getX() + 1));
        return hallway;
    }

}
