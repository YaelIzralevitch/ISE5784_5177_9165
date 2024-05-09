package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface for any geometric body
 */
public interface Geometry {
    /**
     * GetNormal function
     *
     * @param p1 Point
     * @return Normal to the Point p1
     */
    public Vector getNormal(Point p1);
}
