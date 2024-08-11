package renderer;

import primitives.Color;

import java.util.List;

/**
 * Functionality union class for sending rays sample
 */
public class SampleRays {

    private boolean isAntiAliasing = false;
    //grid size
    private int N = 9;
    private int M = 9;

    /** empty constructor **/
    public SampleRays() {}

    /** one parameter constructor **/
    public SampleRays(boolean isAA){
        isAntiAliasing = isAA;
    }

    /** parameters constructor **/
    public SampleRays(boolean isAA, int n, int m){
        isAntiAliasing = isAA;
        N = n;
        M = m;
    }

    public boolean isAntiAliasing() {
        return isAntiAliasing;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    /**
     * Calculates the average of all the colors in the list
     * @param colors list of Color
     */
    public Color average_colors(List<Color> colors){
        Color[] colorsArray = colors.toArray(new Color[0]);
        Color initColor = Color.BLACK;
        Color colorsSum = initColor.add(colorsArray);
        return colorsSum.reduce(colors.size());
    }
}