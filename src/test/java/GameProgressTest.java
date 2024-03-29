import game.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class GameProgressTest {

    /*
     * 3. Spelverloop
     */

    // a. Wit heeft de eerste beurt.
    @Test
    void testIfWhiteIsTheFirstPlayerToPlay() {
        HiveGame game = new HiveGame();
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
    }

    // b. Tijdens zijn beurt kan een speler een steen spelen, een steen verplaatsen of passen; daarna is de tegenstander aan de beurt.
    @Test
    void testThatPlayerChanges() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w
        assertEquals(Hive.Player.BLACK, game.getCurrentPlayer());
        game.play(Hive.TileType.QUEEN_BEE, 1, 0); // b
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
        game.move(0, 0, 0, 1); // w
        assertEquals(Hive.Player.BLACK, game.getCurrentPlayer());
        game.move(1, 0, 0, 0); // b
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
    }

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.
    @Test
    void testIfWhitePlayerWinsWhenThereAreSixOppositeTilesAtBlackQueen() {
        // mock the tiles on the board, then check if win gives back true in certain situations
        Board board = spy(Board.class);
        HiveGame game = new HiveGame(board);
        when(board.getPlayField()).thenReturn(generateWinBoard(Hive.Player.BLACK));
        assertTrue(game.isWinner(Hive.Player.WHITE));
    }

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.
    @Test
    void testIfBlackPlayerWinsWhenThereAreSixOppositeTilesAtWhiteQueen() {
        // mock the tiles on the board, then check if win gives back true in certain situations
        Board board = spy(Board.class);
        HiveGame game = new HiveGame(board);
        when(board.getPlayField()).thenReturn(generateWinBoard(Hive.Player.WHITE));
        assertTrue(game.isWinner(Hive.Player.BLACK));
    }

    /**
     * Generates a winning board
     *
     * @param loser the loser of the game, the other player will have
     * @return the board with a winner and loser
     */
    private HashMap<Coordinate, Field> generateWinBoard(Hive.Player loser) {
        Hive.Player winner = (loser == Hive.Player.BLACK) ? Hive.Player.WHITE : Hive.Player.BLACK;
        Coordinate beeCoordinate = new Coordinate(0, 0);
        ArrayList<Coordinate> neighbours = beeCoordinate.getNeighbours();

        HashMap<Coordinate, Field> board = new HashMap<Coordinate, Field>() {{
            put(beeCoordinate, new Field(new Tile(loser, Hive.TileType.QUEEN_BEE)));
        }};

        int index = 0;
        for (Coordinate neighbour : neighbours) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Field(new Tile(winner, type)));
            ++index;
        }

        return board;
    }

    // d. Als beide spelers tegelijk zouden winnen is het in plaats daarvan een gelijkspel.
    @Test
    void testIfItsADrawWhenBothPlayersWouldWin() {
        HiveGame game = new HiveGame();
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(generateTiedBoard());
        game.setBoard(board);
        assertTrue(game.isDraw());
    }

    /**
     * Generates a tied board
     *
     * @return the board with a tied board
     */
    private HashMap<Coordinate, Field> generateTiedBoard() {
        Coordinate whiteBee = new Coordinate(0, 0);
        Coordinate blackBee = new Coordinate(10, 10);

        ArrayList<Coordinate> neighboursBee1 = whiteBee.getNeighbours();
        ArrayList<Coordinate> neighboursBee2 = blackBee.getNeighbours();

        HashMap<Coordinate, Field> board = new HashMap<Coordinate, Field>() {{
            put(whiteBee, new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(blackBee, new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
        }};

        int index = 0;
        for (Coordinate neighbour : neighboursBee1) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Field(new Tile(Hive.Player.BLACK, type)));
            ++index;
        }

        index = 0;
        for (Coordinate neighbour : neighboursBee2) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Field(new Tile(Hive.Player.WHITE, type)));
            ++index;
        }
        return board;
    }

}
