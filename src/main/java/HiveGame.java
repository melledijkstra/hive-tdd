import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Contains the main Hive game data
 */
public class HiveGame implements Hive {

    private HashMap<Coordinate, Field> board;

    private Player currentPlayer;

    private HashMap<Player, ArrayList<Tile>> stoneList;

    public HiveGame() {
        this.board = new HashMap<>();
        this.stoneList = new HashMap<>();
        // give each player their stones
        handOutStones();
        currentPlayer = Player.WHITE; // white starts
    }

    private void handOutStones() {
        // the initial tiles for each player
        Tile[] tileList = new Tile[]{
                Tile.QUEEN_BEE,
                Tile.SPIDER, Tile.SPIDER,
                Tile.BEETLE, Tile.BEETLE,
                Tile.SOLDIER_ANT, Tile.SOLDIER_ANT, Tile.SOLDIER_ANT,
                Tile.GRASSHOPPER, Tile.GRASSHOPPER, Tile.GRASSHOPPER
        };
        for (Player p : Player.values()) { // give the list of stones to each player
            stoneList.put(p, new ArrayList<>(Arrays.asList(tileList)));
        }
    }

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        // 4. Een steen spelen
        if (isValidPlay(tile, q, r)) {
            board.put(new Coordinate(q, r), new Field(Tile.QUEEN_BEE, currentPlayer));
            // switch the player, only when play is valid?
            currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
        }
    }

    /**
     * Checks if a given move is possible for current player
     *
     * @param tile the type of tile being set
     * @param q    the q coordinate
     * @param r    the r coordinate
     * @return if the move is valid
     */
    private boolean isValidPlay(Tile tile, int q, int r) {
        // a. Een speler mag alleen zijn eigen nog niet gespeelde stenen spelen.

        // b. Een speler speelt een steen door deze op een leeg vlak in het speelveld te leggen.

        // c. Als er al stenen op het bord liggen moet een naast een andere steen gespeeld worden

        // d. Als er stenen van beide spelers op het bord liggen mag een steen niet naast een steen van de
        // tegenstander geplaatst worden

        // e. Als een speler al drie stenen gespeeld heeft maar zijn bijenkoningin nog niet, dan moet deze gespeeld worden.

        return true;
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {
        // 5. Een steen verplaatsen & 6. Een steen verschuiven
    }

    @Override
    public void pass() throws IllegalMove {
        currentPlayer = (currentPlayer == Player.WHITE) ? Player.BLACK : Player.WHITE;
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
}
