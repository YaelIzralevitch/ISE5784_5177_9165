package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Spotlight class
 */
public class SpotLight extends PointLight{

    private Vector direction;

    /**
     * Parameters constructor
     * @param intensity
     * @param position
     * @param direction
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
    public PointLight setkC(double kC) {
        return super.setkC(kC);
    }

    /**
     * setkL function
     * @param kL
     */
    @Override
    public PointLight setkL(double kL) {
        return super.setkC(kL);
    }

    /**
     * setkQ function
     * @param kQ
     */
    @Override
    public PointLight setkQ(double kQ) {
        return super.setkC(kQ);
    }
}
