package strategy;

import game.Board;
import game.Hive;
import game.Tile;

public interface MoveStrategy {

    void move(Board board, Tile tile, int fromQ, int fromR, int toQ, int toR) throws Hive.IllegalMove;

}
