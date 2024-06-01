package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTests {

    /**
     * Test method for {Triangle#${findIntersections}}
     */
    @Test
    void testFindIntersections() {
        Triangle tr = new Triangle(new Point(0, 1, 0),
                                   new Point(0, 5, 0),
                                   new Point(0, 3, 5));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray cross the triangle (1 point)
        final Point p01 = new Point(1, 3 , 0);
        final Vector v01 = new Vector( -1, 0, 1);
        List<Point> exp = List.of(new Point(0, 3 , 1));
        List<Point> result = tr.findIntersections(new Ray(p01, v01));
        assertEquals(1, result.size(), "TC01: Wrong number of points");
        assertEquals(exp, result, "TC01: Ray crosses triangle");

        // TC02: The ray is outside the triangle in front of edge (0 points)
        final Point p02 = new Point(1, 0 , 0);

        result = tr.findIntersections(new Ray(p02, v01));
        assertEquals(null, result, "TC02: Ray crosses triangle");

        // TC03: The ray is outside the triangle in front of a vertex (0 points)
        final Vector v03 = new Vector(-1, 0.1, -0.1);

        result = tr.findIntersections(new Ray(p02, v03));
        assertEquals(null, result, "TC03: Ray crosses triangle");

        // =============== Boundary Values Tests ==================
        // TC11: The ray starts on edge (0 point)
        final Point p11 = new Point(1, 3 , 0);
        final Vector v11 = new Vector(-1, 0, 0);

        result = tr.findIntersections(new Ray(p11, v11));
        assertEquals(null, result, "TC11: Ray crosses triangle");

        // TC12: The ray starts on continue edge (0 point)
        final Vector v12 = new Vector(-1, 0.1, 0);

        result = tr.findIntersections(new Ray(p02, v12));
        assertEquals(null, result, "TC12: Ray crosses triangle");

        // TC13: The ray starts on vertex (0 point)
        final Point p13 = new Point(1, 1 , 0);

        result = tr.findIntersections(new Ray(p13, v11));
        assertEquals(null, result, "TC13: Ray crosses triangle");

    }
}