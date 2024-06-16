package primitives;

import static primitives.Util.isZero;
import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * This class contains functions and calculations on a Ray
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructor with parameters
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * @return  head
     */
    public Point getHead() { return head; }

    /**
     * @return direction
     */
    public Vector getDirection() { return direction; }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray ray)
                && this.head.equals(ray.head)
                && this.direction.equals(ray.direction);

    }

    /**
     * toString function
     */
    @Override
    public String toString() {
        return this.head + " " + this.direction;
    }

    /**
     * The method calculates a point on the line of the beam, at a distance given by the head of the fund
     * @param t number
     */
    public Point getPoint(double t){
        if(isZero(t)){
            return this.head;
        }
        return this.head.add(this.direction.scale(t));
    }

    /**
     * findClosestPoint function
     * @return the point closest to the beginning of the ray
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        if (geoPoints.isEmpty())
            return null;

        GeoPoint geoPClosest = geoPoints.getFirst();
        for( GeoPoint gp : geoPoints){
            if (this.head.distance(gp.point) < this.head.distance(geoPClosest.point)){
                geoPClosest = gp;
            }
        }
        return geoPClosest;
    }
}
