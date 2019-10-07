import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HiveGameTest {

    private HiveGame game;

    @BeforeEach
    void setup() {
        game = new HiveGame();
    }

    // 3. Spelverloop
    @Test
    void testGameFlow() {
        // a. Wit heeft de eerste beurt.
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());

        // Onderstaande punten allemaal hun eigen test?
        // b. Tijdens zijn beurt kan een speler een steen spelen, een steen verplaatsen of passen;
        // daarna is de tegenstander aan de beurt.

        // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.

        // d. Als beide spelers tegelijk zouden winnen is het in plaats daarvan een gelijkspel.
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