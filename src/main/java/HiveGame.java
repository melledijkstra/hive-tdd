import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Contains the main Hive game data
 */
public class HiveGame implements Hive {

    private Player currentPlayer;

    private HashMap<Coordinate, Field> board;

    private HashMap<Player, HashMap<TileType, Integer>> playerTiles;

    int turn;

    public HiveGame() {
        currentPlayer = Player.WHITE;
        playerTiles = new HashMap<>();
        board = new HashMap<>();
        distributeTilesToPlayers();
        turn = 1;
    }

    private void distributeTilesToPlayers() {
        for (Player p : new Player[]{Player.WHITE, Player.BLACK}) {
            playerTiles.put(p, new HashMap<TileType, Integer>() {{
                put(TileType.QUEEN_BEE, 1);
                put(TileType.SPIDER, 2);
                put(TileType.BEETLE, 2);
                put(TileType.SOLDIER_ANT, 3);
                put(TileType.GRASSHOPPER, 3);
            }});
        }
    }

    @Override
    public void play(TileType tileType, int q, int r) throws IllegalMove {
        if (tileType != TileType.QUEEN_BEE && getPlayerTiles(currentPlayer).get(TileType.QUEEN_BEE) == 1) { // if queen has not been played
            // check if already three moves have been done
            int tilesLeft = 0;
            for (int amount : getPlayerTiles(currentPlayer).values()) {
                tilesLeft += amount; // count all the left over tiles of this player
            }
            if (tilesLeft <= 8) { // if he has 8 tiles or below means he did 3 turns already
                throw new IllegalMove("Play queen bee at least in 4 turns");
            }
        }
        if (turn > 1 && !positionHasNeighbours(q, r)) {
            throw new IllegalMove("Tiles must be placed next to other tiles");
        }
        if (turn > 2 && touchesOpponent(currentPlayer, q, r)) {
            throw new IllegalMove("Tile cannot be placed next to opponent");
        }
        placeFromInventory(tileType, q, r);
        switchPlayer();
        ++turn;
    }

    private boolean touchesOpponent(Player currentPlayer, int q, int r) {
        HashMap<Coordinate, Field> board = getBoard();
        boolean touches = false;
        for (Coordinate neighbour : new Coordinate(q, r).getNeighbours()) {
            if (board.get(neighbour) != null &&
                    board.get(neighbour).peek() != null && // check the upper tile
                    board.get(neighbour).peek().getColor() != currentPlayer) {
                touches = true; // a neighbour is from the other player
            }
        }
        return touches;
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        Field field = getBoard().get(new Coordinate(fromQ, fromR)); // retrieve the field from the board
        Tile upperTile = field.peek();
        if (upperTile == null || upperTile.getColor() != currentPlayer) {
            throw new IllegalMove(String.format("There is no Tile on coordinate: (%d,%d), with color: %s", fromQ, fromR, currentPlayer));
        }
        if (getPlayerTiles(currentPlayer).get(TileType.QUEEN_BEE) == 1) {
            throw new IllegalMove("Play bee first before moving");
        }
        // check if hive would split
        if (hiveWouldSplit(fromQ, fromR)) {
            throw new IllegalMove("Invalid move, the hive would split");
        }
        upperTile = field.removeUpperTile(); // delete top tile from the board
        if (!positionHasNeighbours(new Coordinate(toQ, toR))) {
            field.addTile(upperTile); // put tile back on the old position
            throw new IllegalMove("Tile not in contact with another stone");
        }
        place(upperTile, toQ, toR); // move the field to new position
        switchPlayer();
    }

    private boolean hiveWouldSplit(int q, int r) {
        // hive is divided when the neighbours after movement are not all connected
        HashMap<Coordinate, Field> board = getBoard();
        int actualNeighbours = 0; // the actual amount of neighbours the removed tile has
        int connections = 0; // the amount of connections between neighbours when looping through them
        ArrayList<Coordinate> neighbours = new Coordinate(q, r).getNeighbours();
        // set to true if last element of neighbours has tile, otherwise this connection would not be included
        Field lastNeighbour = board.get(neighbours.get(neighbours.size() - 1));
        boolean previousHadTile = lastNeighbour != null && lastNeighbour.peek() != null;
        for (Coordinate neighbour : neighbours) {
            if (board.get(neighbour) != null &&
                    board.get(neighbour).peek() != null) {
                actualNeighbours++; // there is actually a neighbour on this tile
                if (previousHadTile) { // if the previous neighbour also had a tile, it means we have a connection
                    connections++;
                }
                previousHadTile = true;
            } else {
                previousHadTile = false;
            }
        }
        return connections < actualNeighbours - 1;
    }

    @Override
    public void pass() throws IllegalMove {
        switchPlayer();
    }

    /**
     * Check if a position has neighbours
     */
    private boolean positionHasNeighbours(Coordinate position) {
        HashMap<Coordinate, Field> board = getBoard();
        boolean hasNeighbours = false;
        for (Coordinate neighbour : position.getNeighbours()) {
            Field nbrField = board.get(neighbour);
            if (nbrField != null && !nbrField.getTiles().isEmpty()) { // if field exists and tiles exist, he has a neighbour
                hasNeighbours = true;
                break;
            }
        }
        return hasNeighbours;
    }

    private boolean positionHasNeighbours(int q, int r) {
        return positionHasNeighbours(new Coordinate(q, r));
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
    }

    public void placeFromInventory(TileType tileType, int q, int r) throws IllegalMove {
        if (getPlayerTiles(currentPlayer).get(tileType) < 1) { // check if player actually has this tile
            throw new IllegalMove("This player does not have any more tile of this tiletype");
        }
        removeFromInventory(tileType, currentPlayer);
        place(new Tile(currentPlayer, tileType), q, r);
    }

    public void place(Tile tile, int q, int r) {
        Field field = board.get(new Coordinate(q, r));
        if (field == null) {
            board.put(new Coordinate(q, r), new Field(tile));
        } else {
            field.addTile(tile);
        }
    }

    private void removeFromInventory(TileType tileType, Player currentPlayer) {
        int amount = playerTiles.get(currentPlayer).get(tileType); // get current amount of tiles of tiletype
        playerTiles.get(currentPlayer).put(tileType, amount - 1); // decrease the amount by one
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

    public void setCurrentPlayer(Player newPlayer) {
        currentPlayer = newPlayer;
    }

    public HashMap<TileType, Integer> getPlayerTiles(Player player) {
        return playerTiles.get(player);
    }

    public HashMap<Coordinate, Field> getBoard() {
        return board;
    }
}
