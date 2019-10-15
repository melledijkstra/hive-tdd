import java.util.Stack;

public class Tile {

    private Hive.Player color;

    private Stack<Hive.TileType> tiles;

    public Tile(Hive.Player color, Hive.TileType type) {
        this.color = color;
        this.tiles = new Stack<>();
        this.tiles.push(type);
    }

    public Hive.Player getColor() {
        return this.color;
    }

    public Hive.TileType getTopTile() {
        return tiles.peek();
    }

    public Stack<Hive.TileType> getTiles() {
        return tiles;
    }

    void addTile(Hive.TileType tile) {
        tiles.push(tile);
    }

}
