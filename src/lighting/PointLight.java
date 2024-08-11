package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class
 */
public class PointLight extends Light implements LightSource {

    private final Point position;
    /** As the distance increases the light will fade**/
    private double kC = 1;
    /** The weakening of light intensity due to the phenomenon of light scattering **/
    private double kL = 0;
    /** Weakening of light intensity due to the law of conservation of energy **/
    private double kQ = 0;

    /**
     * Parameters constructor
     * @param intensity light color
     * @param position  light source point
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * getIntensity function
     * @param p point
     * @return the intensity color of the point
     */
    @Override
    public Color getIntensity(Point p) {
        double d = this.position.distance(p);
        return this.intensity.scale(1d / (kC + kL * d + kQ * d * d));
    }

    /**
     * getL function - get the direction of the light towards the point
     * @param p point
     */
    @Override
    public Vector getL(Point p) {
        if (p.equals(this.position))
            return null;
        return p.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }

    /**
     * setkC function
     * @param kC
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setkL function
     * @param kL
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setkQ function
     * @param kQ
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
