package geometries;

import primitives.Point;
import primitives.Vector;

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
        q = p1;
        normal = null;
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

    @Override
    public Vector getNormal(Point p1) {
        return null;
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
