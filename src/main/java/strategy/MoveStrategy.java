package strategy;

import game.Board;
import game.Coordinate;
import game.Hive;

import java.util.ArrayList;

public interface MoveStrategy {

    /**
     * Test if move is possible for this type of strategy
     * @param board The current state of the board
     * @param from coordinates from
     * @param to coordinates to
     * @return If this tile can make this move with this strategy
     * @throws Hive.IllegalMove If the move cannot be made an exception is thrown
     */
    boolean canMove(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove;

    /**
     * Generate the amount of moves a player can make with this strategy
     * @param board The current state of the board
     * @param player The player to make the move
     * @param fromQ The Q from position
     * @param fromR The R from position
     * @return The coordinates of all the places where the player can make a move to
     */
    ArrayList<Coordinate> availableMoves(Board board, Hive.Player player, int fromQ, int fromR);

}
