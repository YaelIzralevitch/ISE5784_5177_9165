package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * This class contains functions and calculations on ambient light
 */
public class AmbientLight extends Light {

    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Parameters constructor - Calculates the final intensity of fill lighting maintained in the field intensity
     * @param Ia - Light intensity according to RGB components
     * @param Ka - Fill light attenuation coefficient
     */
    public AmbientLight(Color Ia, Double3 Ka){
        super(Ia.scale(Ka));
    }

    /**
     * Parameters constructor - Calculates the final intensity of fill lighting maintained in the field intensity
     * @param Ia - Light intensity according to RGB components
     * @param Ka - Fill light attenuation coefficient
     */
    public AmbientLight(Color Ia, double Ka){
        super(Ia.scale(Ka));
    }

}
