package primitives;

/**
 * This class contains functions and calculations on a Point
 */
public class Point {
    /**
     * zero Point
     */
    public static final Point ZERO = new Point(Double3.ZERO);
    /**
     * Double3 coordinates
     */
    protected final Double3 xyz;

    /**
     * Constructor to initialize Point - receives 3 coordinates
     *
     * @param x first coordinate
     * @param y second coordinate
     * @param z third coordinate
     */
    public Point(double x, double y, double z) {
        this(new Double3(x, y, z));
    }

    /**
     * Constructor to initialize Point - receives Double3 object
     *
     * @param xyz coordinates
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point point)
                && this.xyz.equals(point.xyz);
    }

    /**
     * Subtract function - receives point and subtract two points
     *
     * @param p1 point
     */
    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    /**
     * Add function - receives vector and adds this vector to a point - returns a new point
     *
     * @param v1 vector
     */
    public Point add(Vector v1) {
        return new Point(xyz.add(v1.xyz));
    }

    /**
     * DistanceSquared function - receives point and returns distance squared
     *
     * @param p1 point
     */
    public double distanceSquared(Point p1) {
        return (this.xyz.d1 - p1.xyz.d1) * (this.xyz.d1 - p1.xyz.d1)
                + (this.xyz.d2 - p1.xyz.d2) * (this.xyz.d2 - p1.xyz.d2)
                + (this.xyz.d3 - p1.xyz.d3) * (this.xyz.d3 - p1.xyz.d3);
    }

    /**
     * Distance function - the function calculates the distance between two points
     *
     * @param p1 point
     */
    public double distance(Point p1) {
        return Math.sqrt(distanceSquared(p1));
    }

    //toString function
    @Override
    public String toString() {
        return "" + this.xyz;
    }
}
