package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
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
        Vector n = v1.crossProduct(v2);

        if(n.getXYZ().getD1() == 0 && n.getXYZ().getD2() == 0 && n.getXYZ().getD3() == 0)
            throw new IllegalArgumentException("The three points lie on the same lines");

        q = p1;
        normal = n.normalize();
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        //If the ray starts at a point representing the plane
        if(this.q.equals(ray.getHead()))
            return null;

        double t1 = this.normal.dotProduct(this.q.subtract(ray.getHead()));
        double t2 = this.normal.dotProduct(ray.getDirection());

        //If the ray is parallel to the plane
        if(isZero(t2))
            return null;

        double t = t1 / t2;

        if(alignZero(t) > 0) {
            return List.of(ray.getHead().add(ray.getDirection().scale(t)));
        }
        return null ;
    }
}
