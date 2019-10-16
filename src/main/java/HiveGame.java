import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains the main Hive game data
 */
public class HiveGame implements Hive {

    private Player currentPlayer;

    private HashMap<Coordinate, Field> board = new HashMap<>();

    public HiveGame() {
        currentPlayer = Player.WHITE;
    }

    @Override
    public void play(TileType tileType, int q, int r) throws IllegalMove {
        if (isValidPlay(tileType, q, r)) {
            Field field = board.get(new Coordinate(q, r));
            if (field == null) {
                board.put(new Coordinate(q, r), new Field(new Tile(currentPlayer, tileType)));
            } else {
                field.addTile(new Tile(currentPlayer, tileType));
            }
            currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
        }
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        Coordinate from = new Coordinate(fromQ, fromR);
        Field field = board.get(from); // retrieve the field from the board
        if (field == null) {
            throw new IllegalMove("There is no Tile on this coordinate!");
        }
        board.remove(from); // delete field from the board
        board.put(new Coordinate(toQ, toR), field); // move the field to new position
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    @Override
    public void pass() throws IllegalMove {
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    /**
     * Checks if a given move is possible for current player
     *
     * @return if the move is valid
     */
    public boolean isValidPlay(TileType tileType, int q, int r) throws IllegalMove {
        // a. Een speler mag alleen zijn eigen nog niet gespeelde stenen spelen.
        HashMap<TileType, Integer> availableTiles = getPlayerTiles(currentPlayer);
        if (availableTiles.get(tileType) == 0) {
            throw new IllegalMove(String.format("There is no available tile of type: %s left for player: %s", tileType, currentPlayer));
        }

        //if(availableTiles[tile.getTiles()])   //HEEFT REFACTOR SLAG NODIG

        // b. Een speler speelt een steen door deze op een leeg vlak in het speelveld te leggen.

        // c. Als er al stenen op het bord liggen moet een naast een andere steen gespeeld worden

        // d. Als er stenen van beide spelers op het bord liggen mag een steen niet naast een steen van de
        // tegenstander geplaatst worden

        // e. Als een speler al drie stenen gespeeld heeft maar zijn bijenkoningin nog niet, dan moet deze gespeeld worden.

        return true;
    }

    @Override
    public boolean isWinner(Player player) {
        Player loser = (player == Player.WHITE) ? Player.BLACK : Player.WHITE;
        for (Map.Entry<Coordinate, Field> entry : getBoard().entrySet()) {
            Field field = entry.getValue();
            if (field.peek().getColor() != loser) { // todo: needs refactor
                continue;
            }
            if (field.contains(TileType.QUEEN_BEE)) {
                for (Coordinate neighbour : entry.getKey().getNeighbours()) {
                    if (!getBoard().containsKey(neighbour)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isDraw() {
        if (getBoard().size() < 10) {
            return false;
        }

        for (Coordinate key : getBoard().keySet()) {
            Field field = getBoard().get(key);
            if (field.contains(TileType.QUEEN_BEE)) {
                ArrayList<Coordinate> neighbours = key.getNeighbours();
                for (Coordinate neighbour : neighbours) {
                    if (getBoard().get(neighbour) == null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<TileType, Integer> getPlayerTiles(Player player) {
        return new HashMap<TileType, Integer>() {{
            put(TileType.QUEEN_BEE, 1);
            put(TileType.SPIDER, 2);
            put(TileType.BEETLE, 2);
            put(TileType.SOLDIER_ANT, 3);
            put(TileType.GRASSHOPPER, 3);
        }};
    }

    public HashMap<Coordinate, Field> getBoard() {
        return board;
    }
}
