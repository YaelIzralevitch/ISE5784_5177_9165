package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testTestEquals() {
        //TODO:?
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testTestToString() {
        //TODO:?
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testAdd() {
        Vector v1         = new Vector(1, 2, 3);
        Vector v1Opposite = new Vector(-1, -2, -3);
        Vector v2         = new Vector(-2, -4, -6);

        assertEquals(new Vector(-1, -2,-3), v1.add(v2));
        assertThrows(IllegalArgumentException.class,
                     ()-> v1.add(v1Opposite),
                     "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testLengthSquared() {
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testLength() {
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testScale() {
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testDotProduct() {
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector vr = v123.crossProduct(v03M2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v123.length() * v03M2.length(), vr.length(), DELTA, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, vr.dotProduct(v123), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, vr.dotProduct(v03M2), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(vM2M4M6), //
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link Vector#{MethodName}(${Parameters})}
     */
    @Test
    void testNormalize() {
    }
}