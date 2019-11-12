package game;

import strategy.MoveStrategyFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    public HashMap<Coordinate, ArrayList<Coordinate>> getPossiblePlaysAndMoves(Hive.Player player) {
        // this stores all the possible moves, key is from coordinate, the arraylist is filled with coordinates to play to from this coordinate
        HashMap<Coordinate, ArrayList<Coordinate>> possibleMoves = new HashMap<>();
        MoveStrategyFactory strategyFactory = new MoveStrategyFactory();
        for (Map.Entry<Coordinate, Field> entry : getPlayField().entrySet()) { // loop through all the fields on the board
            Tile upperTile = entry.getValue().peek();
            if (upperTile != null && // only upperTile can be played
                    upperTile.getColor() == player) { // check if there is a tile on this field from this player
                Coordinate from = entry.getKey();
                ArrayList<Coordinate> moves = strategyFactory
                        .createMoveStrategy(upperTile.getType())
                        .availableMoves(this, from); // get all the moves from this position with this strategy
                if (!moves.isEmpty()) {
                    possibleMoves.put(from, moves);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Check if a position has neighbours
     */
    public boolean positionHasNeighbours(Coordinate position) {
        HashMap<Coordinate, Field> board = getPlayField();
        boolean hasNeighbours = false;
        for (Coordinate neighbour : position.getNeighbours()) {
            Field nbrField = board.get(neighbour);
            if (nbrField != null && !nbrField.getTiles().isEmpty()) { // if field exists and tiles exist, he has a neighbour
                hasNeighbours = true;
                break;
            }
        }
        return hasNeighbours;
    }

    public boolean positionHasNeighbours(int q, int r) {
        return positionHasNeighbours(new Coordinate(q, r));
    }

    public boolean isOccupied(Coordinate coordinate) {
        // the field is occupied when it exists and it has a least one tile
        return getPlayField().get(coordinate) != null && !getPlayField().get(coordinate).getTiles().isEmpty();
    }

    public boolean canPlace(Hive.Player currentPlayer) {
        if (getPlayField().isEmpty()) { // first play move is always allowed on empty board
            return true;
        }
        for (Field field : getPlayField().values()) {
            if (field.peek() != null && // check if there is a tile on this field
                    field.peek().getColor() == currentPlayer // check if the top tile is owned by current player
            ) {
                return true; // if top tile is owned by current player he can place a new tile
            }
        }
        return false; // otherwise not...
    }
}
