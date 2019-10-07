import java.util.Stack;
import java.util.stream.Collectors;

class Field {

    private Stack<Hive.Tile> stones;
    private Hive.Player color;

    Field(Hive.Tile tile, Hive.Player color) {
        stones = new Stack<>();
        stones.push(tile);
        this.color = color;
    }

    public Stack<Hive.Tile> getStones() {
        return stones;
    }

    public Hive.Player getColor() {
        return color;
    }

    public void addTile(Hive.Tile tile) {
        stones.push(tile);
    }

    @Override
    public String toString() {
        String stonesStr = stones.stream().map(Object::toString).collect(Collectors.joining(","));
        String color = this.color == Hive.Player.BLACK ? "B" : "W";
        return String.format("[%s] size: %d, color: %s", stonesStr, stones.size(), color);
    }
}
