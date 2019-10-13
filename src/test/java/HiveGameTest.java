import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HiveGameTest {

    /*
     * 1. Spelmateriaal
     */

    // a. Hive wordt gespeeld met zeshoekige stenen in de kleuren wit en zwart, die corresponderen met de twee spelers.
    /**
     * this test is partly covered by
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
    }

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.
    @Test
    void testIfPlayerWinsWhenThereAreSixOppositeTilesAtEnemiesQueen() {
        // mock the tiles on the board, then check if win gives back true in certain situations
    }

    // d. Als beide spelers tegelijk zouden winnen is het in plaats daarvan een gelijkspel.
    @Test
    void testIfItsADrawWhenBothPlayersWouldWin() {

    }

}