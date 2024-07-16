package renderer;

import primitives.Color;
import primitives.Ray;

/**
 * Functionality union class for Adaptive SuperSampling
 */
public class AdaptiveSuperSampling {

    /**
     * Is adaptive super sampling
     */
    private boolean isASS = false;

    /**
     * The depth of adaptive super sampling's recursion
     */
    private int depth = 2;

    public AdaptiveSuperSampling(boolean isASS) {
        this.isASS = isASS;
    }

    public AdaptiveSuperSampling(boolean isASS, int depth) {
        this.isASS = isASS;
        this.depth = depth;
    }

    public boolean isASS() {
        return isASS;
    }

    public int getDepth() {
        return depth;
    }

    public void setASS(boolean ASS) {
        isASS = ASS;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}
