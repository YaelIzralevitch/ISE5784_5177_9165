package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * Geometry interface for any geometric body
 */
public abstract class Geometry extends Intersectable{
    protected Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * getEmission function
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * getMaterial function
     */
    public Material getMaterial() { return material; }

    /**
     * setEmission function
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * setMaterial function
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * GetNormal function
     * @param p1 Point
     * @return Normal to the Point p1
     */
    public abstract Vector getNormal(Point p1);
}
