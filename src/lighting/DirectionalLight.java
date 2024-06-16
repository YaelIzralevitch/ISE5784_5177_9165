package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class
 */
public class DirectionalLight extends Light implements LightSource{

    private Vector direction;

    /**
     * Parameter constructor
     *
     * @param intensity
     */
    protected DirectionalLight(Color intensity) {
        super(intensity);
    }

    /**
     * getIntensity function
     * @param p
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    /**
     * getL function
     * @param p
     */
    @Override
    public Vector getL(Point p) {
        return null;
    }
}
