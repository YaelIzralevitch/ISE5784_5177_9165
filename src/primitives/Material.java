package primitives;

/**
 * This class contains functions and calculations on a material
 */
public class Material {
    /**
     * Diffusion component
     */
    public Double3 kD = Double3.ZERO;
    /**
     * Specular component
     */
    public Double3 kS = Double3.ZERO;
    /**
     * Transparency attenuation factor
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Reflection attenuation factor
     */
    public Double3 kR = Double3.ZERO;
    /**
     * Specular shininess degree
     */
    public int nShininess = 0;

    /**
     * setkD function
     *
     * @param kD - Double3 type
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setkD function
     *
     * @param kD - double type
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * setkS function
     *
     * @param kS - Double3 type
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setkS function
     *
     * @param kS - double type
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * setkT function
     *
     * @param kT - Double3 type
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setkT function
     *
     * @param kT - double type
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setkR function
     *
     * @param kR - Double3 type
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setkR function
     *
     * @param kR - double type
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * setnShininess function
     *
     * @param nShininess
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
