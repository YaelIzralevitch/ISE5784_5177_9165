package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * findIntersections function
     *
     * @param ray Ray
     * @return List of intersection points between the ray and the geometry.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
