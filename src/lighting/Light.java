package lighting;

import primitives.Color;

/**
 * This class contains functions and calculations on light
 */
abstract class Light {
    protected final Color intensity;

    /**
     * Parameter constructor
     * @param intensity
     */
    protected Light(Color intensity){
        this.intensity = intensity;
    }

    /**
     * getIntensity function
     */
    public Color getIntensity() {
        return intensity;
    }
}
