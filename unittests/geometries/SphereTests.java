package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SphereTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Sphere#{MethodName}(${Parameters})}
     */
    @Test
    void testGetNormal() {
        Point p1 = new Point(1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Sphere sp = new Sphere(1, new Point(0, 0, 0));

        // ensure there are no exceptions
        assertDoesNotThrow(() -> sp.getNormal(p1), "the function throw an exception");
        // generate the test result
        Vector result = sp.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Sphere's normal is not an unit vector");
    }
}