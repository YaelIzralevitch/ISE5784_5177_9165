package xml;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import scene.Scene;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadFromXml {

    /*
    public static Scene readSceneFromXML(String filePath) throws Exception {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Create scene object

        Element root = doc.getDocumentElement();
        Color color = Utils.stringToArray(root.getAttribute("background-color"));

        // Read ambient light
        NodeList ambientLightList = doc.getElementsByTagName("ambient-light");
        if (ambientLightList.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLightList.item(0);
            AmbientLight ambientLight = new AmbientLight();
            ambientLight.setColor(Utils.stringToArray(ambientLightElement.getAttribute("color")));
            scene.setAmbientLight(ambientLight);
        }

        // Read geometries
        NodeList geometriesList = doc.getElementsByTagName("geometries");
        if (geometriesList.getLength() > 0) {
            Element geometriesElement = (Element) geometriesList.item(0);
            NodeList geometryNodes = geometriesElement.getChildNodes();
            List<Geometry> geometries = new ArrayList<>();

            for (int i = 0; i < geometryNodes.getLength(); i++) {
                Node node = geometryNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) node;
                    String nodeName = geometryElement.getNodeName();

                    if (nodeName.equals("sphere")) {
                        Sphere sphere = new Sphere();
                        sphere.setCenter(Utils.stringToArray(geometryElement.getAttribute("center")));
                        sphere.setRadius(Integer.parseInt(geometryElement.getAttribute("radius")));
                        geometries.add(sphere);
                    } else if (nodeName.equals("triangle")) {
                        Triangle triangle = new Triangle();
                        triangle.setP0(Utils.stringToArray(geometryElement.getAttribute("p0")));
                        triangle.setP1(Utils.stringToArray(geometryElement.getAttribute("p1")));
                        triangle.setP2(Utils.stringToArray(geometryElement.getAttribute("p2")));
                        geometries.add(triangle);
                    }
                }
            }
            scene.setGeometries(geometries);
        }

        return scene;
    }

     */
}
