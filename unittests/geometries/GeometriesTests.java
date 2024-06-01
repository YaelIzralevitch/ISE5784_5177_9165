package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTests {
    /**
     * Test method for {@link Geometries#{findIntersections}(${Parameters})}
     */
    @Test
    void testFindIntersections() {

        // Plane
        Plane pl = new Plane(new Point(1, 0, 0),
                new Point(2, 0, 0),
                new Point(1.5,0, 1));


        // Sphere
        Sphere sphere = new Sphere(1, new Point(1, 0, 1));

        // Triangle
        Triangle tr = new Triangle(new Point(0, 2, 0),
                new Point(2, 2, 0),
                new Point(1.5, 2, 2));

        Vector v = new Vector(0, -1, 0);

        Geometries g = new Geometries(pl, sphere, tr);
        // ============ Equivalence Partitions Tests ==============
        // TC01: The ray crosses few geometries - not all (3 point)
        Point p01 = new Point(1, 1.5, 1);


        List<Point> result = g.findIntersections(new Ray(p01, v));
        assertEquals(3, result.size(), "TC01: Wrong number of points");

        // =============== Boundary Values Tests ==================
        // TC11: There are no geometries in the collection (0 point)
        Geometries gEmpty = new Geometries();
        Point p11 = new Point(1, -1, 1);
        assertNull(gEmpty.findIntersections(new Ray(p11, v)), "There is no geometries");


        // TC12: The ray doesn't cross any geometries in the collection (0 point)
        assertNull(g.findIntersections(new Ray(p11, v)), "TC12: Ray crosses geometries");

        // TC13: The ray cross one geometry from the collection (1 point)
        Point p12 = new Point(1.5, 1.5, 0.5);
        Vector v12 = new Vector(0, 1, 0);

        result = g.findIntersections(new Ray(p12, v12));
        assertEquals(1, result.size(), "TC13: Wrong number of points");

        // TC14: The ray cross all geometries in the collection (4 point)
        Point p13 = new Point(1, 2.5, 1);

        result = g.findIntersections(new Ray(p13, v));
        assertEquals(4, result.size(), "TC14: Wrong number of points");
    }
}