import java.util.Stack;
import java.util.stream.Collectors;

class Field {

    private Stack<Hive.Tile> stones;

    Field(Hive.Tile tile) {
        stones = new Stack<>();
        stones.push(tile);
    }

    public Stack<Hive.Tile> getStones() {
        return stones;
    }

    @Override
    public String toString() {
        String stonesstr = stones.stream().map(Object::toString).collect(Collectors.joining(","));
        return String.format("[%s] size: %d", stonesstr, stones.size());
    }
}
