import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testEquals() {
        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 0);
        Coordinate c3 = new Coordinate(1, 0);
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
    }

    @Test
    void testToString() {
        Coordinate c1 = new Coordinate(0, 0);
        assertEquals("(0,0)", c1.toString());
    }
}