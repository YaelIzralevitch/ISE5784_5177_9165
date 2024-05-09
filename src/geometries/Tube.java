package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

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
        return null;
    }
}
