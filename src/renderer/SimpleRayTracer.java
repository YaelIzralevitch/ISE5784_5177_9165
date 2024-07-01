package renderer;

import lighting.DirectionalLight;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

/**
 * This class contains functions on a SimpleRayTracer
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * The distance of moving the ray head when needed
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Parameter constructor
     * @param scene the scene to trac
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * traceRay function
     * @param ray tay
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        if (points == null)
            return scene.background;
        GeoPoint p = ray.findClosestGeoPoint(points);
        return calcColor(p, ray);
    }

    /**
     * The method implements Simple Phong Reflectance model for calculating a point color.
     * @param gp point and its geometry
     * @param ray  incoming ray
     */
    private Color calcColor(GeoPoint gp, Ray ray){
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * The method implements Simple Phong Reflectance model for calculating a point
     * color.
     *
     * @param gp point and its geometry
     * @param ray incoming ray
     * @param level remaining levels for recursion
     * @param k accumulated attenuation factor for recursion
     * @return the final color
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color
                : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calcLocalEffects function - calculated light contribution from all light sources
     * @param gp the geo point we calculate the color of
     * @param ray      ray from the camera to the point
     * @return the color from the lights at the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = gp.geometry.getMaterial();
        Color color = gp.geometry.getEmission();

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));

            if ((nl * nv > 0) // sign(nl) == sing(nv)
                    && unshaded(gp, lightSource, l, n)) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                                calcDiffusive(material.kD, l, n, iL)
                                .add(calcSpecular(material.kS, l, n, v, material.nShininess, iL)));
            }
        }
        return color;
    }

    /**
     * Calculate global effects for the point color
     * @param gp    geoPoint
     * @param ray     ray
     * @param level recursion level
     * @param k   triple attenuation factor of reflection/refraction
     * @return the global effects color
    */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffects(constructRefractedRay(gp, ray), material.kR,level, k)
                .add(calcGlobalEffects(constructReflectedRay(gp, ray), material.kT,level, k));
    }

    /**
     * Function to construct transparency ray
     * @param point point
     * @param v  ray direction
     * @param n  normal
     * @return new refracted ray
    */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /**
     * Function to construct reflected ray
     * @param point point
     * @param v  ray direction
     * @param n  normal
     * @return new reflected ray
    */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        return new Ray(point,
                       v.subtract(n.scale(2 * v.dotProduct(n))),
                       n);
    }

    /**
     * Calculate a global effect - reflection or refraction
     * @param ray   reflected\refracted ray
     * @param level recursion level
     * @param k     accumulated recursion attenuation factor
     * @param kx    reflection/refraction attenuation factor
     * @return the global effect color
    */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background :calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    /**
     * Calculate the diffusive component of the light at this point
     *
     * @param kd diffusive component
     * @param l
     * @param n
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = l.normalize().dotProduct(n.normalize());
        return lightIntensity.scale(kd.scale(Math.abs(ln)));
    }


    /**
     * Calculate the Specular component of the light at this point
     * @param ks             specular component
     * @param l              direction from light to point
     * @param n              normal from the object at the point
     * @param v              direction from the camera to the point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity
     * @return the Specular component at the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
        double max = Math.max(0, -v.dotProduct(r));
        double maxNs = Math.pow(max, nShininess);
        Double3 ksMaxNs = ks.scale(maxNs);

        return lightIntensity.scale(ksMaxNs);
    }

    /**
     * The method checks whether there is any object shading the light source from a point
     *
     * @param gp the point with its geometry
     * @param light light source
     * @param l  direction from light to the point
     * @param n  normal to the point at the geometry surface
     * @return true if the light source is not shaded, false if it is
    */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        double nl = l.dotProduct(n);
        Ray ray = new Ray(gp.point, lightDirection, n);

        //Checking whether the intersection points are between the light and geo point - gp
        double distanceFromLight = light.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, distanceFromLight);

        if(intersections == null) return true;

        return false;
    }

}
