import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * This test class will have a few games instances to test if the complete flow of the game is correct
 */
class GameInstancesTest {

    @Test
    void testInstanceOnCrazyLocations() throws Hive.IllegalMove {
        Random r = new Random();
        int startQ = r.nextInt((3000 - 1000) + 1) + 1000;
        int startR = r.nextInt((3000 - 1000) + 1) + 1000;
        HiveGame game = new HiveGame();
        game.play(Hive.TileType.QUEEN_BEE, startQ, startR); // w
        game.play(Hive.TileType.SOLDIER_ANT, startQ + 1, startR); // b
        game.play(Hive.TileType.SPIDER, startQ - 1, startR + 1); // w
    }

    @Test
    void testTieInstance() {

    }

    @Test
    void testWhiteWinInstance() {

    }

    @Test
    void testBlackWinInstance() {

    }

    @Test
    void testInstance1() {

    }

    @Test
    void testInstanceWithMinusLocationNumbers() {

    }

    @Test
    void testInstanceWhereToGroupsWillExist() {

    }

    @Test
    void testInstanceWhereAntTriesToFillAGapInTheHive() {

    }

}
