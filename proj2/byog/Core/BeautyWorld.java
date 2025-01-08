package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * Draws a beautiful world contains RANDOM rooms and hallways.
 */
public class BeautyWorld {
    private static int WIDTH = 70;
    private static int HEIGHT = 30;

    private static long seed;
    private static Random RANDOM = new Random(seed);

    public static int getWEIGTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        seed = 999999999;

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // initialize rooms
        ArrayList<Room> rooms = RoomGenerator.roomGenerator(RANDOM);
        for (Room room: rooms) {
            int x = room.getPosition().getX();
            int y = room.getPosition().getY();
            for (int i = 0; i < room.getWeight(); i += 1) {
                for (int j = 0; j < room.getHeight(); j += 1) {
                    world[x+i][y+j] = Tileset.FLOOR;
                }
            }
        }


        ArrayList<Hallway> whallways = HorizontalHallwayGenerator.HallwayGenerator(rooms, RANDOM);
        for (Hallway whallway: whallways) {
            int x = whallway.getPosition().getX();
            int y = whallway.getPosition().getY();
            for (int i = 0; i < whallway.getLength(); i += 1) {
                world[x+i][y] = Tileset.FLOOR;
            }
        }


        ArrayList<Hallway> vhallways = VerticalHallwayGenerator.HallwayGenerator(rooms, RANDOM);
        for (Hallway vhallway: vhallways) {
            int x = vhallway.getPosition().getX();
            int y = vhallway.getPosition().getY();
            for (int i = 0; i < vhallway.getLength(); i += 1) {
                world[x][y+i] = Tileset.FLOOR;
            }
        }


        // worldmanaer


        // draws the world to the screen
        ter.renderFrame(world);
    }
}
