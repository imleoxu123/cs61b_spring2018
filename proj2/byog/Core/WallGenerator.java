package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class WallGenerator {

    public static void wallGenerator(TETile[][] world, int WIDTH, int HEIGHT) {
        // 遍历整个 world
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                // 如果当前位置是 Tileset.NOTHING
                if (world[x][y] == Tileset.NOTHING) {
                    // 检查周围八个点是否有 Tileset.FLOOR
                    if (hasFloorNeighbor(world, x, y, WIDTH, HEIGHT)) {
                        world[x][y] = Tileset.WALL; // 设置为墙
                    }
                }
            }
        }
    }

    // 检查一个点周围的八个点是否有 Tileset.FLOOR
    private static boolean hasFloorNeighbor(TETile[][] world, int x, int y, int WIDTH, int HEIGHT) {
        // 定义方向数组，用于表示相邻的八个方向
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        // 遍历所有方向
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i]; // 相邻点的 x 坐标
            int ny = y + dy[i]; // 相邻点的 y 坐标

            // 检查相邻点是否在合法范围内
            if (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT) {
                // 如果相邻点是 Tileset.FLOOR，则返回 true
                if (world[nx][ny] == Tileset.FLOOR) {
                    return true;
                }
            }
        }
        return false; // 如果所有相邻点都不是 Tileset.FLOOR，返回 false
    }

}
