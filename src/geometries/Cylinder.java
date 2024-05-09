package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
        return null;
    }

}
