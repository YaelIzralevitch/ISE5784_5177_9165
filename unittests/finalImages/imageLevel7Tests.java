package finalImages;

import geometries.*;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Vector;
import renderer.ImageWriter;
import scene.*;
import primitives.*;
import renderer.*;

import static java.awt.Color.*;


public class imageLevel7Tests {
    /**
     * Produce final picture with Bounding Volume Hierarchy
     */
    private Scene scene = new Scene("Test scene");

    @Test
    public void finalProject() {
        Camera.Builder camera = Camera.getBuilder()
                .setRayTracer(new SimpleRayTracer(scene))
                .setLocation(new Point(200, 0, 2500))
                .setDirection(new Vector(-200,-10,-2550), new Vector(0, 255, -1))
                .setVpSize(150, 150).setVpDistance(1000);


        scene.background = new Color(93, 151, 191);
        scene.geometries.add( //
                //snowman
                new Sphere(25, new Point(30, -20, -50)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.1)),
                new Sphere(2.5, new Point(32, 20, -37)) //eyes
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(2.5, new Point(20, 20, -40)) //eyes
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(15, new Point(30, 15, -50)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.1)),
                //nose
                new Triangle(new Point(18, 12, -25), new Point(28, 12, -35), new Point(25, 17, -35)) //
                        .setEmission(new Color(219, 124, 15))
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                //snowman's hands
                new Polygon(new Point(48, -10, -30), new Point(48, -12, -30), new Point(65, 0, -30),
                        new Point(65, 2, -30)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Polygon(new Point(-7, 2, -55), new Point(-7, 0, -55), new Point(10, -12, -55),
                        new Point(10, -10, -55)) //
                        .setEmission(new Color(java.awt.Color.BLACK)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                //buttons
                new Sphere(2.8, new Point(20, -6, -29)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(2.8, new Point(17, -18, -28)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(2.8, new Point(20, -30, -29)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),

                //crystal ball
                new Sphere(80, new Point(0, -10, -50)).setEmission(new Color(java.awt.Color.GRAY)) //
                        .setMaterial(new Material().setkD(0).setkS(1.0).setnShininess(30).setkT(0.5)),

                //plane
                new Polygon(new Point(-400,20,-400), new Point(400, 20, -400), new Point(500,-200,800),
                        new Point(-500,-200,800)) // floor
                        .setEmission(new Color(125, 158, 209)) //
                        .setMaterial(new Material().setkR(0.2)),

                new Polygon(new Point(205.02,158.75,400), new Point(-194.98,158.75,400), new Point(-194.98,158.75,-250),
                        new Point(205.02,158.75,-250)) // roof
                        .setEmission(new Color(146,146,146)) //
                        .setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setnShininess(400)),

                new Polygon(new Point(-194.98,158.75,150), new Point(-194.98,158.75,-250), new Point(-194.98,-241.25,-250),
                        new Point(-194.98,-241.25,150)) //
                        .setEmission(new Color(84,178,26)) //left
                        .setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setnShininess(400)),
/*
                new Polygon(new Point(205.02,158.75,150), new Point(205.02,-241.25,150), new Point(205.02,-241.25,-250),
                        new Point(205.02,158.75,-250)) //
                        .setEmission(new Color(yellow)) //
                        .setMaterial(new Material().setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setnShininess(301)),
*/
                new Polygon(new Point(-194.98,158.75,-250), new Point(-194.98,-241.25,-250), new Point(205.02,-241.25,-250),
                        new Point(205.02,158.75,-250)) //
                        .setEmission(new Color(93, 151, 191)) // front
                        .setMaterial(new Material().setkR(0.2).setkD(new Double3(0.2, 0.6, 0.4)).setkS(new Double3(0.2, 0.4, 0.3)).setnShininess(400)),

                //tree
                new Triangle(new Point(-50, 15, -50), new Point(-32.5, 50, -50), new Point(-15, 23, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-50, 15, -50), new Point(-32.5, 50, -50), new Point(-26.5,18.5,-39)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-50, 15, -50), new Point(-15, 23, -50), new Point(-26.5,18.5,-39)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),

                new Triangle(new Point(-55, 0, -50), new Point(-32.5, 35, -50), new Point(-10, 8, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-55, 0, -50), new Point(-32.5, 35, -50), new Point(-32,-2,-35)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-55, 0, -50), new Point(-10, 8, -50), new Point(-32,-2,-35)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),

                new Triangle(new Point(-60, -15, -50), new Point(-32.5, 22, -50), new Point(-5, -5, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-60, -15, -50), new Point(-32.5, 22, -50), new Point(-22,-11.5,-36)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-60, -15, -50), new Point(-5, -5, -50), new Point(-22,-11.5,-36)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),

                new Triangle(new Point(-65, -30, -50), new Point(-32.5, 7, -50), new Point(0, -20, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-65, -30, -50), new Point(-32.5, 7, -50), new Point(-30,-32,-31)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-65, -30, -50), new Point(0, -20, -50), new Point(-30,-32,-31)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),

                new Triangle(new Point(-70, -45, -50), new Point(-32.5, -8, -50), new Point(5, -35, -50)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-70, -45, -50), new Point(-32.5, -8, -50), new Point(-24.5, -45.5, -25)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),
                new Triangle(new Point(-70, -45, -50), new Point(5, -35, -50), new Point(-24.5, -45.5, -25)) //
                        .setEmission(new Color(11, 82, 31)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100)),

                //snowflakes
                new Sphere(1, new Point(60, 20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(56, 22, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(50, 28, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(0, 29, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(25, 30, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-12, 14, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-16, 16, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-25, 0, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, 22, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, 0, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(8, 9, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(19, 23, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-30, 23, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-32, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-34, 20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-42, 10, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-39, 5, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-47, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-52, 2, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(60, -20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(56, -18, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(50, -12, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(0, -11, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(25, -10, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-12, -26, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-16, -24, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-25, -40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, -18, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, -40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(8, -41, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(19, -17, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-30, -17, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-32, -8, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-34, -20, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-42, -30, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-39, -35, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-47, -1, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-52, -38, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(60, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(56, 37, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(50, 35, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(0, 65, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(25, 48, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-12, 54, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-16, 56, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-25, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, 62, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(5, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(8, 49, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(19, 63, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-30, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-32, 47, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-34, 37, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-42, 39, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-39, 45, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-47, 40, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)),
                new Sphere(1, new Point(-52, 32, -20)) //
                        .setEmission(new Color(155, 182, 224)) //
                        .setMaterial(new Material().setkD(0.4).setkS(0.3).setnShininess(100).setkT(0.3)));


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-161,72,-36), new Vector(161,-82,-14)) //
                        .setkL(0.0004).setkQ(0.0000006).setNarrowBeam(10));
        /*
        scene.lights.add(
                new PointLight(new Color(1000, 600, 0), new Point(114.40793,129.07386,-222.3377))
                        .setkL(0.0004).setkQ(0.000006));


         */

        camera.setImageWriter(new ImageWriter("FinalProject", 500, 500)) //
                .build() //
                .renderImage()
                .writeToImage();
    }
}
