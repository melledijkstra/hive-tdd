import game.Board;
import game.Coordinate;
import game.Hive;
import game.HiveGame;
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

    @Test
    void testIfCoordinatesHaveCommonNeighbours() {
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(0, 1);
        assertEquals(2, a.getCommonNeighbours(b).size());
    }

    @Test
    void testIfCoordinatesDontHaveCommonNeighbours() {
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(1, 2);
        assertEquals(0, a.getCommonNeighbours(b).size());
    }

    @Test
    void testIfTwoNeighboursAreActuallyNeighbours() {
        Coordinate center = new Coordinate(0, 0);
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>() {{
            add(new Coordinate(0, -1));
            add(new Coordinate(1, -1));
            add(new Coordinate(1, 0));
            add(new Coordinate(0, 1));
            add(new Coordinate(-1, 1));
            add(new Coordinate(-1, 0));
        }};
        for (Coordinate nbrs : coordinates) {
            assertTrue(center.isNeighbour(nbrs));
        }
    }

    @Test
    void testIfTwoCoordinatesAreNotNeighbours() {
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(0, 1);
        assertEquals(2, a.getCommonNeighbours(b).size());
    }

    // c. Aan het begin van het spel is het speelveld leeg.
    @Test
    void testIfBoardAtStartOfGameIsEmpty() {
        HiveGame game = new HiveGame();
        assertEquals(0, game.getPlayField().size());
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
        assertEquals(Hive.TileType.QUEEN_BEE, game.getPlayField().get(new Coordinate(1, -1)).peek().getType());
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
        assertEquals(2, game.getPlayField().get(new Coordinate(0, 0)).getTiles().size());
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
        assertEquals(Hive.TileType.SOLDIER_ANT, game.getPlayField().get(new Coordinate(-1, 1)).peek().getType());
    }

    @Test
    void testIfTwoTilesCanBePlacedNextToEachOther() throws Hive.IllegalMove {
        HiveGame game = new HiveGame();
        game.placeFromInventory(Hive.TileType.QUEEN_BEE, 0, 0);
        game.placeFromInventory(Hive.TileType.BEETLE, 1, 0);
        assertEquals(2, game.getPlayField().size());
        assertEquals(1, game.getPlayField().get(new Coordinate(0, 0)).getTiles().size());
        assertEquals(1, game.getPlayField().get(new Coordinate(1, 0)).getTiles().size());
    }

    @Test
    void testIfBoardIsAbleToGeneratePossibleMovesForAllTheTiles() {
        Board board = new Board();

    }

}
