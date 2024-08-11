package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * LightSource interface
 */
public interface LightSource {
    /**
     * getIntensity function
     * @param p
     */
    public Color getIntensity(Point p);

    /**
     * getL function - get the direction of the light towards the point
     * @param p
     */
    public Vector getL(Point p);

    /**
     * Get distance between a point and the light source
     * @param p the lighted point
     * @return the distance
     */
    double getDistance(Point p);

}
