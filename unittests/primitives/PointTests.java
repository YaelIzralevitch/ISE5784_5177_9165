package primitives;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Point class
 * @author Shir Perez and Yael Izralevitch
 */
class PointTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Point#{MethodName}(${Parameters})}
     */
    @Test
    void testSubtract() {
        Point  p1         = new Point(1, 2, 3);
        Point  p2         = new Point(2, 4, 6);
        Vector v1         = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - subtract between two points
        assertEquals(v1, p2.subtract(p1), "ERROR: (point2 - point1) does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test - subtract point to itself throw exception
        assertThrows(IllegalArgumentException.class,
                ()-> p1.subtract(p1),
                "ERROR: (point - itself) does not throw an exception");
    }

    /**
     * Test method for {@link Point#{MethodName}(${Parameters})}
     */
    @Test
    void testAdd() {
        Point  p1         = new Point(1, 2, 3);
        Point  p2         = new Point(2, 4, 6);
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - add between two points
        assertEquals(p2, p1.add(v1),
                "ERROR: (point + vector) = other point does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC11: Test - add point to opposite vector given zero
        assertEquals(Point.ZERO, p1.add(v1Opposite),
                "ERROR: (point + vector) = center of coordinates does not work correctly");

    }

    /**
     * Test method for {@link Point#{MethodName}(${Parameters})}
     */
    @Test
    void testDistanceSquared() {
        Point  p1         = new Point(1, 2, 3);
        Point  p3         = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - squared distance between two points
        assertEquals(9,
                p1.distanceSquared(p3),
                DELTA,
                "ERROR: squared distance between points is wrong");

        assertEquals(9,
                p3.distanceSquared(p1),
                DELTA,
                "ERROR: squared distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: Test - squared distance point to itself given zero
        assertEquals(0,
                p1.distanceSquared(p1),
                DELTA,
                "ERROR: point squared distance to itself is not zero");
    }

    /**
     * Test method for {@link Point#{MethodName}(${Parameters})}
     */
    @Test
    void testDistance() {
        Point  p1         = new Point(1, 2, 3);
        Point  p3         = new Point(2, 4, 5);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - distance between two points
        assertEquals(3,
                p1.distance(p3),
                DELTA,
                "ERROR: distance between points is wrong");

        assertEquals(3,
                p3.distance(p1),
                DELTA,
                "ERROR: distance between points is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: Test - distance point to itself given zero
        assertEquals(0,
                p1.distance(p1),
                DELTA,
                "ERROR: point distance to itself is not zero");
    }

}