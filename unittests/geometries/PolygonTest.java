package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.Polygon;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Unit tests for primitives.Polygon class
 * @author Shir Perez and Yael Izralevitch
 */
public class PolygonTest {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /** Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(
                        new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /** Test method for {@link geometries.Polygon#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts = { new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1) };
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not an unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {geometries.Polygon#${findIntersections}}
     */
    @Test
    void testFindIntersections() {
        Polygon pol = new Polygon(new Point(0, 0, 0),
                                        new Point(1, 0, 0),
                                        new Point(1, 1, 0),
                                        new Point(0,1,0));

        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray cross the Polygon (1 point)
        final Point p01 = new Point(0.5, 0.5 , -1);
        final Vector v01 = new Vector( 0, 0, 1);
        List<Point> exp = List.of(new Point(0.5, 0.5 , 0));
        List<Point> result = pol.findIntersections(new Ray(p01, v01));
        assertEquals(1, result.size(), "TC01: Wrong number of points");
        assertEquals(exp, result, "TC01: Ray crosses Polygon");

        // TC02: The ray is outside the Polygon in front of edge (0 points)
        final Vector v02 = new Vector( 0, 0, -1);
        result = pol.findIntersections(new Ray(p01, v02));
        assertNull(result, "TC02: Ray crosses Polygon");

        // TC03: The ray is outside the Polygon in front of a vertex (0 points)
        final Point p03 = new Point(0.5, -1 , 0);
        final Vector v03 = new Vector(1, 0, 0);

        result = pol.findIntersections(new Ray(p03, v03));
        assertNull(result, "TC03: Ray crosses Polygon");

        // =============== Boundary Values Tests ==================
        // TC11: The ray starts on edge (0 point)
        final Vector v11 = new Vector(0, 1, 0);

        result = pol.findIntersections(new Ray(p01, v11));
        assertNull(result, "TC11: Ray crosses Polygon");

        // TC12: The ray starts on continue edge (0 point)
        result = pol.findIntersections(new Ray(p01, v11));
        assertNull(result, "TC12: Ray crosses Polygon");

        // TC13: The ray starts on vertex (0 point)
        final Vector v13 = new Vector(1, 1 , 0);

        result = pol.findIntersections(new Ray(p01, v13));
        assertNull(result, "TC13: Ray crosses Polygon");

    }

}
