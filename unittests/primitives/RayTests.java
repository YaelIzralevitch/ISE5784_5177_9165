package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RayTests {

    /**
     * Test method for {@link Ray#{MethodName}(${Parameters})}
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
}