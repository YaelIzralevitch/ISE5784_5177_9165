package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Plane#{MethodName}(${Parameters})}
     **/
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Plane pl = new Plane(new Point(0, 0, 1),
                             new Point(1, 0, 0),
                             new Point(0, 1, 0));

        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(new Point(0, 0, 1)), "the function throw an exception");
        // generate the test result
        Vector result = pl.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not an unit vector");
        // ensure the result is orthogonal to the point in the plane
        assertEquals(0d, result.dotProduct(new Vector(pl.getQ().getXYZ())), DELTA,
                    "Plane's normal is not orthogonal to the point");
    }

}