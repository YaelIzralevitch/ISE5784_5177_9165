package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;
/**
 * Intersectable interface for any geometric body
 */
public interface Intersectable {
    /**
     * findIntsersections function
     *
     * @param ray Ray
     * @return List of intersection points between the ray and the geometry.
     */
    List<Point> findIntsersections(Ray ray);
}
