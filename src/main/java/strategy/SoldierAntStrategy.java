package strategy;

import game.Board;
import game.Hive;
import game.Tile;

public class SoldierAntStrategy implements MoveStrategy {

    @Override
    public void move(Board board, Tile tile, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove {
        board.place(tile, toQ, toR);
    }

}
