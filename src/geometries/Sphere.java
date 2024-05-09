package geometries;


import primitives.Point;
import primitives.Vector;

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
        return null;
    }
}
