import game.Hive;
import game.HiveGame;
import game.Tile;
import org.junit.jupiter.api.Test;
import strategy.MoveStrategy;
import strategy.MoveStrategyFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

public class MoveTileTest {

    /*
     * 5. Een steen verplaatsen
     */

    // a. Een speler mag alleen zijn eigen eerder gespeelde stenen verplaatsen.
    @Test
    void testIfPlayerCanMoveTheirOwnPlayedTiles() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SPIDER, 1, 0); // w
        game.move(0, 0, 1, -1); // w
    }

    @Test
    void testIfPlayerCannotMoveOtherPlayerTiles() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.play(Hive.TileType.BEETLE, 1, 0); // b
        assertThrows(Hive.IllegalMove.class, () -> game.move(1, 0, -1, 0), "Cannot move opponent's tiles"); // w
    }

    // b. Een speler mag pas stenen verplaatsen als zijn bijenkoningin gespeeld is.
    @Test
    void testIfPlayerCannotMoveWhenHisQueenHasNotBeenPlayed() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.BEETLE, 0, 0); // w
        game.play(Hive.TileType.BEETLE, 1, 0); // b
        assertThrows(Hive.IllegalMove.class, () -> game.move(0, 0, 2, 0), "Play bee queen first before being move");
    }

    @Test
    void testIfPlayerCanMoveTilesWhenHisQueenHasBeenPlayed() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.placeFromInventory(Hive.TileType.BEETLE, 1, 0); // w
        assertDoesNotThrow(() -> game.move(1, 0, 0, 1)); // w
    }

    // c. Een steen moet na het verplaatsen in contact zijn met minstens één andere steen.
    @Test
    void testThatMovedTilesAreAlwaysInContactWithAtLeastOneOtherStone() {
        HiveGame game = spy(HiveGame.class);
        game.place(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE), 0, 0);
        game.place(new Tile(Hive.Player.BLACK, Hive.TileType.BEETLE), 1, 0);
        game.place(new Tile(Hive.Player.WHITE, Hive.TileType.GRASSHOPPER), -1, 0);
        assertThrows(Hive.IllegalMove.class, () -> game.move(-1, 0, -5, -5));
    }

    // d. Een steen mag niet verplaatst worden als er door het weghalen van de steen twee niet onderling
    // verbonden groepen stenen ontstaan.
    @Test
    void testThatTileCannotBeMovedWhenTwoIndividualGroupsWillExist() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        // play pieces
        game.play(Hive.TileType.QUEEN_BEE, 0, 0); // w  // white bee in the middle
        game.play(Hive.TileType.QUEEN_BEE, 1, 0); // b  // black bee on top of white bee
        game.play(Hive.TileType.SOLDIER_ANT, -1, 0); // w  // white ant below white bee
        game.play(Hive.TileType.SOLDIER_ANT, 2, 0); // b  // black ant above black bee
        // move pieces
        game.move(-1, 0, 2, -1); // w  // white ant next to in between black bee and ant
        game.move(2, 0, -1, 1); // b  // black ant next to in between white bee and ant

        assertThrows(Hive.IllegalMove.class, () -> game.move(0, 0, 3, -1), "Move invalid, otherwise game.Hive would split"); // w
        game.setCurrentPlayer(Hive.Player.BLACK); // switch to black
        assertThrows(Hive.IllegalMove.class, () -> game.move(0, 1, -2, 1), "Move invalid, otherwise game.Hive would split"); // b
    }

    // e. Elk van de types stenen heeft zijn eigen manier van verplaatsen.
    @Test
    void testIfEveryTileHasTheirOwnStrategyOfMoving() {
        MoveStrategyFactory factory = new MoveStrategyFactory();
        MoveStrategy strategy1 = factory.createMoveStrategy(Hive.TileType.QUEEN_BEE);
        MoveStrategy strategy2 = factory.createMoveStrategy(Hive.TileType.SOLDIER_ANT);
        assertNotEquals(strategy1, strategy2);
        assertNotSame(strategy1, strategy2);
    }

}
