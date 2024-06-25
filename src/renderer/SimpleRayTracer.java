package renderer;

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
     * calcColor function
     * @param gp
     * @param ray
     */
    private Color calcColor(GeoPoint gp, Ray ray){
        return scene.ambientLight.getIntensity().add(calcLocalEffects(gp, ray));
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

            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                                calcDiffusive(material.kD, l, n, iL)
                                .add(calcSpecular(material.kS, l, n, v, material.nShininess, iL)));
            }
        }
        return color;
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
}
