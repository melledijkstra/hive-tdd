import game.*;
import org.junit.jupiter.api.Test;
import strategy.BeetleStrategy;
import strategy.QueenBeeStrategy;
import strategy.SoldierAntStrategy;
import strategy.SpiderStrategy;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MoveStrategyTest {

    /*
     * 7. Verplaatsen van een kever
     */
    //a. Een kever verplaatst zich door precies één keer te verschuiven.
    @Test
    void testBeetleStrategy() throws Hive.IllegalMove {
        // beetle moves by sliding once
        Board board = spy(Board.class);
        Tile beetleTile = new Tile(Hive.Player.WHITE, Hive.TileType.BEETLE);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(beetleTile));
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
            put(new Coordinate(1, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.BEETLE)));
        }});
        BeetleStrategy strategy = new BeetleStrategy();
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 0), new Coordinate(0, -1)));
    }

    /*
     * 8. Verplaatsen van de bijenkoningin
     */
    @Test
    void testQueenBeeStrategy() {
        Board board = new Board();
        board.place(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE), 0, 0);
        board.place(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE), 1, 0);
        // moves by sliding once
        QueenBeeStrategy strategy = new QueenBeeStrategy();
        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(1, -1)));
    }

    // a. De bijenkoningin verplaatst zich door precies één keer te verschuiven.
    @Test
    void testIfQueenCanOnlyMoveBySlidingOnce() {
        Board board = new Board();
        board.place(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE), 0, 0);
        board.place(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE), 1, 0);

        QueenBeeStrategy strategy = new QueenBeeStrategy();
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(2, -1)));
    }

    // b. De bijenkoningin mag alleen verplaatst worden naar een leeg veld.
    @Test
    void testIfQueenCanOnlyBeMovedToEmptyField() {
        Board board = new Board();
        board.place(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE), 0, 0);
        board.place(new Tile(Hive.Player.BLACK, Hive.TileType.SPIDER), 1, 0);
        board.place(new Tile(Hive.Player.BLACK, Hive.TileType.BEETLE), 1, -1);

        QueenBeeStrategy strategy = new QueenBeeStrategy();
        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(0, 0), new Coordinate(1, -1)), "Queen bee can only be moved to empty fields");
    }

    /*
     * 9. Verplaatsen van een soldatenmier
     */
    // a. Een soldatenmier verplaatst zich door een onbeperkt aantal keren te verschuiven.
    // todo: hoe dit te testen
//    @Test
//    void testUnlimitedMovesForSoldierAntStrategy() {
//        Board board = spy(Board.class);
//        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
//            put(new Coordinate(0, 0), new Field(any(Tile.class)));
//            put(new Coordinate(1, 0), new Field(any(Tile.class)));
//            put(new Coordinate(0, 1), new Field(any(Tile.class)));
//            put(new Coordinate(1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SOLDIER_ANT)));
//        }});
//        SoldierAntStrategy strategy = new SoldierAntStrategy();
//        strategy.canMove();
//    }

    // b. Een soldatenmier mag zich niet verplaatsen naar het veld waar hij al staat.
    @Test
    void testIfSoldierAntCannotBeMovedToItsSamePosition() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(any(Tile.class)));
            put(new Coordinate(1, 0), new Field(any(Tile.class)));
            put(new Coordinate(0, 1), new Field(any(Tile.class)));
            put(new Coordinate(1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SOLDIER_ANT)));
        }});
        SoldierAntStrategy strategy = new SoldierAntStrategy();

        assertThrows(Hive.IllegalMove.class, () -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, 1)));
    }

    // c. Een soldatenmier mag alleen verplaatst worden over en naar lege velden.
    @Test
    void testIfSoldierAntCanOnlyBeMovedOverAndToEmptyFields() {
        // todo: hoe testen we dit?
    }

    @Test
    void testSpiderStrategy() {
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(any(Tile.class)));
            put(new Coordinate(1, 0), new Field(any(Tile.class)));
            put(new Coordinate(0, 1), new Field(any(Tile.class)));
            put(new Coordinate(1, 1), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.SPIDER)));
        }});
        SpiderStrategy strategy = new SpiderStrategy();
        assertDoesNotThrow(() -> strategy.canMove(board, new Coordinate(1, 1), new Coordinate(1, -1)));

        // has to slide 3 times

        // cannot move to the field where its located

        // can only move over and to empty fields

        // not allowed to slide to a position where it has been during the move
        throw new NotImplementedException();
    }

    @Test
    void testSpider() {

    }


    @Test
    void testGrassHopperStrategy() {
        // moves by jumping over other tiles in a straight line exactly after another tile in the direction of the jump

        // cannot move to the position where its located

        // has to move over at least one other tile

        // cannot move to a field which is occupied

        // cannot jump over empty fields, every tile in the sequence has to be occupied
        throw new NotImplementedException();
    }

}
