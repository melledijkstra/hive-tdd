package strategy;

import game.Hive;

public class MoveStrategyFactory {

    public MoveStrategy createMoveStrategy(Hive.TileType type) {
        MoveStrategy strategy = null;
        switch (type) {
            case QUEEN_BEE:
                strategy = new QueenBeeStrategy();
                break;
            case SPIDER:
                strategy = new SpiderStrategy();
                break;
            case BEETLE:
                strategy = new BeetleStrategy();
                break;
            case GRASSHOPPER:
                strategy = new GrassHopperStrategy();
                break;
            case SOLDIER_ANT:
                strategy = new SoldierAntStrategy();
                break;
        }
        return strategy;
    }

}
