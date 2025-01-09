package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * Draws a beautiful world contains RANDOM rooms and hallways.
 */
public class WorldGenerator {
    private static int WIDTH = 70;
    private static int HEIGHT = 30;

    private static long seed = 88889999;
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

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // initialize rooms
        ArrayList<Room> roomList = RoomGenerator.roomGenerator(world, RANDOM, WIDTH, HEIGHT);

        HorizontalHallwayGenerator.hallwayGenerator(world, roomList, RANDOM, WIDTH, HEIGHT);

        VerticalHallwayGenerator.hallwayGenerator(world, roomList, RANDOM, WIDTH, HEIGHT);

        ConnectGenerator.connectGenerator(world, RANDOM, WIDTH, HEIGHT);

        WallGenerator.wallGenerator(world, WIDTH, HEIGHT);

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
