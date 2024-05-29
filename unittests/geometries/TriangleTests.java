package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    /**
     * Test method for {@link Triangle#${MethodName}(${Parameters})}
     */
    @Test
    void testFindIntsersections() {
        Plane pl = new Plane(new Point(2, 0, 0),
                             new Point(0, 4, 0),
                             new Point(0, 0, 3));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray inside the triangle (1 point)
        final Point p01 = new Point(1, 1 , 1);
        final Vector v01 = new Vector(1, 0, -1);
        List<Point> exp = List.of(new Point(0.5, 1 , 1.5));
        final List<Point> result = pl.findIntersections(new Ray(p01, v01));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(exp, result, "Ray crosses triangle");

        // TC02: The ray is outside the triangle in front of edge (0 points)
        final Point p02 = new Point(3, 1 , 1);
        final Vector v02 = new Vector(-2, 4, 0);

        result = pl.findIntersections(new Ray(p02, v02));
        assertEquals(null, result, "Ray crosses triangle");

        // TC03: The ray is outside the triangle in front of a vertex (0 points)
        final Point p03 = new Point(3, 3 , 0);
        final Vector v03 = new Vector(1, 0, 1);

        result = pl.findIntersections(new Ray(p03, v03));
        assertEquals(null, result, "Ray crosses triangle");

        // =============== Boundary Values Tests ==================
        // TC11: The ray starts on edge (0 point)
        final Point p11 = new Point(0, 0 , 1);
        final Vector v11 = new Vector(1, 2, 0);

        result = pl.findIntersections(new Ray(p11, v11));
        assertEquals(null, result, "Ray crosses triangle");

        // TC12: The ray starts on continue edge (0 point)
        final Point p12 = new Point(4, -4 , 0);
        final Vector v12 = new Vector(0, 0, 1);

        result = pl.findIntersections(new Ray(p12, v12));
        assertEquals(null, result, "Ray crosses triangle");

        // TC13: The ray starts on vertex (0 point)
        final Point p13 = new Point(2, 0 , 0);
        final Vector v13 = new Vector(1, 1, 1);

        result = pl.findIntersections(new Ray(p13, v13));
        assertEquals(null, result, "Ray crosses triangle");

    }
}