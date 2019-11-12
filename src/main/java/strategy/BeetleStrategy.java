package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class BeetleStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        // beetle can only slide one space and only is able to slide to it
        if(!from.isNeighbour(to)) {
            throw new Hive.IllegalMove("Beetle can only move to neighbouring fields");
        }
        if (!canSlide(board, from, to)) {
            throw new Hive.IllegalMove("Beetle cannot slide to this position");
        }

        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Coordinate from) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for (Coordinate neighbour : from.getNeighbours()) {
            if (canSlide(board, from, neighbour)) {
                moves.add(neighbour);
            }
        }
        return moves;
    }

    @Override
    Integer getDistanceLimit() {
        return 1;
    }
}
