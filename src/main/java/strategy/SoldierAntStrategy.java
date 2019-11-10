package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class SoldierAntStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        Coordinate from = new Coordinate(fromQ, fromR);
        for (Coordinate neighbour : from.getNeighbours()) {

        }
        return moves;
    }

    @Override
    Integer getSlideLimit() {
        return null; // no limit
    }
}
