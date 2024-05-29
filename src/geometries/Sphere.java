package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Arrays;
import java.util.List;

/**
 * This class contains functions and calculations on a Sphere
 */
public class Sphere extends RadialGeometry {
    private final Point center;

    /**
     * Constructor to initialize Sphere
     *
     * @param radius double
     * @param center Point
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p1) {
        return (p1.subtract(center)).normalize();
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }


}
