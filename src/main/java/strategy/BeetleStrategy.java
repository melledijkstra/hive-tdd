package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class BeetleStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        // beetle can only slide one space
        if(!from.isNeighbour(to) && canSlide(board, from, to)) {
            throw new Hive.IllegalMove("Beetle can only move to neighbouring fields");
        }

        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        Coordinate from = new Coordinate(fromQ, fromR);
        for (Coordinate neighbour : from.getNeighbours()) {
            try {
                if (canSlide(board, from, neighbour)) {
                    moves.add(neighbour);
                }
            } catch (Hive.IllegalMove ignored) {
            }
        }
        return moves;
    }

    @Override
    Integer getSlideLimit() {
        return 1;
    }
}
