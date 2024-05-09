package geometries;

/**
 * All shapes containing a radius
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * radius
     */
    protected final double radius;

    /**
     * Constructor to initialize RadialGeometry receives double radius
     *
     * @param radius radius
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
