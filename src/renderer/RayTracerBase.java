package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * This abstract class contains functions on a RayTracerBase
 */
public abstract class RayTracerBase {
    protected final Scene scene;

    /**
     * Parameter constructor
     * @param scene
     */
    public RayTracerBase(Scene scene){
        this.scene = scene;
    }

    /**
     * traceRay - abstract function
     * @param ray
     */
    public abstract Color traceRay(Ray ray);
}
