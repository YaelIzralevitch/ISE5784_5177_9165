package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

/**
 * This class contains functions and calculations on a Tube
 */
public class Tube extends RadialGeometry {
    /**
     * Ray axis
     */
    protected final Ray axis;

    /**
     * Constructor to initialize Tube
     *
     * @param radius double
     * @param axis   Ray
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p1) {
        double t = axis.getDirection().dotProduct(p1.subtract(axis.getHead()));
        Point o;
        if (!isZero(t))
            o = axis.getPoint(t);
        else
            o = axis.getHead();
        return o.subtract(p1).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
