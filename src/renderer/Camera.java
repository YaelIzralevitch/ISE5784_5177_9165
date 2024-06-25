package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.RayTracerBase;

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

    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


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

    /**
     * getBuilder function
     * @return Builder object
     */
    public static Builder getBuilder(){
        return new Builder();
    }

    /**
     * constructRay function
     * @param nX Represents the amount of columns (row width).
     * @param nY Represents the amount of rows (column height)
     * @param j A column of pixels
     * @param i A row of pixels
     * @return A ray from camera to center pixel i,j
     */
    public Ray constructRay(int nX, int nY, int j, int i){

        Point Pc = p0.add(vTo.scale(distance)); //Image center

        //Ratio (pixel width & height)
        double Rx = width / nX;
        double Ry = height / nY;

        Point Pij = Pc; //Pixel[i,j] center
        double Yi = - (i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        // Pixel[i,j] is in the middle column
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }
        //Pixel[i,j] is in the middle row
        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }

        return new Ray(p0, Pij.subtract(p0));
    }

    /**
     * printGrid function
     * @param interval
     * @param color
     */
    public Camera printGrid(int interval, Color color){
        Color pink = new Color(255d, 29d, 190d);
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for(int i = 0; i < nY; i++){
            for(int j = 0; j < nX; j++){
                if(i % interval == 0 || j % interval == 0){
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * writeToImage function - Delegates the writeToImage function of imageWriter
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * renderImage function
     */
    public Camera renderImage(){
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for(int i = 0; i < nY; i++){
            for(int j = 0; j < nX; j++){
                castRay(nX, nY, j, i);
            }
        }
        return this;
    }

    /**
     * castRay function
     * @param nX
     * @param nY
     * @param i
     * @param j
     */
    private void castRay(int nX, int nY, int i, int j){
        Ray ray = constructRay(nX, nY, i, j);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(i, j, color);
    }

    /**
     * Builder class - Implementation of a builder template for a camera class
     */
    public static class Builder {

        private final Camera camera = new Camera();

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
            if(!isZero(vt.dotProduct(vu))){
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
         * setImageWriter function
         * @param imageWriter the image writer to set
         * @return this
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * setRayTracer function
         * @param rayTracer
         * @return this
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * build function
         * @return an object of Camera
         */
        public Camera build() {
            final String className = "Camera";
            final String s = "Missing rendering data";

            if (camera.p0 == null) {
                throw new MissingResourceException(s, className, "p0");
            }

            if (camera.vUp == null) {
                throw new MissingResourceException(s, className, "vUp");
            }

            if (camera.vTo == null) {
                throw new MissingResourceException(s, className, "vTo");
            }

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

            if (camera.imageWriter == null) {
                throw new MissingResourceException(s, className, "imageWriter");
            }

            if (camera.rayTracer == null) {
                throw new MissingResourceException(s, className, "rayTracer");
            }

            //calculate vRight
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            try {
                return (Camera) camera.clone();
            }
            catch (CloneNotSupportedException e) {
                return null;
            }
        }
    }

}
