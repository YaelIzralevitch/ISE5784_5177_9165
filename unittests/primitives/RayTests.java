package primitives;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray class
 * @author Shir Perez and Yael Izralevitch
 */
class RayTests {

    /**
     * Test method for {@link Ray#{getPoint}(${Parameters})}
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(1,0,0), new Vector(0,0,1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - for a negative distance (t)
        assertEquals(new Point(1, 0,-2),
                    ray.getPoint(-2),
                    "TC01: getPoint() did not return a valid point");

        // TC02: Test - for a positive distance (t)
        assertEquals(new Point(1, 0,2),
                ray.getPoint(2),
                "TC02: getPoint() did not return a valid point");


        // =============== Boundary Values Tests ==================
        // TC11: Test - for a zero distance (t)
        assertEquals(ray.getHead(),
                ray.getPoint(0),
                "TC11: getPoint() did not return the ray head point");

    }

    /**
     * Test method for {@link Ray#{findClosestPoint}(${Parameters})}
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(1,2,3),
                          new Vector(4,5,6));

        Point p1 = new Point(5,7,9);
        Point pClosestToRay = new Point(3,4.5,6);
        Point p2 = new Point(9,12,15);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - for a point in the middle of the list is the one closest to the beginning of the ray
        List<Point> points = List.of(p1, pClosestToRay, p2);
        assertEquals(pClosestToRay, ray.findClosestPoint(points), "The point is incorrect");

        // =============== Boundary Values Tests ==================
        // TC11: Test - for an empty list
        points = List.of();
        assertNull(ray.findClosestPoint(points), "A point is returned even though the list is empty");

        // TC12: Test - for the first point is closest to the beginning of the ray
        points = List.of(pClosestToRay, p1, p2);
        assertEquals(pClosestToRay, ray.findClosestPoint(points), "The point is incorrect");

        // TC13: Test - for the last point is closest to the beginning of the ray
        points = List.of(p1, p2, pClosestToRay);
        assertEquals(pClosestToRay, ray.findClosestPoint(points), "The point is incorrect");

    }

}