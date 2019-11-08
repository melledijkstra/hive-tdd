import org.junit.jupiter.api.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MoveTileTest {

    /*
     * 5. Een steen verplaatsen
     */

    // a. Een speler mag alleen zijn eigen eerder gespeelde stenen verplaatsen.
    @Test
    void testIfPlayerCanOnlyMoveTheirOwnPlayedTiles() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.place(Hive.TileType.QUEEN_BEE, 0, 0);
        game.place(Hive.TileType.SPIDER, 1, 0);
        game.move(0, 0, 2, 0);
    }

    // b. Een speler mag pas stenen verplaatsen als zijn bijenkoningin gespeeld is.
    @Test
    void testIfPlayerCanOnlyMoveTilesWhenHisQueenHasBeenPlayed() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.BEETLE, 0, 0); // w
        game.play(Hive.TileType.BEETLE, 1, 0); // b
        assertThrows(Hive.IllegalMove.class, () -> game.move(0, 0, 2, 0), "Play bee first before moving");
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
    void testThatTileCannotBeMovedWhen() {
        // TODO: what does this requirement actually state?
        // wat betekend twee niet onderling verbonden groepen stenen?
        throw new NotImplementedException();
    }

    // e. Elk van de types stenen heeft zijn eigen manier van verplaatsen.
    @Test
    void testIfEveryTileHasTheirOwnStrategyOfMoving() {
        // todo: fix this by using interfaces and creating special Move classes that implement the interface move
        // MoveStrategy, this move strategy is then used per tile. A factory could assign a strategy to a tile

        // Tile t = new Tile(TileType.QUEEN_BEE, new QueenBeeMoveStrategy());
        // ...
        // t.getStrategy().move() ???

        // something like this?
        throw new NotImplementedException();
    }

}
