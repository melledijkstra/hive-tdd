import java.util.Stack;

public class Tile {

    private Hive.Player color;

    private Hive.TileType type;

    public Tile(Hive.Player color, Hive.TileType type) {
        this.color = color;
        this.type = type;
    }

    public Hive.Player getColor() {
        return this.color;
    }

    public Hive.TileType getType() {
        return type;
    }

}
