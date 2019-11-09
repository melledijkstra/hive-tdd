import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    /*
     * 2. Speelveld
     */

    // a. Het speelveld is een oneindig zeshoekig veld. Elk vlak wordt aangeduid met twee integercoördinaten, q en r
    // b. Elk van de velden heeft zes aangrenzende velden.
    @Test
    void testIfCoordinatesContainNeighbours() {
        Coordinate coordinate = new Coordinate(150, 150);
        ArrayList<Coordinate> neighbours = coordinate.getNeighbours();
        assertEquals(6, neighbours.size());
    }

    @Test
    void testIfCoordinateHasTwoCoordinates() {
        Coordinate coordinate = new Coordinate(10, 10);
        int[] coordinates = coordinate.getCoordinates();

        assertEquals(10, coordinates[0]);
        assertEquals(10, coordinates[1]);
    }

    // c. Aan het begin van het spel is het speelveld leeg.
    @Test
    void testIfBoardAtStartOfGameIsEmpty() {
        HiveGame game = new HiveGame();
        assertEquals(0, game.getBoard().size());
    }

    // d. Stenen mogen alleen precies in een vlak liggen.
    // This test is covered because integers are used for coordinates, so a tile is always exactly in a field (vlak)

    // e. In sommige gevallen mogen stenen verplaatst worden.
    @Test
    void testIfTilesCanBeMoved() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SPIDER, 1, 0); // w
        game.move(0, 0, 1, -1); // w
        assertEquals(Hive.TileType.QUEEN_BEE, game.getBoard().get(new Coordinate(1, -1)).peek().getType());
    }

    // f. In sommige gevallen mogen stenen op andere stenen liggen; in dat geval mag alleen de bovenste steen van
    // een stapel verplaatst worden.
    @Test
    void testIfStonesAreAbleToStackOnTopOfEachOther() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        // put a stone on a spot
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0);
        // put another stone on the same spot
        game.placeFromInventory(Hive.TileType.SPIDER, 0, 0);
        assertEquals(2, game.getBoard().get(new Coordinate(0,0)).getTiles().size());
    }

    @Test
    void testIfTopTileOfStackCanBeMoved() throws Hive.IllegalMove {
        // we just need the beginning of the previous test? how could we do that at once?
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SPIDER, 0, 0); // w
        game.placeFromInventory(Hive.TileType.SOLDIER_ANT, 0, 0); // w
        // move the top tile
        game.move(0, 0, -1, 1); // w
        assertEquals(Hive.TileType.SOLDIER_ANT, game.getBoard().get(new Coordinate(-1, 1)).peek().getType());
    }

    @Test
    void testIfTwoTilesCanBePlacedNextToEachOther() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0);
        game.placeFromInventory(Hive.TileType.BEETLE, 1, 0);
        assertEquals(2, game.getBoard().size());
        assertEquals(1, game.getBoard().get(new Coordinate(0, 0)).getTiles().size());
        assertEquals(1, game.getBoard().get(new Coordinate(1, 0)).getTiles().size());
    }

}
