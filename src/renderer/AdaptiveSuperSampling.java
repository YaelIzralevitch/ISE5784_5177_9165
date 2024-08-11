package renderer;


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

    /** empty constructor **/
    public AdaptiveSuperSampling() { }

    /** one parameter constructor **/
    public AdaptiveSuperSampling(boolean isASS) {
        this.isASS = isASS;
    }

    /** parameters constructor **/
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
}
