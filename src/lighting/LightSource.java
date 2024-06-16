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
     * getL function
     * @param p
     */
    public Vector getL(Point p);

}
