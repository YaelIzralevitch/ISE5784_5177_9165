package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface for any geometric body
 */
public abstract class Geometry extends Intersectable{
    protected Color emission = Color.BLACK;

    /**
     * getEmission function
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setEmission function
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * GetNormal function
     *
     * @param p1 Point
     * @return Normal to the Point p1
     */
    public abstract Vector getNormal(Point p1);
}
