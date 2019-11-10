package strategy;

import game.*;

import java.util.ArrayList;

public class QueenBeeStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        // bee can only slide one space
        if(!from.isNeighbour(to)) {
            throw new Hive.IllegalMove("Queen bee can only move to neighbouring fields");
        }
        // bee is only allowed to go to empty fields
        if (board.getPlayField().get(to) != null && !board.getPlayField().get(to).getTiles().isEmpty()) {
            throw new Hive.IllegalMove("Queen bee can only be moved to empty fields");
        }

        return true;
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        Coordinate from = new Coordinate(fromQ, fromR);
        for (Coordinate neighbour : from.getNeighbours()) {
            try {
                Field field = board.getPlayField().get(neighbour);
                if (canSlide(board, from, neighbour) && field != null && field.getTiles().isEmpty()) {
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
