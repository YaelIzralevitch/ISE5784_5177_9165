package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private final double DELTA = 0.000001;

    /**
     * Test method for {@link Cylinder#{MethodName}(${Parameters})}
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,0,2);
        Point p2 = new Point(0.5,0,0);
        Point p3 = new Point(0.5,0,10);
        // TC01: There is a simple single test here - using a quad
        Cylinder cy = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(0,0,1)), 10);

        // ensure there are no exceptions
        assertDoesNotThrow(() -> cy.getNormal(p1), "the function throw an exception");
        assertDoesNotThrow(() -> cy.getNormal(p2), "the function throw an exception");
        assertDoesNotThrow(() -> cy.getNormal(p3), "the function throw an exception");

        // generate the test result
        Vector result1 = cy.getNormal(p1);
        Vector result2 = cy.getNormal(p2);
        Vector result3 = cy.getNormal(p3);
        // ensure |result| = 1
        assertEquals(1, result1.length(), DELTA, "Cylinder's normal is not an unit vector");
        // ensure the result is orthogonal to the point in the Cylinder
        assertEquals(0d, result1.dotProduct(new Vector(p1.getXYZ())), DELTA,
                "Cylinder's normal is not orthogonal to the point");

        //  TC02: ensure that the normal from the base is not directed to the face of the cylinder
        assertTrue(result2.getXYZ().getD3() < 0, "Lower base normal is not oriented outwards");
        assertTrue(result3.getXYZ().getD3() < 0, "Higher base normal is not oriented outwards");


        // =============== Boundary Values Tests ==================
        Point p4 = new Point(0,0,10);
        Point p5 = new Point(0,0,0);
        // TC11: Test - normal to a point which is the middle of the base
        assertDoesNotThrow(() -> cy.getNormal(p4), "the function throw an exception");
        assertDoesNotThrow(() -> cy.getNormal(p5), "the function throw an exception");
    }
}