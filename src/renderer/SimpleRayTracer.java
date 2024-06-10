package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * This class contains functions on a SimpleRayTracer
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Parameter constructor
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * traceRay - abstract function
     * @param ray
     */
    @Override
    public Color traceRay(Ray ray) {
        List<Point> points = scene.geometries.findIntersections(ray);
        if (points == null)
            return scene.background;
        Point p = ray.findClosestPoint(points);
        return calcColor(p);
    }

    /**
     * calcColor function
     * @param p
     */
    private Color calcColor(Point p){
        return scene.ambientLight.getIntensity();
    }
}
