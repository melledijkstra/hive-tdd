import org.junit.jupiter.api.Test;

public class MoveTileTest {

    /*
     * 5. Een steen verplaatsen
     */

    // a. Een speler mag alleen zijn eigen eerder gespeelde stenen verplaatsen.
    @Test
    void testIfPlayerCanOnlyMoveTheirOwnPlayedTiles() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, 0 ,0); // w
    }

    // b. Een speler mag pas stenen verplaatsen als zijn bijenkoningin gespeeld is.
    @Test
    void testIfPlayerCanOnlyMoveTilesWhenHisQueenHasBeenPlayed() {

    }

    // c. Een steen moet na het verplaatsen in contact zijn met minstens één andere steen.
    @Test
    void testThatMovedTilesAreAlwaysInContactWithAtLeastOneOtherStone() {

    }

    // d. Een steen mag niet verplaatst worden als er door het weghalen van de steen twee niet onderling
    // verbonden groepen stenen ontstaan.
    @Test
    void testThatTileCannotBeMovedWhen() {
        // TODO: what does this requirement actually state?
        // wat betekend twee niet onderling verbonden groepen stenen?
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
    }

}
