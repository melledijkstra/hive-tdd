import game.*;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class PassTest {

    @Test
    void testPass() throws Hive.IllegalMove { // player can only pass when there is no other option left
        // setup game
        HiveGame game = spy(HiveGame.class);
        when(game.getPlayerTiles(Hive.Player.WHITE)).thenReturn(generateEmptyTileSet());
        // setup board
        Board board = spy(Board.class);
        when(board.getPlayField()).thenReturn(generatePassField());
        game.setBoard(board);
        // retrieve all the possible plays and moves a player can do
        HashMap<Coordinate, ArrayList<Coordinate>> actions = board.getPossiblePlaysAndMoves(Hive.Player.WHITE);

        assertEquals(0, actions.size());

        game.pass(); // should be allowed for white
    }

    @Test
    void testPassNotAllowed() throws Hive.IllegalMove {
        // setup game
        HiveGame game = new HiveGame();
        assertThrows(Hive.IllegalMove.class, game::pass); // w  // not allowed for white to do this
        game.play(Hive.TileType.BEETLE, 0, 0); // w
        game.play(Hive.TileType.BEETLE, 1, 0); // b
        assertThrows(Hive.IllegalMove.class, game::pass);
    }

    private HashMap<Coordinate, Field> generatePassField() {
        throw new NotImplementedException();
//        return new HashMap<Coordinate, Field>() {{
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//            put(new Coordinate(0, 0), new Field(new Tile(Hive.Player.WHITE, Hive.TileType.QUEEN_BEE)));
//        }};
    }


    private HashMap<Hive.TileType, Integer> generateEmptyTileSet() {
        return new HashMap<Hive.TileType, Integer>() {{
            put(Hive.TileType.QUEEN_BEE, 0);
            put(Hive.TileType.SOLDIER_ANT, 0);
            put(Hive.TileType.BEETLE, 0);
            put(Hive.TileType.GRASSHOPPER, 0);
            put(Hive.TileType.SPIDER, 0);
        }};
    }


}
