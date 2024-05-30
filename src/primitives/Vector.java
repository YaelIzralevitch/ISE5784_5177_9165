package primitives;

import static primitives.Util.isZero;

/**
 * This class contains functions and calculations on a Vector
 */
public class Vector extends Point {
    /**
     * Constructor to initialize Vector - receives 3 coordinates
     *
     * @param x first coordinate
     * @param y second coordinate
     * @param z third coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Double3(x,y,z));
    }

    /**
     * Constructor to initialize Vector - receives Double3 object
     *
     * @param double3 coordinates
     */
    public Vector(Double3 double3) {
        super(double3);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("This is the zero vector");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }

    /**
     * Add function
     *
     * @param vector vector
     * @return a new vector
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * LengthSquared function
     *
     * @return length squared
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Length function
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Scale function
     *
     * @param num scalar
     * @return a new vector
     */
    public Vector scale(double num) {
        if(isZero(num))
            throw new IllegalArgumentException("A vector cannot be scale with 0");

        return new Vector(this.xyz.scale(num));
    }

    /**
     * DotProduct function
     *
     * @param vector vector
     * @return scalar
     */
    public double dotProduct(Vector vector) {
        return (this.xyz.d1 * vector.xyz.d1)
                + (this.xyz.d2 * vector.xyz.d2)
                + (this.xyz.d3 * vector.xyz.d3);
    }

    /**
     * CrossProduct function
     *
     * @param vector vector
     * @return the vertical vector to the 2 vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d2,
                -(this.xyz.d1 * vector.xyz.d3 - this.xyz.d3 * vector.xyz.d1),
                this.xyz.d1 * vector.xyz.d2 - this.xyz.d2 * vector.xyz.d1);
    }

    /**
     * Normalize function
     *
     * @return the vector normalize
     */
    public Vector normalize() {
        double vectorLength = this.length();
        return new Vector(this.xyz.d1 / vectorLength,
                          this.xyz.d2 / vectorLength,
                          this.xyz.d3 / vectorLength);
    }
}
