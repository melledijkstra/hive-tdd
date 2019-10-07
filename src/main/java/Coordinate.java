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

}
