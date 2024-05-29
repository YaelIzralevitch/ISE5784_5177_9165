package geometries;

import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Plane class
 * @author Shir Perez and Yael Izralevitch
 */
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
    }


    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane pl = new Plane(new Point(1, 2, 3),
                             new Point(2, 4, 6),
                             new Point(3, 5, 9));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray crosses the plane (1 point)
        final Point p01 = new Point(1, 1 , 1);
        final Vector v01 = new Vector(1, 1, 1);
        List<Point> exp = List.of(new Point(3, 3 , 3));
        final List<Point> result = pl.findIntersections(new Ray(p01, v01));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(exp, result, "Ray crosses plane");

        // TC02: The ray doesn't crosse the plane (0 point)
        result = pl.findIntersections(new Ray(p01, v01.scale(-1)));
        assertEquals(null, result, "Ray crosses plane");

        // =============== Boundary Values Tests ==================
        // TC11: The ray is parallel to the plane (0 point)
        final Point pNotOnPlane = new Point(0,0,1);
        final Vector v02 = new Vector(1,2,3);

        result = pl.findIntersections(new Ray(pNotOnPlane, v02));
        assertEquals(null, result, "Ray crosses plane");

        // TC12: The ray merges with the plane (0 point)
        final Point pOnPlane1 = new Point(1,0,3);

        result = pl.findIntersections(new Ray(pOnPlane1, v02));
        assertEquals(null, result, "Ray crosses plane");

        // TC13: The ray is orthogonal to the plane and starts in front the plane (0 point)
        final Point pAbovePlane = new Point(1,1,10);
        final Vector v03 = new Vector(3,0,-1);

        result = pl.findIntersections(new Ray(pAbovePlane, v03));
        assertEquals(null, result, "Ray crosses plane");

        // TC14: The ray is orthogonal to the plane and starts in the plane (0 point)
        final Point pOnPlane2 = new Point(2,0,0);

        result = pl.findIntersections(new Ray(pOnPlane2, v03));
        assertEquals(null, result, "Ray crosses plane");

        // TC15: The ray is orthogonal to the plane and starts after the plane (1 point)
        exp = List.of(new Point(0.3, 0, 0.9));
        result = pl.findIntersections(new Ray(pNotOnPlane, v03));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(exp, result, "Ray crosses plane");

        // TC16: The ray start in the plane and doesn't orthogonal to the plane (0 point)
        result = pl.findIntersections(new Ray(pOnPlane1, v02));
        assertEquals(null, result, "Ray crosses plane");

        // TC17: The ray start at the plane point q (0 point)
        result = pl.findIntersections(new Ray(pl.getQ(), v02));
        assertEquals(null, result, "Ray crosses plane");
    }
}