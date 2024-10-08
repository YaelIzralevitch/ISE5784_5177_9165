package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.*;
import static java.lang.Math.*;
import static primitives.Util.*;

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
        return (p1.subtract(center)).normalize();
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if(ray.getHead().equals(this.center)){
            return List.of(new GeoPoint(this, this.center.add((ray.getDirection().scale(this.radius)))));
        }

        Vector u = this.center.subtract(ray.getHead());
        double tm = alignZero(ray.getDirection().dotProduct(u));
        double d = sqrt(u.lengthSquared() - (tm * tm));

        //If the distance is large and equal to the radius of the circle - there are no intersection points
        if(d >= this.radius)
            return null;

        double th = sqrt(radius * radius - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        //There is at least one intersection point
        //If there are 2 intersection points
        if(alignZero(t1) > 0
           && alignZero(t2) > 0
           && alignZero(t1 - maxDistance) <= 0
           && alignZero(t2 - maxDistance) <= 0){

            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2))
                    .stream()
                    .sorted(Comparator.comparingDouble(p -> p.point.distance(ray.getHead())))
                    .toList();
        }
        //If there is one intersection point
        if((alignZero(t1) > 0) && (alignZero(t1 - maxDistance) <= 0)){
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this, p1));
        }
        if((alignZero(t2) > 0) && (alignZero(t2 - maxDistance) <= 0)) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this, p2));
        }

        return null;
    }

    /**
     * getCenter function
     */
    public Point getCenter() {
        return center;
    }

    /**
     * getRadius function
     */
    public double getRadius() {
        return radius;
    }
}














