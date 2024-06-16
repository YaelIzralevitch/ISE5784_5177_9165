package renderer;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for integrations casting ray from camera to geometries
 * @author Shir Perez and Yael Izralevitch
 */
public class IntegrationsCastingRayTests {
    private final Scene          scene  = new Scene("Test scene");

    private final Vector vUp = new Vector(0, -1, 0);
    private final Vector vTo = new Vector(0, 0, -1);

    // Camera builder
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(vUp, vTo)
            .setVpDistance(1)
            .setVpSize(3, 3);

    /**
     * A helper function that calculates the number of intersection points of the camera rays with a geometry
     */
    private void numOfIntersectionFromCamera(Camera camera, Geometry g, int expected ){
        int countIntersection = 0;
        List<Point> listOfIntersection = new LinkedList<>();
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 3; i++){
                listOfIntersection = g.findIntersections(camera.constructRay(3, 3, j, i));
                countIntersection += (listOfIntersection == null) ? 0 : listOfIntersection.size();
            }
        }
        assertEquals(expected, countIntersection, "Wrong number of points"); ;
    }

    /**
     * Test integration camera with sphere
     */
    @Test
    void testSphereIntegration() {
        Camera camera1 = cameraBuilder
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1d).setVpSize(3d, 3d)
                .setImageWriter(new ImageWriter("render test", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();
        Camera camera2 = cameraBuilder
                .setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1d).setVpSize(3d, 3d)
                .setImageWriter(new ImageWriter("render test", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        //TC01: Sphere r=1 (2 intersections)
        Sphere sphere = new Sphere(1d, new Point(0, 0, -3));
        numOfIntersectionFromCamera(camera1, sphere, 2);

        //TC02: Sphere r=2.5 (18 intersections)
        sphere = new Sphere(2.5, new Point(0, 0, -2.5));
        numOfIntersectionFromCamera(camera2, sphere, 18);

        //TC03: Sphere r=2 (10 intersections)
        sphere = new Sphere(2, new Point(0, 0, -2));
        numOfIntersectionFromCamera(camera2, sphere, 10);

        //TC04: Sphere r=4 (9 intersections)
        sphere = new Sphere(4, new Point(0, 0, 1));
        numOfIntersectionFromCamera(camera1, sphere, 9);

        //TC05: Sphere r=0.5 (0 intersections)
        sphere = new Sphere(0.5, new Point(0, 0, 1));
        numOfIntersectionFromCamera(camera1, sphere, 0);
    }

    /**
     * Test integration camera with plane
     */
    @Test
    void testPlaneIntegration() {
        Camera camera = cameraBuilder
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVpDistance(1d).setVpSize(3d, 3d)
                .setImageWriter(new ImageWriter("render test", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        //TC01: The plane parallel to the View Plane (9 intersections)
        Plane plane = new Plane(new Point(0, 0, 5),
                                new Vector(0, 0, 1));
        numOfIntersectionFromCamera(camera, plane, 9);

        //TC02: Diagonal plane to the View Plane (9 intersections)
        plane = new Plane(new Point(0, 0, 5),
                          new Vector(0, -1, 2));
        numOfIntersectionFromCamera(camera, plane, 9);

        //TC03: Diagonal plane with an obtuse angle to the View Plane (6 intersections)
        plane = new Plane(new Point(0, 0, 2),
                          new Vector(1, 1, 1));
        numOfIntersectionFromCamera(camera, plane, 6);

        // TC04:The plane behind the view plane (0 intersections)
        plane = new Plane(new Point(0, 0, -4),
                          new Vector(0, 0, 1));
        numOfIntersectionFromCamera(camera, plane, 0);

    }


    /**
     * Test integration camera with triangle
     */
    @Test
    void testTriangleIntegration() {
        Camera camera = cameraBuilder
                .setLocation(Point.ZERO)
                .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVpDistance(1d).setVpSize(3d, 3d)
                .setImageWriter(new ImageWriter("render test", 1000, 1000))
                .setRayTracer(new SimpleRayTracer(scene))
                .build();

        //TC01: Small triangle (1 intersection)
        Triangle triangle = new Triangle(new Point(1, -1, -2),
                                         new Point(-1, -1, -2),
                                         new Point(0, 1, -2));
        numOfIntersectionFromCamera(camera, triangle, 1);

        //TC02: Big triangle (2 intersection)
        triangle = new Triangle(new Point(1, -1, -2),
                                new Point(-1, -1, -2),
                                new Point(0, 20, -2));
        numOfIntersectionFromCamera(camera, triangle, 2);
    }

}