package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;
import java.util.HashSet;

public class SoldierAntStrategy extends SlideStrategy {

    @Override
    public boolean canMove(Board board, Coordinate start, Coordinate end) throws Hive.IllegalMove {
        if (start.equals(end)) throw new Hive.IllegalMove("Soldier Ant cannot move to its own position");
        // the soldier ant can move to the end location if it can be hit by a dfs search
        // so all possible locations where the ant can go are the ones visited in dfs
        if (dfs(board, start, new HashSet<>()).contains(end)) { // goal is reachable by dfs
            return true;
        } else {
            // if location is not found, then this move is not possible
            throw new Hive.IllegalMove("Soldier Ant cannot reach this position");
        }
    }

    @Override
    public ArrayList<Coordinate> availableMoves(Board board, Coordinate from) {
        // use the dfs function to get all the positions
        return new ArrayList<>(dfs(board, from, new HashSet<>()));
    }

    @Override
    Integer getDistanceLimit() {
        return null; // no limit
    }

    /**
     * Uses DFS to traverse through the board to find if this tiletype can move to the end position
     *
     * @param node    the current node to check
     * @param visited a list of visited nodes which doesnt need to be checked anymore
     * @return if the goal can be reached
     */
    private HashSet<Coordinate> dfs(Board board, Coordinate node, HashSet<Coordinate> visited) {
        visited.add(node);

        for (Coordinate frontier : node.getNeighbours()) {
            // only continue searching for the goal in this direction if the following conditions are true
            if (!visited.contains(frontier) && // didn't already visited this coordinate
                    canSlide(board, node, frontier) && // can slide from this position to the next position in the frontier
                    board.positionHasNeighbours(frontier) && // the checking position needs to have a neighbour otherwise there is no end to the board
                    !board.isOccupied(frontier) // field has to be empty of tiles
            ) {
                visited.addAll(dfs(board, frontier, visited)); // move to the position and check again if we can continue
            }
        }
        return visited;
    }
}
