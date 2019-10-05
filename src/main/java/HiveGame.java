import java.lang.annotation.Inherited;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains the main Hive game data
 * q = col and r = row
 */
public class HiveGame implements Hive {

    private HashMap<Coordinate, Field> board;

    private Player currentPlayer;

    public HiveGame() {
        this.board = new HashMap<>();
        currentPlayer = Player.WHITE; // white starts
    }

    @Override
    public void play(Tile tile, int q, int r) throws IllegalMove {
        board.put(new Coordinate(q, r), new Field(Tile.QUEEN_BEE));
    }

    @Override
    public void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove {

    }

    @Override
    public void pass() throws IllegalMove {

    }

    @Override
    public boolean isWinner(Player player) {
        return false;
    }

    @Override
    public boolean isDraw() {
        return false;
    }
}
