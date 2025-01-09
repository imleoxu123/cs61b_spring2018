package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.tree.FixedHeightLayoutCache;
import java.util.ArrayList;
import java.util.Random;

//
public class ConnectGenerator {

    public static void connectGenerator(TETile[][] world, Random r, int WIDTH, int HEIGHT) {

        ArrayList<Position> totalFloors = new ArrayList<>();
        ArrayList<Position> connected = new ArrayList<>();
        ArrayList<Position> disconnected = new ArrayList<>();

        while(true) {
            totalFloors.clear();
            connected.clear();
            disconnected.clear();
            countTotalFloor(world, totalFloors);
            Position randomFloor = totalFloors.get(RandomUtils.uniform(r, totalFloors.size()));
            findConnected(world, WIDTH, HEIGHT, randomFloor, connected);
            findDisconnected(totalFloors, connected, disconnected);

            if (disconnected.isEmpty()) {
                return;
            }

            createConnected(world, connected, disconnected, r);
        }
    }

    public static void countTotalFloor(TETile[][] world, ArrayList<Position> totalFloors) {
        for (int i = 0; i < world.length; i++) { // 遍历每一行
            for (int j = 0; j < world[i].length; j++) { // 遍历每一列
                // 检查当前点是否是 Tileset.FLOOR
                if (world[i][j] == Tileset.FLOOR) {
                    // 创建 Position 对象并加入 totalFloors
                    Position p = new Position(i, j); // 初始化 Position 对象，传入 x 和 y 坐标
                    totalFloors.add(p); // 添加到列表中
                }
            }
        }
    }

    public static void findDisconnected(ArrayList<Position> totalFloors, ArrayList<Position> connected, ArrayList<Position> disconnected) {
        // 遍历 totalFloors 列表
        for (Position floor : totalFloors) {
            // 如果 connected 中不包含当前 floor，则将其加入 disconnected
            if (!connected.contains(floor)) {
                disconnected.add(floor);
            }
        }

    }

    public static void findConnected(TETile[][] world, int WIDTH, int HEIGHT, Position p, ArrayList<Position> connected) {

        if (p.getX() < 0 || p.getX() >= WIDTH || p.getY() < 0 || p.getY() >= HEIGHT) {
            return; // 边界检查，防止越界访问
        }

        if (world[p.getX()][p.getY()] == Tileset.NOTHING || connected.contains(p)) {
            return; // 非地板点或已访问过，结束递归
        }

            // 添加当前点到 connected
        connected.add(p);

        // 递归搜索相邻点
        findConnected(world, WIDTH, HEIGHT, new Position(p.getX(), p.getY() + 1), connected); // 上
        findConnected(world, WIDTH, HEIGHT, new Position(p.getX(), p.getY() - 1), connected); // 下
        findConnected(world, WIDTH, HEIGHT, new Position(p.getX() - 1, p.getY()), connected); // 左
        findConnected(world, WIDTH, HEIGHT, new Position(p.getX() + 1, p.getY()), connected); // 右
    }

    public static void addConnected(ArrayList<Position> connected, Position p) {
        // 检查 p 是否已存在于 connected 列表中
        if (connected.isEmpty()) {
            connected.add(p);
            return;
        }

        for (Position connectedPosition : connected) {
            if (connectedPosition.equals(p)) {
                return; // 如果已存在，直接返回
            }
        }

        // 如果列表中不存在 p，则添加
        connected.add(p);
    }


    public static void createConnected(TETile[][] world, ArrayList<Position> connected, ArrayList<Position> disconnected, Random r) {
        if (disconnected.isEmpty() || connected.isEmpty()) {
            return; // 如果没有未连接点或已连接点，则直接返回
        }

        Position randomDisconnectedPoint = disconnected.get(RandomUtils.uniform(r, disconnected.size()));

        // 找到 connected 中最近的点
        Position nearestConnectedPoint = findNearestConnectedPoint(connected, randomDisconnectedPoint);

        // 找到从 nearestConnectedPoint 到 randomDisconnectedPoint 的方向，并生成路径
        ArrayList<Position> newConnection = generateConnection(nearestConnectedPoint, randomDisconnectedPoint);

        for (Position p : newConnection) {
            if (world[p.getX()][p.getY()] != Tileset.FLOOR) {
                world[p.getX()][p.getY()] = Tileset.FLOOR; // 在 world 上标记为 FLOOR
            }
        }
    }

    // 找到 disconnected 中挑选的随机点与 connected 中距离最近的点
    private static Position findNearestConnectedPoint(ArrayList<Position> connected, Position target) {
        Position nearest = null;
        int minDistance = Integer.MAX_VALUE;

        // 遍历 connected 中的每个点
        for (Position point : connected) {
            int distance = calculateManhattanDistance(point, target); // 计算曼哈顿距离
            if (distance < minDistance) {
                minDistance = distance;
                nearest = point;
            }
        }
        return nearest;
    }

    // 生成路径，路径只能上下和左右组合形成
    private static ArrayList<Position> generateConnection(Position start, Position end) {
        ArrayList<Position> path = new ArrayList<>();

        // 获取起点和终点的坐标
        int x = start.getX();
        int y = start.getY();
        int targetX = end.getX();
        int targetY = end.getY();

        // 先判断是否直接相邻（上下或左右）
        if (x == targetX) { // 直接上下连接
            int minY = Math.min(y, targetY);
            int maxY = Math.max(y, targetY);
            for (int i = minY + 1; i < maxY; i++) {
                path.add(new Position(x, i));
            }
        } else if (y == targetY) { // 直接左右连接
            int minX = Math.min(x, targetX);
            int maxX = Math.max(x, targetX);
            for (int i = minX + 1; i < maxX; i++) {
                path.add(new Position(i, y));
            }
        } else {
            // 需要组合上下和左右连接
            // 水平移动到目标 x 坐标
            while (x != targetX) {
                if (x < targetX) {
                    x++;
                } else {
                    x--;
                }
                path.add(new Position(x, y));
            }

            // 垂直移动到目标 y 坐标
            while (y != targetY) {
                if (y < targetY) {
                    y++;
                } else {
                    y--;
                }
                path.add(new Position(x, y));
            }
        }

        return path;
    }

    private static int calculateManhattanDistance(Position p1, Position p2) {
        return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
    }

}
