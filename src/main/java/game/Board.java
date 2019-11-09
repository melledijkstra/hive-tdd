package game;

import java.util.HashMap;

public class Board {

    private HashMap<Coordinate, Field> playField;

    public Board() {
        playField = new HashMap<>();
    }

    public HashMap<Coordinate, Field> getPlayField() {
        return playField;
    }

    public void place(Tile tile, int q, int r) {
        Field field = playField.get(new Coordinate(q, r));
        if (field == null) {
            playField.put(new Coordinate(q, r), new Field(tile));
        } else {
            field.addTile(tile);
        }
    }

}
