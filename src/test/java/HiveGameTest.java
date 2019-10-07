import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HiveGameTest {

    private HiveGame game;

    @BeforeEach
    void setup() {
        game = new HiveGame();
    }

    // 3. Spelverloop
    @Test
    void testIfWhiteFirstPlayer() {
        // a. Wit heeft de eerste beurt.
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
    }

    @Test
    void testThatPlayerChanges() {
        assertEquals(Hive.Player.WHITE, game.getCurrentPlayer());
        try {
            game.play(Hive.Tile.QUEEN_BEE, 0, 0);
        } catch (Hive.IllegalMove ignored) {
        }
        assertEquals(Hive.Player.BLACK, game.getCurrentPlayer());
    }

    @Test
    void testAvailableStartingTiles() {
        // Elke speler heeft aan het begin van het spel de beschikking over één
        // bijenkoningin, twee spinnen, twee kevers, drie soldatenmieren en drie
        // sprinkhanen in zijn eigen kleur.

        // ArrayList<Hive.Tile> whiteStones = game.getAvailableTiles(Hive.Player.WHITE);
    }

    // b. Tijdens zijn beurt kan een speler een steen spelen, een steen verplaatsen of passen;
    // daarna is de tegenstander aan de beurt.

    // c. Een speler wint als alle zes velden naast de bijenkoningin van de tegenstander bezet zijn.

    // d. Als beide spelers tegelijk zouden winnen is het in plaats daarvan een gelijkspel.

    @Test
    void testPlay() {
        // first set every stone can be set
        assertDoesNotThrow(() -> game.play(Hive.Tile.QUEEN_BEE, 0, 0));
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