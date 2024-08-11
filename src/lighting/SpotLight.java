package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * Spotlight class
 */
public class SpotLight extends PointLight{

    private final Vector direction;
    private int narrowBeam = 1;

    /**
     * Parameters constructor
     * @param intensity intensity of the light
     * @param position  position point
     * @param direction spot direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * setkC function
     * @param kC
     */
    @Override
    public SpotLight setkC(double kC) {
        return (SpotLight) super.setkC(kC);

    }

    /**
     * setkL function
     * @param kL
     */
    @Override
    public SpotLight setkL(double kL) {
        return (SpotLight) super.setkL(kL);
    }

    /**
     * setkQ function
     * @param kQ
     */
    @Override
    public SpotLight setkQ(double kQ) {
        return (SpotLight) super.setkQ(kQ);
    }

    /**
     * setNarrowBeam function
     * @param narrow narrow degree
     */
    public SpotLight setNarrowBeam(int narrow) {
        this.narrowBeam = narrow;
        return this;
    }

    /**
     * getIntensity function
     * @param p point
     * @return the intensity color of the point
     */
    @Override
    public Color getIntensity(Point p) {

        double projection = alignZero(direction.dotProduct(getL(p)));
        if (projection <= 0) // if the point is behind the light source
            return Color.BLACK;
        if (narrowBeam != 1)
            projection = alignZero(Math.pow(projection, narrowBeam));
        return super.getIntensity(p).scale(projection);
    }
}
