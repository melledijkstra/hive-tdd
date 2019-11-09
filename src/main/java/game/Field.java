package game;

import java.util.Stack;

public class Field {

    private Stack<Tile> tiles;

    public Field(Tile tile) {
        tiles = new Stack<>();
        tiles.push(tile);
    }

    public Field(Stack<Tile> tiles) {
        this.tiles = tiles;
    }

    public Field() {
        this.tiles = new Stack<>();
    }

    public Stack<Tile> getTiles() {
        return tiles;
    }

    void addTile(Tile tile) {
        tiles.push(tile);
    }

    public Tile peek() {
        return !tiles.isEmpty() ? tiles.peek() : null;
    }

    boolean contains(Hive.TileType tileType) {
        for (Tile tile : tiles) {
            if (tile.getType() == tileType) {
                return true;
            }
        }
        return false;
    }

    public Tile removeUpperTile() {
        return tiles.pop();
    }

}
