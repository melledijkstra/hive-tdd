import game.*;
import org.junit.jupiter.api.Test;
import strategy.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MoveStrategyTest {

    // ! The tile being tested in the strategy should not be on the board !
    // normally the game removes the tile and then applies the strategy, during the test don't place it and just use
    // the strategy

    /*
     * 7. Verplaatsen van een kever
     */
    //a. Een kever verplaatst zich door precies één keer te verschuiven.
    @Test
    void testBeetleStrategy() throws Hive.IllegalMove {
        // beetle moves by sliding once
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
        }});
        BeetleStrategy strategy = new BeetleStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 0), new Coordinate(1, -1)));
    }

    @Test
    void testIncorrectMoveOfBeetle() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
        }});
        BeetleStrategy strategy = new BeetleStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 0), new Coordinate(0, -1)));
    }

    /*
     * 8. Verplaatsen van de bijenkoningin
     */
    @Test
    void testQueenBeeStrategy() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
        }});
        QueenBeeStrategy strategy = new QueenBeeStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(1, -1)));
    }

    // a. De bijenkoningin verplaatst zich door precies één keer te verschuiven.
    @Test
    void testIfQueenCanOnlyMoveBySlidingOnce() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
        }});
        QueenBeeStrategy strategy = new QueenBeeStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(2, -1)));
    }

    // b. De bijenkoningin mag alleen verplaatst worden naar een leeg veld.
    @Test
    void testIfQueenCanOnlyBeMovedToEmptyField() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.BEETLE)));
        }});
        QueenBeeStrategy strategy = new QueenBeeStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(1, -1)), "Queen bee can only be moved to empty fields");
    }

    /*
     * 9. Verplaatsen van een soldatenmier
     */
    // a. Een soldatenmier verplaatst zich door een onbeperkt aantal keren te verschuiven.
    // het is nogal moeilijk om een onbeperkt aantal keren te verschuiven te testen, dus een aantal test dat hij hele bord rond kan moet genoeg zijn
    @Test
    void testUnlimitedMovesForSoldierAntStrategy() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SoldierAntStrategy strategy = new SoldierAntStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(0, -1)));
    }

    @Test
    void testFarMoveForSoldierAntStrategy() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, -2), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SoldierAntStrategy strategy = new SoldierAntStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(0, -3)));
    }

    // b. Een soldatenmier mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    void testIfSoldierAntCannotBeMovedToItsSamePosition() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SoldierAntStrategy strategy = new SoldierAntStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    // c. Een soldatenmier mag alleen verplaatst worden over en naar lege velden.
    @Test
    void testIfSoldierAntCanOnlyBeMovedOverAndToEmptyFields() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SoldierAntStrategy strategy = new SoldierAntStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(2, -1), new Coordinate(0, 0)));
    }

    /*
     * 10. Verplaatsen van een spin
     */
    // a. Een spin verplaatst zich door precies drie keer te verschuiven.
    @Test
    void testIfSpiderCanSlideThreeSpaces() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, -1)));
        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(-1, 1)));
    }

    @Test
    void testIfSpiderCanOnlySlideThreeSpaces() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(0, -1)));
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(-1, 0)));
    }

    // b. Een spin mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    void testIfSpiderCannotMoveToItsOwnField() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    // c. Een spin mag alleen verplaatst worden over en naar lege velden.
    @Test
    void testIfSpiderCanOnlySlideAndMovedToEmptyFields() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(2, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(3, -1), new Coordinate(0, 0)));
    }

    // d. Een spin mag tijdens zijn verplaatsing geen stap maken naar een veld waar hij tijdens de verplaatsing al is geweest.
    @Test
    void testIfSpiderCannotMoveToAPlaceWhereHeHasBeenDuringHisMovement() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(2, 0)));
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(0, 2)));
    }

    @Test
    void testIfOnlyTwoMovesAreAvailable() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        ArrayList<Coordinate> availableMoves = strategy.availableMoves(board, new Coordinate(1, 1));
        Coordinate move1 = availableMoves.get(0);
        Coordinate move2 = availableMoves.get(1);

        assertEquals(2, availableMoves.size());
        assertEquals(0, (move1.q + move2.q) + (move1.r + move2.r)); // this should be mathematically correct
    }

    /*
     * 11. Verplaatsen van een sprinkhaan
     */
    // a. Een sprinkhaan verplaatst zich door in een rechte lijn een sprong te maken naar een veld meteen achter een andere steen in de richting van de sprong.
    @Test
    void testIfGrassHopperMovesByJumpingOverOtherTilesInAStraightLineAndStopsExactlyAtLastTileInTheRow() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(-2, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(2, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(3, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(4, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        GrassHopperStrategy strategy = new GrassHopperStrategy();

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(-3, 0), new Coordinate(5, 0)));
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(-3, 0), new Coordinate(2, -1)));
    }

    @Test
    void testIfGrassHopperCanJumpOutOfAPrisonedState() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(-1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(-1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        GrassHopperStrategy strategy = new GrassHopperStrategy();

        ArrayList<Coordinate> availableMoves = strategy.availableMoves(board, new Coordinate(0, 0));
        assertEquals(6, availableMoves.size());

        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(2, 0)));
    }

    // b. Een sprinkhaan mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    void testIfGrassHopperCannotMoveToItsOwnLocation() {
        Board board = spy(Board.class);
        SpiderStrategy strategy = new SpiderStrategy();
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    // c. Een sprinkhaan moet over minimaal één steen springen.
    @Test
    void testIfGrassHopperHasToMoveOverAtLeastOneOtherTile() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(0, 1)));
        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, -1)));
    }

    // d. Een sprinkhaan mag niet naar een bezet veld springen.
    @Test
    void testIfGrassHopperCannotMoveToAnOccupiedTile() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
            put(new Coordinate(1, -1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, -1)));
    }

}
