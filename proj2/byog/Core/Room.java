package byog.Core;

import java.util.Random;

public class Room {

    private Position position;
    private int weight;
    private int height;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public static Room roomHelper(Random r) {
        Room room = new Room();
        Position p = new Position(RandomUtils.uniform(r, 0, BeautyWorld.getWEIGTH()), RandomUtils.uniform(r, 0, BeautyWorld.getHEIGHT()));

        room.setPosition(p);
        room.setWeight(RandomUtils.uniform(r, 3, 6));
        room.setHeight(RandomUtils.uniform(r, 3, 6));

        return room;
    }

}
