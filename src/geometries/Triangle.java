package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * This class is a type of Polygon with 3 points
 */
public class Triangle extends Polygon {
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        return null;
    }
}
