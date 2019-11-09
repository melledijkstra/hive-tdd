import game.Hive;
import game.HiveGame;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PlayTileTest {

    /**
     * 4. Een steen spelen
     */

    // a. Een speler mag alleen zijn eigen nog niet gespeelde stenen spelen.
    @Test
    void testWhetherPlayerCanOnlyPlayOwnNonePlayedTiles() {
        HiveGame game = spy(HiveGame.class);
        when(game.getPlayerTiles(game.getCurrentPlayer())).thenReturn(new HashMap<Hive.TileType, Integer>() {{
            put(Hive.TileType.QUEEN_BEE, 0); // mock that player has no queen bee anymore
        }});
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.QUEEN_BEE, 0, 0), "This player does not have any more tile of this tiletype");
    }

    @Test
    void testIfPlayerCannotPlayQueenBeeTwice() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.play(Hive.TileType.GRASSHOPPER, 1, 0); // b
        // white tries to play queen bee again!
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.QUEEN_BEE, 2, 0), "This player does not have any more tile of this tiletype");
    }

    // b. Een speler speelt een steen door deze op een leeg vlak in het speelveld te leggen.
    // this test goes against the principle that tiles can be stacked? a player can play on a field where tiles exist
    //    @Test
    //    void testIfPlayCanOnlyBeDoneOnEmptyFields() throws game.Hive.IllegalMove {
    //        game.HiveGame game = new game.HiveGame();
    //        game.play(game.Hive.TileType.QUEEN_BEE, 0, 0); // w
    //        game.play(game.Hive.TileType.SPIDER, 0, 1); // b
    //        assertThrows(game.Hive.IllegalMove.class, () -> game.play(game.Hive.TileType.SPIDER, 0, 0)); // w
    //    }

    // c. Als er al stenen op het bord liggen moet er naast een andere steen gespeeld worden
    @Test
    void testIfPlayerCanOnlyPlayNextToOtherTilesWhenThereHasBeenPlayedOnTheBoard() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SOLDIER_ANT, 1, 0); // w
        game.placeFromInventory(Hive.TileType.SPIDER, 2, 0); // w
        game.placeFromInventory(Hive.TileType.GRASSHOPPER, 3, 0);  // w

        game.play(Hive.TileType.SOLDIER_ANT, 4, 0); // w
        game.play(Hive.TileType.SOLDIER_ANT, 3, 1); // b
        // place tile on a place with no neighbours!
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.SOLDIER_ANT, 15, 15), "Tiles must be placed next to other tiles"); // w
    }

    // d. Als er stenen van beide spelers op het bord liggen mag een steen niet naast een steen van de
    // tegenstander geplaatst worden
    @Test
    void testIfTileCannotBePlacedNextToOpponentTilesWhenBothPlayersHavePlayed() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.play(Hive.TileType.SPIDER, 1, 0);
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.BEETLE, 1, 1), "Tile cannot be placed next to opponent"); // w
    }

    // e. Als een speler al drie stenen gespeeld heeft maar zijn bijenkoningin nog niet, dan moet deze gespeeld worden.
    @Test
    void testConstraintOnPlayingQueenWhenThreeTilesHaveBeenPlayed() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.GRASSHOPPER, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SOLDIER_ANT, 1, 0); // w
        game.placeFromInventory(Hive.TileType.SPIDER, 0, 1); // w
        assertThrows(Hive.IllegalMove.class, () -> game.play(Hive.TileType.BEETLE, 2, 0), "Play queen bee at least in 4 turns"); // w
    }

}
