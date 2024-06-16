package xml;

import geometries.Geometry;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import scene.Scene;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class WriteToXml {

    public static void writeSceneToXML(Scene scene, String filePath) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // Root element
        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("scene");
        rootElement.setAttribute("background-color", scene.background.getRgb().toStringToXml());
        doc.appendChild(rootElement);

        // Ambient light element
        Element ambientLight = doc.createElement("ambient-light");
        ambientLight.setAttribute("color", scene.ambientLight.getIntensity().getRgb().toStringToXml());
        rootElement.appendChild(ambientLight);

        // Geometries element
        Element geometries = doc.createElement("geometries");
        rootElement.appendChild(geometries);

        List<Intersectable> geometryList = scene.geometries.getGeometries();
        for (Intersectable geometry : geometryList) {
            if (geometry instanceof Sphere) {
                Sphere sphere = (Sphere) geometry;
                Element sphereElement = doc.createElement("sphere");
                sphereElement.setAttribute("center", sphere.getCenter().getXYZ().toStringToXml());
                sphereElement.setAttribute("radius", Double.toString(sphere.getRadius()));
                geometries.appendChild(sphereElement);
            } else if (geometry instanceof Triangle) {
                Triangle triangle = (Triangle) geometry;
                Element triangleElement = doc.createElement("triangle");
                triangleElement.setAttribute("p0", triangle.getVertices().get(0).getXYZ().toStringToXml());
                triangleElement.setAttribute("p1", triangle.getVertices().get(1).getXYZ().toStringToXml());
                triangleElement.setAttribute("p2", triangle.getVertices().get(2).getXYZ().toStringToXml());
                geometries.appendChild(triangleElement);
            }
        }

        // Write the content into an XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));

        transformer.transform(source, result);
    }
}
