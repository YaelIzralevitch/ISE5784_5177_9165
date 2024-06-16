package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;
import static primitives.Util.*;

/**
 * This class is a type of Polygon with 3 points
 */
public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        //Finding an intersection with the plane of the triangle
        List<Point> intersectionWithPlane =  this.plane.findIntersections(ray);

        if(intersectionWithPlane == null)
            return null;

        Vector v1 = vertices.get(0).subtract(ray.getHead());
        Vector v2 = vertices.get(1).subtract(ray.getHead());
        Vector v3 = vertices.get(2).subtract(ray.getHead());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        double s1 = ray.getDirection().dotProduct(n1);
        double s2 = ray.getDirection().dotProduct(n2);
        double s3 = ray.getDirection().dotProduct(n3);

        //If one of the scalar products is zero - no cutting
        if(isZero(s1) || isZero(s2) || isZero(s3))
            return null;

        //If all the scalar lines have the same sign - then the intersection with the plane cuts the triangle
        if((s1 < 0 && s2 < 0 && s3 < 0 ) || (s1 > 0 && s2 > 0 && s3 > 0))
            return  List.of(new GeoPoint(this, intersectionWithPlane.getFirst()));;

        return null;
    }


}
