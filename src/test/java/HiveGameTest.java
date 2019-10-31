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
        assertNotNull(tile.getType());
    }

    // c. Elke speler heeft aan het begin van het spel de beschikking over één bijenkoningin, twee spinnen,
    // twee kevers, drie soldatenmieren en drie sprinkhanen in zijn eigen kleur.
    @Test
    void testIfPlayersEachHaveCorrectStartingTiles() {
        HiveGame game = new HiveGame();
        Map<Hive.TileType, Integer> blackTiles = game.getPlayerTiles(Hive.Player.BLACK);
        Map<Hive.TileType, Integer> whiteTiles = game.getPlayerTiles(Hive.Player.WHITE);

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

        // todo: HOW CAN WE WRITE THIS TEST BETTER?
    }

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.
    @Test
    void testIfPlayerWinsWhenThereAreSixOppositeTilesAtEnemiesQueen() {
        // mock the tiles on the board, then check if win gives back true in certain situations
        HiveGame game = spy(HiveGame.class);
        when(game.getBoard()).thenReturn(generateWinBoard(Hive.Player.BLACK));
        assertTrue(game.isWinner(Hive.Player.WHITE));

        // todo: isWinner not has to call `getBoard()` instead of `this.board` otherwise test will fail, how to fix this?
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
        HiveGame game = spy(HiveGame.class);
        when(game.getBoard()).thenReturn(generateTiedBoard());
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

    /**
     * 4. Een steen spelen
     */

    // a. Een speler mag alleen zijn eigen nog niet gespeelde stenen spelen.
    @Test
    void testWhetherPlayerCanOnlyPlayOwnNonePlayedTiles() throws Hive.IllegalMove {
        HiveGame game = spy(HiveGame.class);
        when(game.getPlayerTiles(game.getCurrentPlayer())).thenReturn(new HashMap<>());
        assertFalse(game.isValidPlay(Hive.TileType.QUEEN_BEE, 0, 0));
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.QUEEN_BEE, 0, 0));
    }

    @Test
    void testIfPlayerCanPlayQueenBeeTwice() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // white plays
        game.play(Hive.TileType.GRASSHOPPER, 1, 0); // black plays
        assertThrows(Hive.IllegalMove.class, () -> {
            game.play(Hive.TileType.QUEEN_BEE, 2, 0); // white tries to play queen bee again!
        });
    }

    // b. Een speler speelt een steen door deze op een leeg vlak in het speelveld te leggen.
    @Test
    void testIfPlayCanOnlyBeDoneOnEmptyFields() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.play(Hive.TileType.SPIDER, 0, 1); // b
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.SPIDER, 0, 0)); // w
    }

    // c. Als er al stenen op het bord liggen moet er naast een andere steen gespeeld worden
    @Test
    void testIfPlayerCanOnlyPlayNextToOtherTilesWhenThereHasBeenPlayedOnTheBoard() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.place(Hive.TileType.QUEEN_BEE, 0, 0);
        game.place(Hive.TileType.SOLDIER_ANT, 1, 0);
        game.place(Hive.TileType.SPIDER, 2, 0);
        game.place(Hive.TileType.GRASSHOPPER, 3, 0);

        game.play(Hive.TileType.SOLDIER_ANT, 4, 0); // w
        game.play(Hive.TileType.SOLDIER_ANT, 3, 1); // b
        // place tile on a place with no neighbours!
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.SOLDIER_ANT, 10, 10)); // w
    }

    // d. Als er stenen van beide spelers op het bord liggen mag een steen niet naast een steen van de
    // tegenstander geplaatst worden
    @Test
    void testIfTileCannotBePlacedNextToOpponentTilesWhenBothPlayersHavePlayed() {
        HiveGame game = spy(HiveGame.class);
        when(game.getBoard()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.SPIDER)));
        }});
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.BEETLE, 1, 1)); // w
    }

    // e. Als een speler al drie stenen gespeeld heeft maar zijn bijenkoningin nog niet, dan moet deze gespeeld worden.
    @Test
    void testConstraintOnPlayingQueenWhenThreeTilesHaveBeenPlayed() {
        HiveGame game = new HiveGame();
        game.place(Hive.TileType.GRASSHOPPER, 0, 0); // w
        game.place(Hive.TileType.SOLDIER_ANT, 1, 0); // w
        game.place(Hive.TileType.SPIDER, 0, 1); // w
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.BEETLE, 2, 0)); // w
    }

}