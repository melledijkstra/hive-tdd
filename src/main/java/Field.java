import java.util.Stack;
import java.util.stream.Collectors;

class Field {

    private Stack<Hive.TileType> stones;

    Field(Hive.TileType tileType) {
        stones = new Stack<>();
        stones.push(tileType);
    }

    public Stack<Hive.TileType> getStones() {
        return stones;
    }

    public void addTile(Hive.TileType tileType) {
        stones.push(tileType);
    }

    @Override
    public String toString() {
        String stonesStr = stones.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("[%s] size: %d", stonesStr, stones.size());
    }
}
