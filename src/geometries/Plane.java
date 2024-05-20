package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * This class contains functions and calculations on a Plane
 */
public class Plane implements Geometry {
    private final Point q;
    private final Vector normal;

    /**
     * Constructor to initialize Plane - receives 3 Points
     *
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        if(p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("It is not possible to build a plane with less than 3 different points");

        Vector v1 = new Vector(p2.subtract(p1).getXYZ());
        Vector v2 = new Vector(p3.subtract(p1).getXYZ());
        Vector v = v1.crossProduct(v2);
        if(v.getXYZ().getD1() == 0 && v.getXYZ().getD2() == 0 && v.getXYZ().getD3() == 0)
            throw new IllegalArgumentException("The three points lie on the same lines");

        q = p1;
        v1 = new Vector((p2.subtract(p1)).getXYZ());
        v2 = new Vector((p3.subtract(p1)).getXYZ());
        normal = (v1.crossProduct(v2)).normalize();
    }

    /**
     * Constructor to initialize Plane - receives Point and Vector
     *
     * @param p1     point
     * @param vector vector
     */
    public Plane(Point p1, Vector vector) {
        q = p1;
        this.normal = vector.normalize();
    }

    /**
     * Get function - return the q point
     *
     */
    public Point getQ() {return q;}

    @Override
    public Vector getNormal(Point p1) {
        return normal;
    }

    /**
     * GetNormal function
     *
     * @return normal
     */
    public Vector getNormal() {
        return normal;
    }
}
