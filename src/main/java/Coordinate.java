import java.util.Objects;

public class Coordinate {

    private int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        else if (!(obj instanceof Coordinate))
            return false;

        Coordinate other = (Coordinate) obj;
        return this.x == other.x && this.y == other.y;
    }

    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }

}
