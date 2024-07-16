package renderer;

import primitives.Color;

import java.util.List;

/**
 * Functionality union class for sending rays sample
 */
public class SampleRays {

    private boolean isAntiAliasing = false;
    //grid size
    private int N = 10;
    private int M = 10;

    public SampleRays(boolean isAA){
        isAntiAliasing = isAA;
    }

    public SampleRays(boolean isAA, int n, int m){
        isAntiAliasing = isAA;
        N = n;
        M = m;
    }

    public void setAntiAliasing(boolean antiAliasing) {
        isAntiAliasing = antiAliasing;
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