package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class contains functions and calculations on a Cylinder
 */
public class Cylinder extends Tube {
    private final double height;

    /**
     * Constructor to initialize Cylinder
     *
     * @param radius double
     * @param axis   Ray
     * @param height double
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p1) {

        if (p1.equals(axis.getHead())) { //The point is in the middle of the base
            return axis.getDirection().scale(-1);
        }

        //Checking where on the cylinder is the point p1
        Vector fromHeadToP1 = p1.subtract(axis.getHead());
        double t = axis.getDirection().dotProduct(fromHeadToP1);

        //on the lower base
        if (isZero(t)) {
            return axis.getDirection().scale(-1);
        }
        //on the top base
        else if (isZero(t - height)) {
            return axis.getDirection();
        }
        //on the shell
        else {
            Point o = axis.getHead().add(axis.getDirection().scale(t));
            return o.subtract(p1).normalize();
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
