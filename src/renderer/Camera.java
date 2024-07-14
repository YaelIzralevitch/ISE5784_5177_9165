package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.RayTracerBase;

import java.util.LinkedList;
import java.util.List;
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

    /* isAntiAliasing */
    private boolean isAntiAliasing = false;


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
     * castRay function - Returns the color of a single pixel
     * @param nX Image resolution
     * @param nY Image resolution
     * @param i The pixel index
     * @param j The pixel index
     */
    private void castRay(int nX, int nY, int i, int j){
        Ray centerRay = constructRay(nX, nY, i, j);
        Color color;
        if(isAntiAliasing){
            Point pc =centerRay.getPoint(this.distance); //pixel center
            double pixelHeight = alignZero(this.height / nY);
            double pixelWidth = alignZero(this.width / nX);

            color = constructRays(pixelWidth, pixelHeight, pc, vRight, vUp, p0);
        }
        else {
            color = rayTracer.traceRay(centerRay);
        }
        imageWriter.writePixel(i, j, color);
    }

    private final int N = 10;
    private final int M = 10;

    /**
     * The function sends a sample of rays through a single pixel and returns the color of the pixel
     * by averaging the colors of all the rays.
     * @param pixelWidth pixel width
     * @param pixelHeight pixel height
     */
    public Color constructRays(double pixelWidth, double pixelHeight, Point pixelCenter, Vector vUp, Vector vRight, Point p0){
        List<Color> colors = new LinkedList<>();
        Ray[][] rays = constructRaysGrid(pixelWidth, pixelHeight, pixelCenter, vUp, vRight, p0);
        //Finding the colors of each ray
        for(Ray[] rayC : rays){
            for(Ray rayR : rayC){
                colors.add(rayTracer.traceRay(rayR));
            }
        }
        return average_colors(colors);

    }

    /**
     * This function get a ray launched in the center of a pixel and launch a beam n * m others rays
     * on the same pixel
     * @param pixelWidth pixel width
     * @param pixelHeight pixel height
     * @param pixelCenter the center of the pixel Point
     * @return list of rays when every ray is launched inside a pixel with random emplacement
     */
    public Ray[][] constructRaysGrid(double pixelWidth, double pixelHeight, Point pixelCenter, Vector vUp, Vector vRight, Point p0) {

        Ray[][] rays = new Ray[N][M]; //grid for rays

        //We call the function constructRay but this time we launch m * n ray in the same pixel
        for (int c = 0; c < N; c++) {
            for (int r = 0; r< M; r++) {
                rays[c][r] = constructRay(r, c, pixelHeight, pixelWidth, pixelCenter, vUp, vRight, p0);
            }
        }
        return rays;
    }


    /**
     * The function constructs a ray from Camera location through a point (i,j) on the grid of a
     * pixel in the view plane
     *
     * @param c      pixel grid's height
     * @param r      pixel grid's width
     * @param pixelH height of the pixel
     * @param pixelW width of the pixel
     * @param pc     pixel center
     * @return the ray through pixel's center
     */
    private Ray constructRay(double r, double c, double pixelH, double pixelW, Point pc, Vector vUp, Vector vRight, Point p0) {
        Point Pij = pc;
        //pixelH = height / nY
        double rY = pixelH / N;
        //pixelW = weight / nX
        double rX = pixelW / M;

        double xR = (r - ((M - 1) / 2d)) * rX;
        double yC = -(c - ((N - 1) / 2d)) * rY;

        if (xR != 0) {
            pc = pc.add(vRight.scale(xR));
        }
        if (yC != 0) {
            Pij = Pij.add(vUp.scale(yC));
        }

        //get vector from camera location p0 to the point
        Vector rayVector = Pij.subtract(p0);

        //return ray to the center of the pixel
        return new Ray(p0, rayVector);

    }

    /**
     * Calculates the average of all the colors in the list
     * @param colors list of Color
     */
    private Color average_colors(List<Color> colors){
        Color[] colorsArray = colors.toArray(new Color[0]);
        Color initColor = Color.BLACK;
        Color colorsSum = initColor.add(colorsArray);
        return colorsSum.reduce(colors.size());
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
         * setIsAntiAliasing function - Do the improvement Anti-aliasing or not
         * @param isAntiAliasing
         * @return this
         */
        public Builder setIsAntiAliasing(boolean isAntiAliasing) {
            camera.isAntiAliasing = isAntiAliasing;
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
