import java.util.Stack;

class Field {

    private Stack<Tile> tiles;

    Field(Tile tile) {
        tiles = new Stack<>();
        tiles.push(tile);
    }

    Field(Stack<Tile> tiles) {
        this.tiles = tiles;
    }

    Field() {
        this.tiles = new Stack<>();
    }

    public Stack<Tile> getTiles() {
        return tiles;
    }

    void addTile(Tile tile) {
        tiles.push(tile);
    }

    Tile peek() {
        return tiles.peek();
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
