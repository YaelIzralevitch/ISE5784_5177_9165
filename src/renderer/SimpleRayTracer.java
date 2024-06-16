package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

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
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        if (points == null)
            return scene.background;
        GeoPoint p = ray.findClosestGeoPoint(points);
        return calcColor(p);
    }

    /**
     * calcColor function
     * @param gp
     */
    private Color calcColor(GeoPoint gp){
        return scene.ambientLight.getIntensity().add(gp.geometry.getEmission());
    }
}
