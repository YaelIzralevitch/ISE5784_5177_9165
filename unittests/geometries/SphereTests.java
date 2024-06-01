package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Sphere class
 * @author Shir Perez and Yael Izralevitch
 */
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

    /**
     * Test method for { geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        final Point p100 = new Point(1, 0, 0);
        Sphere sphere = new Sphere(1, p100);
        final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> exp = List.of(gp1, gp2);
        final Vector v310 = new Vector(3, 1, 0);
        final Vector v120 = new Vector(3, 4, 0);
        final Point p01 = new Point(-1, 0, 0);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(p01, v120)), "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        List<Point> result = sphere.findIntersections(new Ray(p01, v310));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(exp, result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        final Point p02 = new Point(1, -0.5 , 0);
        final Vector v02 = new Vector(0, 1, 0);
        exp = List.of(new Point(1,1,0));
        result = sphere.findIntersections(new Ray(p02, v02));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(exp, result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point(-3, 0, 0), new Vector(-2, 3, 1))), "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 point)
        final Point p11 = new Point(2, 0, 0);
        final Vector v11 = new Vector(-1, 0, 1);
        exp = List.of(new Point(1,0,1));
        result = sphere.findIntersections(new Ray(p11, v11));
        assertEquals(1, result.size(), "TC11: Wrong number of points");
        assertEquals(exp, result, "TC11: Ray crosses sphere");

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(p11, new Vector(1, 0, 0)));
        assertEquals(null, result, "TC12: Ray crosses sphere");

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        final Point p12 = new Point(1,0, 2);
        final Vector v12 = new Vector(0, 0, -1);
        exp = List.of(new Point(1,0,1), new Point(1,0,-1));
        result = sphere.findIntersections(new Ray(p12, v12));
        assertEquals(2, result.size(), "TC13: Wrong number of points");
        assertEquals(exp, result, "TC13: Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 point)
        final Point p13 = new Point(1,0, 1);
        exp = List.of(new Point(1,0,-1));
        result = sphere.findIntersections(new Ray(p13, v12));
        assertEquals(1, result.size(), "TC14: Wrong number of points");
        assertEquals(exp, result, "TC14: Ray crosses sphere");

        // TC15: Ray starts inside (1 point)
        final Point p14 = new Point(1,0, 0.5);
        result = sphere.findIntersections(new Ray(p14, v12));
        assertEquals(1, result.size(), "TC15: Wrong number of points");
        assertEquals(exp, result, "TC15: Ray crosses sphere");

        // TC16: Ray starts at the center (1 point)
        result = sphere.findIntersections(new Ray(p100, v12));
        assertEquals(1, result.size(), "TC16: Wrong number of points");
        assertEquals(exp, result, "TC16: Ray crosses sphere");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(p13, v12.scale(-1)));
        assertEquals(null, result, "Ray crosses sphere");

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray(p12, v12.scale(-1)));
        assertEquals(null, result, "TC17: Ray crosses sphere");

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        Point pOnTangent = new Point(1, Math.sqrt(2), 0);
        final Vector vOnTangent = new Vector(-1 * (Math.sqrt(2)/2), (Math.sqrt(2)/2), 0);
        result = sphere.findIntersections(new Ray(pOnTangent, vOnTangent));
        assertEquals(null, result, "TC19: Ray crosses sphere");

        // TC20: Ray starts at the tangent point
        final Point pOnSphere = new Point(1 + (Math.sqrt(2)/2), Math.sqrt(2)/2, 0);
        result = sphere.findIntersections(new Ray(pOnSphere, vOnTangent));
        assertEquals(null, result, "TC20: Ray crosses sphere");

        // TC21: Ray starts after the tangent point
        pOnTangent = new Point(1, 1, 1);
        result = sphere.findIntersections(new Ray(pOnTangent, new Vector(0,0,1)));
        assertEquals(null, result, "TC21: Ray crosses sphere");

        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        Point pOutsideSphere = new Point(3, 2, 1);
        final Vector v15 = new Vector(1, -1, 0);
        result = sphere.findIntersections(new Ray(pOutsideSphere, v15));
        assertEquals(null, result, "TC22: Ray crosses sphere");

    }
}