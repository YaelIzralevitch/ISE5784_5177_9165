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
     * Parameters constructor
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
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
        return this.direction.normalize();
    }
}
