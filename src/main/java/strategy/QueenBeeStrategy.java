package strategy;

import game.Board;
import game.Hive;
import game.Tile;

public class QueenBeeStrategy implements MoveStrategy {

    @Override
    public void move(Board board, Tile tile, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        // todo: check if queen bee can make this move
        board.place(tile, toQ, toR);
    }

}
