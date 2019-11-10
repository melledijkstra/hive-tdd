package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class SpiderStrategy extends SlideStrategy {

    protected Integer depth = 3;

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR) {
        return null;
    }

    @Override
    Integer getSlideLimit() {
        return 3;
    }
}
