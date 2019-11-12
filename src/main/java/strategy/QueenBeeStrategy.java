package strategy;

import game.*;

import java.util.ArrayList;

public class QueenBeeStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        // bee can only slide one space
        if (!from.isNeighbour(to)) {
            throw new Hive.IllegalMove("Queen bee can only move to neighbouring fields");
        }
        // bee is only allowed to go to empty fields
        if (board.getPlayField().get(to) != null && !board.getPlayField().get(to).getTiles().isEmpty()) {
            throw new Hive.IllegalMove("Queen bee can only be moved to empty fields");
        }

        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Coordinate from) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        for (Coordinate neighbour : from.getNeighbours()) {
            if (canSlide(board, from, neighbour) && !board.isOccupied(neighbour)) {
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
