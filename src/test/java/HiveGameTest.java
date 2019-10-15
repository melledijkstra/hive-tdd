import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class HiveGameTest {

    /*
     * 1. Spelmateriaal
     */

    // a. Hive wordt gespeeld met zeshoekige stenen in de kleuren wit en zwart, die corresponderen met de twee spelers.

    /**
     * this test is partly covered by
     *
     * @see BoardTest#testIfCoordinatesContainNeighbours
     */


    @Test
    void testIfTileHasAColorOfAPlayerWhiteOrBlack() {
        Tile tile1 = new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE);
        Tile tile2 = new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE);
        assertEquals(Hive.Player.WHITE, tile1.getColor());
        assertEquals(Hive.Player.BLACK, tile2.getColor());
    }

    // b. De stenen bevatten ieder een afbeelding: een bijenkoningin, spin, kever, soldatenmier of sprinkhaan.
    @Test
    void testWhetherTileHasAType() {
        Tile tile = new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE);
        assertNotNull(tile.getTopTile());
    }

    // c. Elke speler heeft aan het begin van het spel de beschikking over één bijenkoningin, twee spinnen,
    // twee kevers, drie soldatenmieren en drie sprinkhanen in zijn eigen kleur.
    @Test
    void testIfPlayersEachHaveCorrectStartingTiles() {
        HiveGame game = new HiveGame();
        Map<Hive.TileType, Integer> blackTiles = game.getTiles(Hive.Player.BLACK);
        Map<Hive.TileType, Integer> whiteTiles = game.getTiles(Hive.Player.WHITE);

        assertEquals(1, blackTiles.get(Hive.TileType.QUEEN_BEE));
        assertEquals(2, blackTiles.get(Hive.TileType.SPIDER));
        assertEquals(2, blackTiles.get(Hive.TileType.BEETLE));
        assertEquals(3, blackTiles.get(Hive.TileType.SOLDIER_ANT));
        assertEquals(3, blackTiles.get(Hive.TileType.GRASSHOPPER));

        assertEquals(1, whiteTiles.get(Hive.TileType.QUEEN_BEE));
        assertEquals(2, whiteTiles.get(Hive.TileType.SPIDER));
        assertEquals(2, whiteTiles.get(Hive.TileType.BEETLE));
        assertEquals(3, whiteTiles.get(Hive.TileType.SOLDIER_ANT));
        assertEquals(3, whiteTiles.get(Hive.TileType.GRASSHOPPER));
    }

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
        game.play(Hive.TileType.QUEEN_BEE, 0, 0);
        assertEquals(Hive.Player.BLACK, game.getCurrentPlayer());
        game.play(Hive.TileType.SPIDER, 1, 1);
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
        game.pass();
        assertEquals(Hive.Player.BLACK, game.getCurrentPlayer());
        game.move(1, 1, 2, 1);
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());

        // HOW CAN WE WRITE THIS TEST BETTER?
    }

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.
    @Test
    void testIfPlayerWinsWhenThereAreSixOppositeTilesAtEnemiesQueen() {
        // mock the tiles on the board, then check if win gives back true in certain situations
        HiveGame game = spy(HiveGame.class);
        when(game.getBoard()).thenReturn(generateWinBoard(Hive.Player.BLACK));
        assertTrue(game.isWinner(Hive.Player.WHITE));

        // isWinner not has to call `getBoard()` instead of `this.board` otherwise test will fail, how to fix this?
    }

    /**
     * Generates a winning board
     *
     * @param loser the loser of the game, the other player will have
     * @return the board with a winner and loser
     */
    private HashMap<Coordinate, Tile> generateWinBoard(Hive.Player loser) {
        Hive.Player winner = (loser == Hive.Player.BLACK) ? Hive.Player.WHITE : Hive.Player.BLACK;
        Coordinate beeCoordinate = new Coordinate(0, 0);
        ArrayList<Coordinate> neighbours = beeCoordinate.getNeighbours();

        HashMap<Coordinate, Tile> board = new HashMap<Coordinate, Tile>() {{
            put(beeCoordinate, new Tile(loser, Hive.TileType.QUEEN_BEE));
        }};

        int index = 0;
        for (Coordinate neighbour : neighbours) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Tile(winner, type));
            ++index;
        }

        return board;
    }

    // d. Als beide spelers tegelijk zouden winnen is het in plaats daarvan een gelijkspel.
    @Test
    void testIfItsADrawWhenBothPlayersWouldWin() {
        HiveGame game = spy(HiveGame.class);
        when(game.getBoard()).thenReturn(generateTiedBoard());
        assertTrue(game.isDraw());
    }

    /**
     * Generates a tied board
     *
     * @return the board with a winner and loser
     */
    private HashMap<Coordinate, Tile> generateTiedBoard() {
        Coordinate bee1Coordinate = new Coordinate(0, 0);
        Coordinate bee2Coordinate = new Coordinate(10, 10);

        ArrayList<Coordinate> neighboursBee1 = bee1Coordinate.getNeighbours();
        ArrayList<Coordinate> neighboursBee2 = bee2Coordinate.getNeighbours();


        HashMap<Coordinate, Tile> board = new HashMap<Coordinate, Tile>() {{
            put(bee1Coordinate, new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE));
            put(bee2Coordinate, new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE));

        }};


        int index = 0;
        for (Coordinate neighbour : neighboursBee1) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Tile(Hive.Player.BLACK, type));
            ++index;
        }
        index = 0;
        for (Coordinate neighbour : neighboursBee2) {
            Hive.TileType type = (index <= 2) ? Hive.TileType.SOLDIER_ANT : Hive.TileType.BEETLE;
            board.put(neighbour, new Tile(Hive.Player.WHITE, type));
            ++index;        }
        return board;
    }
}