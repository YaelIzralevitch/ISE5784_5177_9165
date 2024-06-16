package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class
 */
public class PointLight extends Light implements LightSource {

    protected Point position;
    private double kC, kL, kQ;

    /**
     * Parameter constructor
     *
     * @param intensity
     */
    protected PointLight(Color intensity) {
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
