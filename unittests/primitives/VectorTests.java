package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for primitives.Vector class
 * @author Shir Perez and Yael Izralevitch
 */
class VectorTests {

    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testAdd() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2         = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - add two vectors
        assertEquals(new Vector(-1, -2,-3), v1.add(v2));

        // =============== Boundary Values Tests ==================
        // TC11: Test - add vector to its opposite
        assertThrows(IllegalArgumentException.class,
                     ()-> v1.add(v1Opposite),
                     "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testLengthSquared() {
        Vector v4         = new Vector(1, 2, 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - squared length of vector
        assertEquals(9, v4.lengthSquared(), DELTA, "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testLength() {
        Vector v4         = new Vector(1, 2, 2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - length of vector
        assertEquals(3, v4.length(), DELTA, "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testScale() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v2         = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - scale vector with number
        assertEquals(v2, v1.scale(-2), "the calculations is wrong");

        // =============== Boundary Values Tests ==================
        // TC11: Test - scale vector with zero
        assertThrows(IllegalArgumentException.class,
                     ()-> v1.scale(0),
                     "scale() with zero number does not throw an exception");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testDotProduct() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v2         = new Vector(-2, -4, -6);
        Vector v3         = new Vector(0, 3, -2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test - dotProduct between two vectors
        assertEquals(-28 ,
                v1.dotProduct(v2),
                DELTA,
                "ERROR: dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        // TC11: Test - dotProduct between two vectors with result zero
        assertEquals(0 ,
                v1.dotProduct(v3),
                DELTA,
                "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testCrossProduct() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2         = new Vector(-2, -4, -6);
        Vector v3         = new Vector(0, 3, -2);
        Vector v4         = new Vector(1, 2, 2);

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), DELTA, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v3), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), //
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testNormalize() {
        Vector v1         = new Vector(1, 2, 3);
        Vector u = v1.normalize();

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the length of the normal is 1
        assertEquals(1, u.length(), DELTA, "ERROR: the normalized vector is not a unit vector");


        // =============== Boundary Values Tests ==================
        // TC11: test that the vectors are co-lined
        assertThrows(Exception.class, () -> v1.crossProduct(u),
                "ERROR: the normalized vector is not parallel to the original one");

        // TC12: test that the normal is not opposite to the original one
        assertFalse(() -> v1.dotProduct(u) < 0, "ERROR: the normalized vector is opposite to the original one");
    }
}