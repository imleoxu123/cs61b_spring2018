package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class HorizontalHallwayGenerator extends HallwayGenerator {

    public static void hallwayGenerator(TETile[][] world, ArrayList<Room> rooms, Random r, int WIDTH, int HEIGHT) {
        ArrayList<Hallway> HallwayList = new ArrayList<>();
        int num = hallwayCount(r);

        for (int i=0; i<num; i++) {
            Room startRoom = rooms.get(RandomUtils.uniform(r, rooms.size()));
            if (startRoom.getPosition().getX() + startRoom.getWeight() == WorldGenerator.getWEIGTH() - 1) {
                continue;
            }

            Hallway hallway = hallwayHelper(rooms, startRoom, r, WIDTH, HEIGHT);
            if (isHallwayNonOverlapping(HallwayList, hallway)) {
                HallwayList.add(hallway);
            }
        }

        for (Hallway hallway: HallwayList) {
            int x = hallway.getPosition().getX();
            int y = hallway.getPosition().getY();
            for (int i = 0; i < hallway.getLength(); i += 1) {
                world[x+i][y] = Tileset.FLOOR;
            }
        }
    }

    public static Hallway hallwayHelper(ArrayList<Room> rooms, Room room, Random r, int WIDTH, int HEIGHT) {
        Hallway hallway = new Hallway();

        Position p = new Position(room.getPosition().getX() + room.getWeight(),
                room.getPosition().getY() + RandomUtils.uniform(r, room.getHeight()));
        hallway.setPosition(p);

        for (int l = 1; l < WIDTH - p.getX(); l++) {
            Position tempPosition = new Position(p.getX() + l, p.getY());
            if (isWithinRoomBounds(rooms, tempPosition)) {
                hallway.setLength(l);
                return hallway;
            }
        }

        hallway.setLength(RandomUtils.uniform(r, 1, WIDTH - p.getX()));
        return hallway;
    }

}
