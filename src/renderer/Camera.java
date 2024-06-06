package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double width = 0.0;
    private double height = 0.0;
    private double distance = 0.0;

    public Point getP0() { return p0; }

    public Vector getvRight() { return vRight; }

    public Vector getvUp() { return vUp; }

    public Vector getvTo() { return vTo; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public double getDistance() { return distance; }

    /**
     * Empty Constructor
     */
    private Camera(){ }


    public static Builder getBuilder(){
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i){
        return null;
    }

    /**
     * Builder class - Implementation of a builder template for a camera class
     */
    public static class Builder {

        final private Camera camera = new Camera();

        /**
         * setLocation function
         * @param p point
         * @return this
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * setDirection function
         * @param vt toward vector
         * @param vu up vector
         * @return this
         */
        public Builder setDirection(Vector vt, Vector vu) {
            if(isZero(vt.dotProduct(vu))){
                throw new IllegalArgumentException("The vectors are not orthogonal");
            }

            camera.vTo = vt.normalize();
            camera.vUp = vu.normalize();

            return this;
        }

        /**
         * setVpSize function
         * @param w width of view plane
         * @param h height of view plane
         * @return this
         */
        public Builder setVpSize(double w, double h) {
            if(isZero(w) || isZero(h) || w < 0 || h < 0){
                throw new IllegalArgumentException("View plane size can't be negative or 0");
            }

            camera.width = w;
            camera.height = h;

            return this;
        }

        /**
         * setVpDistance function
         * @param d distance of view plane from camera
         * @return this
         */
        public Builder setVpDistance(double d) {
            if(isZero(d) || d < 0){
                throw new IllegalArgumentException("Distance length can't be negative or 0");
            }

            camera.distance = d;

            return this;
        }

        /**
         * build function
         * @return an object of Camera
         */
        public Camera build() throws CloneNotSupportedException {
            final String className = "Camera";
            final String s = "Missing rendering data";

            if (camera.width == 0) {
                throw new MissingResourceException(s, className, "width");
            }
            if (camera.height == 0) {
                throw new MissingResourceException(s, className, "height");
            }
            if (camera.distance == 0) {
                throw new MissingResourceException(s, className, "distance");
            }

            if (camera.width <= 0) {
                throw new IllegalArgumentException("width can't be negative");
            }
            if (camera.height <= 0) {
                throw new IllegalArgumentException("height can't be negative");
            }
            if (camera.distance <= 0) {
                throw new IllegalArgumentException("distance can't be negative");
            }

            //calculate vRight
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            return (Camera) camera.clone();
        }
    }

}
