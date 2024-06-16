package scene;
import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class    Scene {

    public String name;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    /**
     * Parameter Constructor
     * @param name - name of scene
     */
    public Scene(String name){
        this.name = name;
    }

    /**
     * Setter function - builder design pattern
     * @param background
     * @return this
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Setter function - builder design pattern
     * @param ambientLight
     * @return this
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Setter function - builder design pattern
     * @param geometries
     * @return this
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
