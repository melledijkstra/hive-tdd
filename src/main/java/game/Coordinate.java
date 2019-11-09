package game;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate {

    private int q, r;

    public Coordinate(int q, int r) {
        this.q = q;
        this.r = r;
    }

    public int hashCode() {
        return Objects.hash(q, r);
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (!(obj instanceof Coordinate))
            return false;

        Coordinate other = (Coordinate) obj;
        return this.q == other.q && this.r == other.r;
    }

    public String toString() {
        return String.format("(%d,%d)", this.q, this.r);
    }

    public int[] getCoordinates() {
        return new int[]{q, r};
    }

    public ArrayList<Coordinate> getNeighbours() {
        return new ArrayList<Coordinate>() {{
            add(new Coordinate(q + 1, r));
            add(new Coordinate(q, r + 1));
            add(new Coordinate(q - 1, r + 1));
            add(new Coordinate(q - 1, r));
            add(new Coordinate(q, r - 1));
            add(new Coordinate(q + 1, r - 1));
        }};
    }
}
