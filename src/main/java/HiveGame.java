import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains the main Hive game data
 */
public class HiveGame implements Hive {

    private Player currentPlayer;

    private HashMap<Coordinate, Tile> board = new HashMap<>();

    @Override
    public void play(TileType tileType, int q, int r) throws IllegalMove {
        Tile tile = board.get(new Coordinate(0, 0));
        if (tile == null) {
            board.put(new Coordinate(q, r), new Tile(Player.WHITE, tileType));
        } else {
            tile.addTile(tileType);
        }
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        Coordinate from = new Coordinate(fromQ, fromR);
        Tile tile = board.get(from); // retrieve the tile from the board
        board.remove(from); // delete tile from the board
        if(tile == null) {
            throw new IllegalMove("There is no Tile on this coordinate!");
        }
        board.put(new Coordinate(toQ, toR), tile); // move the tile to new position
    }

    @Override
    public void pass() throws IllegalMove {

    }

    /**
     * Checks if a given move is possible for current player
     *
     * @param tileType the type of tile being set
     * @param q        the q coordinate
     * @param r        the r coordinate
     * @return if the move is valid
     */
    private boolean isValidPlay(TileType tileType, int q, int r) {
        // a. Een speler mag alleen zijn eigen nog niet gespeelde stenen spelen.

        // b. Een speler speelt een steen door deze op een leeg vlak in het speelveld te leggen.

        // c. Als er al stenen op het bord liggen moet een naast een andere steen gespeeld worden

        // d. Als er stenen van beide spelers op het bord liggen mag een steen niet naast een steen van de
        // tegenstander geplaatst worden

        // e. Als een speler al drie stenen gespeeld heeft maar zijn bijenkoningin nog niet, dan moet deze gespeeld worden.

        return true;
    }

    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    @Override
    public boolean isDraw() {
        return false;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Map<TileType, Integer> getTiles(Player player) {
        return new HashMap<TileType, Integer>() {{
            put(TileType.QUEEN_BEE, 1);
            put(TileType.SPIDER, 2);
            put(TileType.BEETLE, 2);
            put(TileType.SOLDIER_ANT, 3);
            put(TileType.GRASSHOPPER, 3);
        }};
    }

    public HashMap<Coordinate, Tile> getBoard() {
        return board;
    }
}
