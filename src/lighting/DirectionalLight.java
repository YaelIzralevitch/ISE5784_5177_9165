package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * DirectionalLight class
 */
public class DirectionalLight extends Light implements LightSource{

    private final Vector direction;

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
     * @param p point
     * @return the intensity color of the point
     */
    @Override
    public Color getIntensity(Point p) {
        return this.intensity;
    }

    /**
     * getL function - get the direction of the light towards the point
     * @param p point
     */
    @Override
    public Vector getL(Point p) {
        return this.direction.normalize();
    }

    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
