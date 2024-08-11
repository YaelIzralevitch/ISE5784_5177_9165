package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;
/**
 * Intersectable for any geometric body
 */
public abstract class Intersectable {
    /**
     * findIntersections function:
     * find all intersections points that intersect with a specific ray
     * @param ray  the ray to intersect a geometry/geometries
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * findGeoIntersections function:
     * find all intersections points that intersect with a specific ray
     * @param ray  the ray to intersect a geometry/geometries
     * @return list of intersection points
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * findGeoIntersections function:
     * find all intersections points that intersect with a specific ray
     * @param ray  the ray to intersect a geometry/geometries
     * @param maxDistance  max intersection distance
     * @return list of intersection points
     */
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }


    /**
     * findIntersections helper
     * @param ray
     * @return
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * PDS static internal helper class - GeoPoint
     */
    public static class GeoPoint {
        /** The geometry that the point is on **/
        public Geometry geometry;
        /** point **/
        public Point point;

        /**
         * Parameter constructor
         *
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint geoPoint)
                    && this.geometry.equals(geoPoint.geometry)
                    && this.point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
