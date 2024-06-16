package lighting;

import primitives.Color;
import primitives.Vector;

/**
 * Spotlight class
 */
public class SpotLight extends PointLight{

    private Vector direction;

    /**
     * Parameter constructor
     *
     * @param intensity
     */
    protected SpotLight(Color intensity) {
        super(intensity);
    }
}
