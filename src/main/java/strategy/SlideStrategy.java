package strategy;

import game.Board;
import game.Coordinate;
import game.Field;
import game.Hive;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class SlideStrategy implements MoveStrategy {

    public boolean canSlide(Board board, Coordinate from, Coordinate to) {
        ArrayList<Coordinate> commonNeighbours = from.getCommonNeighbours(to);
        if (commonNeighbours.size() < 2) {
            return false;
            // throw new Hive.IllegalMove("You can only slide to neighbouring fields");
        }
        HashMap<Coordinate, Field> playField = board.getPlayField();
        Field n1 = playField.get(commonNeighbours.get(0));
        Field n2 = playField.get(commonNeighbours.get(1));

        Field a = playField.get(from);
        Field b = playField.get(to);

        // todo: what is better? return true false or and exception?
        // if (checkHeights()) {
        //      ...
        // else {
        //      throw new Hive.IllegalMove("Heights of neighbours are not allowing to slide to this position");
        // }
        return checkHeights(
                a != null ? a.getTiles().size() : 0,
                b != null ? b.getTiles().size() : 0,
                n1 != null ? n1.getTiles().size() : 0,
                n2 != null ? n2.getTiles().size() : 0
        );
    }

    private boolean checkHeights(int a, int b, int n1, int n2) {
        return Math.min(n1, n2) <= Math.max(a - 1, b);
    }

    abstract Integer getDistanceLimit();

}
