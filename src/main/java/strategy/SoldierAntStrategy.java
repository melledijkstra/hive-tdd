package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public class SoldierAntStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate start, Coordinate end) throws Hive.IllegalMove {
        // the soldier ant can move to the end location if it can be hit by a dfs search

        // todo: use dfs search to "walk" on the board to the end location
        // if location is not found, then this move is not possible
        // so all possible locations where the ant can go are the ones visited in dfs, use this for availableMoves
        ArrayList<Coordinate> visited = new ArrayList<Coordinate>() {{
            add(start);
        }};
        ArrayList<Coordinate> frontier = start.getNeighbours();
        Coordinate currentPos = start;
        for (Coordinate front : frontier) {
            if (canSlide(board, currentPos, front)) {

            }
        }

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
