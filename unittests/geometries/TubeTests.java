package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Tube#{MethodName}(${Parameters})}
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(1,0,2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Tube tb = new Tube(1, new Ray(new Point(0, 0, 0), new Vector(0,0,1)));

        // ensure there are no exceptions
        assertDoesNotThrow(() -> tb.getNormal(p1), "the function throw an exception");
        // generate the test result
        Vector result = tb.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Tube's normal is not an unit vector");

        // =============== Boundary Values Tests ==================
        // TC11: Test - if the (head - point) orthogonal to the ray of the tube
        assertDoesNotThrow(() -> tb.getNormal(new Point(1, 0,0)), "the function throw an exception");
    }
}