package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * Composite class for all the geometries
 */
public class Geometries implements Intersectable{
    final private List<Intersectable> geometries = new LinkedList<>();

    /**
     * empty constructor
     */
    public Geometries(){ }

    /**
     * constructor
     */
    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    /**
     * Gets a collection of bodies and puts them into the geometries attribute
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries,geometries);
    }

    /**
     * findIntersections function
     *
     * @param ray Ray
     * @return List of intersection points between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersectionsPoints = null;
        List<Point> geometryPoints; //intersections points in one geometry

        //The loop goes through the bodies and checks if each one has intersection points,
        //if so it will add them to geometryPoints collection

        for(Intersectable geometry: this.geometries) {
            geometryPoints = geometry.findIntersections(ray);
            if(geometryPoints != null){
                //if there are no Point in the intersectionsPoints collection yet
                if(intersectionsPoints == null){
                    intersectionsPoints = new LinkedList<>();
                }
                intersectionsPoints.addAll(geometryPoints);
            }
        }

        if(intersectionsPoints == null){
            return null;
        }

        return intersectionsPoints
                .stream()
                .sorted(Comparator.comparingDouble(p -> p.distance(ray.getHead())))
                .toList();
    }
}
