package byog.Core;

import java.util.ArrayList;
import java.util.Random;

public class HallwayGenerator {

    public static ArrayList<Hallway> horizontalHallwayGenerator(ArrayList<Room> rooms, Random r) {
        ArrayList<Hallway> horizontalHallwayList = new ArrayList<>();
        int num = hallwayCount(r);

        for (int i=0; i<num; i++) {
            Room startRoom = rooms.get(RandomUtils.uniform(r, rooms.size()));
            Hallway hallway = horizontalHallwayHelper(rooms, startRoom, r);
            horizontalHallwayList.add(hallway);
        }

        return horizontalHallwayList;
    }

    public static ArrayList<Hallway> verticalHallwayGenerator(ArrayList<Room> rooms, Random r) {
        ArrayList<Hallway> verticalHallwayList = new ArrayList<>();
        int num = hallwayCount(r);

        for (int i=0; i<num; i++) {
            Room startRoom = rooms.get(RandomUtils.uniform(r, rooms.size()));
            Hallway hallway = verticalHallwayHelper(rooms, startRoom, r);
            verticalHallwayList.add(hallway);
        }

        return verticalHallwayList;
    }


    public static Hallway horizontalHallwayHelper(ArrayList<Room> rooms, Room room, Random r) {
        Hallway hallway = new Hallway();
        Position p = new Position(room.getPosition().getX() + room.getWeight(), room.getPosition().getY() + RandomUtils.uniform(r, room.getHeight()));
        hallway.setPosition(p);

        for (int l=0; l<=BeautyWorld.getWEIGTH() - p.getX(); l++) {
            Position tempPosition = new Position(p.getX() + l, p.getY()) ;
            if (isWithinRoomBounds(rooms, tempPosition)) {
                hallway.setLength(l);
                return hallway;
            }
        }

        hallway.setLength(RandomUtils.uniform(r, BeautyWorld.getWEIGTH() - p.getX()));
        return hallway;
    }

    public static Hallway verticalHallwayHelper(ArrayList<Room> rooms, Room room, Random r) {
        Hallway hallway = new Hallway();
        Position p = new Position(room.getPosition().getX() + room.getWeight(), room.getPosition().getY() + RandomUtils.uniform(r, room.getHeight()));
        hallway.setPosition(p);

        for (int l=0; l<=BeautyWorld.getHEIGHT() - p.getY(); l++) {
            Position tempPosition = new Position(p.getX(), p.getY() + l) ;
            if (isWithinRoomBounds(rooms, tempPosition)) {
                hallway.setLength(l);
                return hallway;
            }
        }

        hallway.setLength(RandomUtils.uniform(r, BeautyWorld.getHEIGHT() - p.getY()));
        return hallway;
    }

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
