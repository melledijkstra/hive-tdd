package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class GrassHopperStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR) {
        return null;
    }

}
