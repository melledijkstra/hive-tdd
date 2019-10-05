import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HiveGameTest {

    private HiveGame game;

    @BeforeEach
    void setup() {
        game = new HiveGame();
    }

    @Test
    void testPlay() {
        // first set every stone can be set
        for (Hive.Tile value : Hive.Tile.values()) {
            assertDoesNotThrow(() -> game.play(value, 0, 0));
        }
    }

    @Test
    void testMove() {
        assertDoesNotThrow(() -> game.move(0, 0, 1, 0));
    }

    @Test
    void testIsWinner() {
        assertFalse(game.isWinner(Hive.Player.WHITE));
    }



}