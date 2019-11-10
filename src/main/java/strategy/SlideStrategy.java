package strategy;

import game.Board;
import game.Coordinate;
import game.Field;
import game.Hive;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class SlideStrategy implements MoveStrategy {

    public boolean canSlide(Board board, Coordinate from, Coordinate to) throws Hive.IllegalMove {
        ArrayList<Coordinate> commonNeighbours = from.getCommonNeighbours(to);
        if (commonNeighbours.size() < 2) {
            throw new Hive.IllegalMove("You can only slide to neighbouring fields");
        }
        HashMap<Coordinate, Field> playField = board.getPlayField();
        Field n1 = playField.get(commonNeighbours.get(0));
        Field n2 = playField.get(commonNeighbours.get(0));

        Field a = playField.get(from);
        Field b = playField.get(to);

        if (!checkHeights(a, b, n1, n2)) {
            throw new Hive.IllegalMove("Heights of neighbours are not allowing to slide to this position");
        }
        return true;
    }

    /**
     * Some tile types move by sliding
     * @param board The board
     * @param from The direction where the tile is right now
     * @param dirQ The direction to slide in (should be -1, 0, 1)
     * @param dirR The direction to slide in (should be -1, 0, 1)
     */
    private void slide(Board board, Coordinate from, int dirQ, int dirR) throws Hive.IllegalMove {
        if (dirQ > 1 || dirQ < -1 || dirR > 1 || dirR < -1) {
            throw new Hive.IllegalMove("You can only slide one field");
        }
        if (canSlide(board, from, new Coordinate(from.q + dirQ, from.r + dirR))) {

        }
    }

    private boolean checkHeights(Field a, Field b, Field n1, Field n2) {
        return Math.min(n1.getTiles().size(), n2.getTiles().size()) <= Math.max(a.getTiles().size() - 1, b.getTiles().size());
    }

    abstract Integer getSlideLimit();

}
