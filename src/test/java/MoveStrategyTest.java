import game.*;
import org.junit.jupiter.api.Test;
import strategy.BeetleStrategy;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class MoveStrategyTest {

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
        strategy.move(board, beetleTile, 1, 0, 1, -1);
    }

    @Test
    void testIncorrectMoveOfBeetle() {
        Board board = spy(Board.class);
        Tile beetleTile = new Tile(Hive.Player.WHITE, Hive.TileType.BEETLE);
        when(board.getPlayField()).thenReturn(new HashMap<Coordinate, Field>() {{
            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(0, 1), new Field(new Tile(Hive.Player.BLACK, Hive.TileType.QUEEN_BEE)));
            put(new Coordinate(1, 0), new Field(beetleTile));
        }});
        BeetleStrategy strategy = new BeetleStrategy();
        assertThrows(Hive.IllegalMove.class, () -> strategy.move(board, beetleTile, 1, 0, 0, -1));
    }


    @Test
    void testQueenBeeStrategy() {
        // moves by sliding once

        // can only be moved to an empty field
        throw new NotImplementedException();
    }

    @Test
    void testIfQueenIsNotAbleToStack() {

        // assertThrows(IllegalMove.class)
        throw new NotImplementedException();
    }

    @Test
    void testSoldierAntStrategy() {
        // able to slide unlimited times

        // cannot move to the field where its located

        // can only move over and to empty fields
        throw new NotImplementedException();
    }

    @Test
    void testSpiderStrategy() {
        // has to slide 3 times

        // cannot move to the field where its located

        // can only move over and to empty fields

        // not allowed to slide to a position where it has been during the move
        throw new NotImplementedException();
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
