package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.awt.peer.ChoicePeer;
import java.util.ArrayList;
import java.util.HashSet;

public class GrassHopperStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Board board, Coordinate from, Coordinate end) throws Hive.IllegalMove {
        if (from.equals(end)) throw new Hive.IllegalMove("Grasshopper cannot move to its own position");
        if (availableMoves(board, from).contains(end)) {
            return true;
        } else {
            throw new Hive.IllegalMove("Grasshopper cannot move to this location");
        }
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Coordinate from) {
        ArrayList<Coordinate> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {-1, 1}, {1, 0}, {1, -1}}; // [q, r]
        for (int[] direction : directions) { // for all directions hop until you stop ;p
            Coordinate next = new Coordinate(from.q + direction[0], from.r + direction[1]);
            if(board.isOccupied(next)) { // only hop if the next one has a tile
                moves.add(hop(board, from, direction[0], direction[1]));
            }
        }
        return moves;
    }

    private Coordinate hop(Board board, Coordinate from, int dirQ, int dirR) {
        Coordinate next = new Coordinate(from.q + dirQ, from.r + dirR);
        if (board.isOccupied(next)) { // continue hopping if its occupied
            return hop(board, next, dirQ, dirR);
        } else { // if the next one is not hoppable anymore, we can return this coordinate
            return next;
        }
    }

}
