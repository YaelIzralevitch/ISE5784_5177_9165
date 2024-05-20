package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Plane#(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        assertDoesNotThrow(() -> new Plane(new Point(0, 0, 1),
                                            new Point(1, 0, 0),
                                            new Point(0, 1, 0)),
                              "Failed constructing a correct plane");

        // =============== Boundary Values Tests ==================
        // TC11: The first and second points are connected
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 1),
                                new Point(0, 0, 1),
                                new Point(0, 1, 0)),
                "ERROR: Constructor does not throw an exception");

        // TC12: The points are on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(0, 0, 1),
                                new Point(0, 0, 2),
                                new Point(0, 0, 3)),
                "ERROR: Constructor does not throw an exception");
    }


    /**
     * Test method for {@link Plane#{MethodName}(${Parameters})}
     **/
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Plane pl = new Plane(new Point(1, 2, 3),
                             new Point(2, 4, 6),
                             new Point(3, 5, 9));

        Point p1 = new Point(1, 2, 3);

        // ensure there are no exceptions
        assertDoesNotThrow(() -> pl.getNormal(p1), "the function throw an exception");
        // generate the test result
        Vector result = pl.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Plane's normal is not an unit vector");
        // ensure the result is orthogonal to the point in the plane
        assertEquals(0d, result.dotProduct(new Vector(new Double3(1,2,3))), DELTA,
                    "Plane's normal is not orthogonal to the point");
    }

}