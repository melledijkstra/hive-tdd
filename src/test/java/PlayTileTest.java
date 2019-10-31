import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PlayTileTest {

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
