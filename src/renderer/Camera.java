package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Random;

import renderer.RayTracerBase;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

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

    // miniP 1 and 2
    private SampleRays sampleRays = new SampleRays();
    private AdaptiveSuperSampling adaptiveSS = new AdaptiveSuperSampling();

    //multi threading fields
    private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private double printInterval = 0; // printing progress percentage interval

    /** random variable used for ray creation **/
    private final Random rand = new Random();

    public Point getP0() {
        return p0;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * Empty Constructor
     */
    private Camera() { }

    /**
     * getBuilder function
     *
     * @return Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * constructRay function
     *
     * @param nX Represents the amount of columns (row width).
     * @param nY Represents the amount of rows (column height)
     * @param j  A column of pixels
     * @param i  A row of pixels
     * @return A ray from camera to center pixel i,j
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        Point Pc = p0.add(vTo.scale(distance)); //Image center

        //Ratio (pixel width & height)
        double Rx = width / nX;
        double Ry = height / nY;

        Point Pij = Pc; //Pixel[i,j] center
        double Yi = -(i - (nY - 1) / 2d) * Ry;
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
     *
     * @param interval
     * @param color
     */
    public Camera printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
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
     * renderImage function - Sends rays to all pixels in the view plane checks what color each pixel is and colors it
     */
    public Camera renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        Pixel.initialize(nY, nX, printInterval);

        if (threadsCount == 0)
            for (int i = 0; i < nY; i++)
                for (int j = 0; j < nX; j++)
                    castRay(nX, nY, j, i);

        else if (threadsCount == -1) {
            IntStream.range(0, nY).parallel() //
                    .forEach(i -> IntStream.range(0, nX).parallel() //
                            .forEach(j -> castRay(nX, nY, j, i)));
        }

        return this;
    }


    /**
     * castRay function - Paints a single pixel
     *
     * @param nX Image resolution
     * @param nY Image resolution
     * @param i  The pixel index
     * @param j  The pixel index
     */
    private void castRay(int nX, int nY, int i, int j) {
        Ray centerRay = constructRay(nX, nY, i, j);
        Color color;
        //Anti aliasing improvement
        if (sampleRays.isAntiAliasing()) {
            Point pc = centerRay.getPoint(this.distance); //pixel center
            double pixelHeight = alignZero(this.height / nY);
            double pixelWidth = alignZero(this.width / nX);

            if (!adaptiveSS.isASS()) {
                Ray[][] rays = constructRaysGrid(pixelWidth, pixelHeight, pc);
                color = constructRays(rays);
            } else //Improving Performance with adaptive super sampling
            {
                int _N = sampleRays.getN();
                int _M = sampleRays.getM();
                // The color sample of the four corners of the pixel
                Color lu = rayTracer.traceRay(constructRay(0, 0, pixelHeight, pixelWidth, pc));
                Color ld = rayTracer.traceRay(constructRay(_N - 1, 0, pixelHeight, pixelWidth, pc));
                Color ru = rayTracer.traceRay(constructRay(0, _M - 1, pixelHeight, pixelWidth, pc));
                Color rd = rayTracer.traceRay(constructRay(_N - 1, _M - 1, pixelHeight, pixelWidth, pc));

                if (lu.equals(ld) && lu.equals(ru) && lu.equals(rd))
                    color = lu;
                else
                    color = adaptiveSSRecursive(pixelWidth, pixelHeight, pc, lu, ld, ru, rd, 0, 0, _N - 1, _M - 1, adaptiveSS.getDepth());
            }
        } else {
            color = rayTracer.traceRay(centerRay);
        }
        imageWriter.writePixel(i, j, color);
        Pixel.pixelDone();
    }

    /**
     * The function sends a sample of rays through a single pixel and returns the color of the pixel
     * by averaging the colors of all the rays.
     *
     * @param rays grid of rays
     */
    public Color constructRays(Ray[][] rays) {
        List<Color> colors = new LinkedList<>();
        //Finding the colors of each ray
        for (Ray[] rayC : rays) {
            for (Ray rayR : rayC) {
                colors.add(rayTracer.traceRay(rayR));
            }
        }
        return sampleRays.average_colors(colors);

    }

    /**
     * This function get a ray launched in the center of a pixel and launch a beam n * m others rays
     * on the same pixel
     *
     * @param pixelWidth  pixel width
     * @param pixelHeight pixel height
     * @param pixelCenter the center of the pixel Point
     * @return list of rays when every ray is launched inside a pixel with random emplacement
     */
    public Ray[][] constructRaysGrid(double pixelWidth, double pixelHeight, Point pixelCenter) {

        int N = sampleRays.getN();
        int M = sampleRays.getM();
        Ray[][] rays = new Ray[N][M]; //grid for rays

        //We call the function constructRay but this time we launch m * n ray in the same pixel
        for (int c = 0; c < N; c++) {
            for (int r = 0; r < M; r++) {
                rays[c][r] = constructRay(r, c, pixelHeight, pixelWidth, pixelCenter);
            }
        }
        return rays;
    }

    /**
     * the function helps castRay to get the color with super sampling
     *
     * @param lu    the left up point
     * @param ld    the left down point
     * @param ru    the right up point
     * @param rd    the right down point
     * @param x1lu  index x of left up
     * @param y1lu  index y of left up
     * @param x2rd  index x of right down
     * @param y2rd  index y of right down
     * @param depth the deep of the recursion
     * @return the color in the pixel
     */
    public Color adaptiveSSRecursive(double pixelWidth, double pixelHeight, Point pc, Color lu, Color ld, Color ru, Color rd, int x1lu, int y1lu, int x2rd, int y2rd, int depth) {
        if (depth == 0)
            return lu;

        Color col = Color.BLACK;
        int middleX = (x1lu + x2rd) / 2;
        int middleY = (y1lu + y2rd) / 2;

        // Five more points for dividing the square into four squares
        Color mu = rayTracer.traceRay(constructRay(x1lu, middleY, pixelHeight, pixelWidth, pc));
        Color md = rayTracer.traceRay(constructRay(x2rd, middleY, pixelHeight, pixelWidth, pc));
        Color mm = rayTracer.traceRay(constructRay(middleX, middleY, pixelHeight, pixelWidth, pc));
        Color lm = rayTracer.traceRay(constructRay(middleX, y1lu, pixelHeight, pixelWidth, pc));
        Color rm = rayTracer.traceRay(constructRay(middleX, y2rd, pixelHeight, pixelWidth, pc));

        // If all the corners of any sub-square are the same color - add the color to calculate the average colors
        // Otherwise, a recursive call on the sub-square
        if (lu.equals(mu) && lu.equals(mm) && lu.equals(lm))
            col = col.add(lu);
        else
            col = col.add(adaptiveSSRecursive(pixelWidth, pixelHeight, pc, lu, lm, mu, mm, x1lu, y1lu, middleX, middleY, depth - 1));
        if (mu.equals(ru) && mu.equals(mm) && mu.equals(rm))
            col = col.add(mu);
        else
            col = col.add(adaptiveSSRecursive(pixelWidth, pixelHeight, pc, mu, mm, ru, rm, x1lu, middleY, middleX, y2rd, depth - 1));
        if (lm.equals(mm) && lm.equals(ld) && lm.equals(md))
            col = col.add(lm);
        else
            col = col.add(adaptiveSSRecursive(pixelWidth, pixelHeight, pc, lm, ld, mm, md, middleX, y1lu, x2rd, middleY, depth - 1));
        if (mm.equals(rm) && mm.equals(md) && mm.equals(rd))
            col = col.add(mm);
        else
            col = col.add(adaptiveSSRecursive(pixelWidth, pixelHeight, pc, mm, md, rm, rd, middleX, middleY, x2rd, y2rd, depth - 1));

        // Calculating the average of the colors
        return col.reduce(4);
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
    private Ray constructRay(double r, double c, double pixelH, double pixelW, Point pc) {
        Point Pij = pc;
        int N = sampleRays.getN();
        int M = sampleRays.getM();
        //pixelH = height / nY
        double rY = pixelH / N;
        //pixelW = weight / nX
        double rX = pixelW / M;

        double xR = ((r + rand.nextDouble() / (rand.nextBoolean() ? 2 : -2)) - ((M - 1) / 2d)) * rX;
        double yC = -((c + rand.nextDouble() / (rand.nextBoolean() ? 2 : -2)) - ((N - 1) / 2d)) * rY;

        if (xR != 0) {
            Pij = Pij.add(vRight.scale(xR));
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
     * Builder class - Implementation of a builder template for a camera class
     */
    public static class Builder {

        private final Camera camera = new Camera();

        /**
         * setLocation function
         *
         * @param p point
         * @return this
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * setDirection function
         *
         * @param vt toward vector
         * @param vu up vector
         * @return this
         */
        public Builder setDirection(Vector vt, Vector vu) {
            if (!isZero(vt.dotProduct(vu))) {
                throw new IllegalArgumentException("The vectors are not orthogonal");
            }

            camera.vTo = vt.normalize();
            camera.vUp = vu.normalize();

            return this;
        }

        /**
         * setVpSize function
         *
         * @param w width of view plane
         * @param h height of view plane
         * @return this
         */
        public Builder setVpSize(double w, double h) {
            if (isZero(w) || isZero(h) || w < 0 || h < 0) {
                throw new IllegalArgumentException("View plane size can't be negative or 0");
            }

            camera.width = w;
            camera.height = h;

            return this;
        }

        /**
         * setVpDistance function
         *
         * @param d distance of view plane from camera
         * @return this
         */
        public Builder setVpDistance(double d) {
            if (isZero(d) || d < 0) {
                throw new IllegalArgumentException("Distance length can't be negative or 0");
            }

            camera.distance = d;

            return this;
        }

        /**
         * setImageWriter function
         *
         * @param imageWriter the image writer to set
         * @return this
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * setSampleRays function
         *
         * @param sampleRays the sample rays to set
         * @return this
         */
        public Builder setSampleRays(SampleRays sampleRays) {
            camera.sampleRays = sampleRays;
            return this;
        }

        /**
         * setAdaptiveSuperSampling function
         *
         * @param adaptiveSuperSampling the adaptive super sampling to set
         * @return this
         */
        public Builder setAdaptiveSuperSampling(AdaptiveSuperSampling adaptiveSuperSampling) {
            camera.adaptiveSS = adaptiveSuperSampling;
            return this;
        }

        /**
         * setRayTracer function
         *
         * @param rayTracer
         * @return this
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * build function
         *
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
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }

        //multi threading functions
        public Builder setMultithreading(int threads) {
            if (threads < -2) throw new IllegalArgumentException("Multithreading must be -2 or higher");
            if (threads >= -1) camera.threadsCount = threads;
            else { // == -2
                int cores = Runtime.getRuntime().availableProcessors() - camera.SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            }
            return this;
        }

        public Builder setDebugPrint(double interval) {
            camera.printInterval = interval;
            return this;
        }
    }

}
