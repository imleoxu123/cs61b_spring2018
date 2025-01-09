package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class VerticalHallwayGenerator extends HallwayGenerator {

    public static void hallwayGenerator(TETile[][] world, ArrayList<Room> rooms, Random r, int WIDTH, int HEIGHT) {
        ArrayList<Hallway> hallwayList = new ArrayList<>();
        int num = hallwayCount(r);

        for (int i=0; i<num; i++) {
            Room startRoom = rooms.get(RandomUtils.uniform(r, rooms.size()));
            if (startRoom.getPosition().getY() + startRoom.getHeight() == WorldGenerator.getHEIGHT() - 1) {
                continue;
            }

            Hallway hallway = hallwayHelper(rooms, startRoom, r, WIDTH, HEIGHT);
            if (isHallwayNonOverlapping(hallwayList, hallway)) {
                hallwayList.add(hallway);
            }
        }

        for (Hallway hallway: hallwayList) {
            int x = hallway.getPosition().getX();
            int y = hallway.getPosition().getY();
            for (int i = 0; i < hallway.getLength(); i += 1) {
                world[x][y+i] = Tileset.FLOOR;
            }
        }
    }

    public static Hallway hallwayHelper(ArrayList<Room> rooms, Room room, Random r, int WIDTH, int HEIGHT) {
        Hallway hallway = new Hallway();
        Position p = new Position(room.getPosition().getX() + RandomUtils.uniform(r, room.getWeight()),
                room.getPosition().getY() + room.getHeight());
        hallway.setPosition(p);

        for (int l = 1; l < HEIGHT - p.getY(); l++) {
            Position tempPosition = new Position(p.getX(), p.getY() + l) ;
            if (isWithinRoomBounds(rooms, tempPosition)) {
                hallway.setLength(l);
                return hallway;
            }
        }

        hallway.setLength(RandomUtils.uniform(r, 1, HEIGHT - p.getY()));
        return hallway;
    }
}
