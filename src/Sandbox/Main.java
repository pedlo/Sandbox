package Sandbox;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(loadSkybox());
        rootNode.attachChild(geom);
        System.out.println("Hello GitHub");
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    private Spatial loadSkybox(){
        
        String resolution = new Util().getProperty("config/config.cfg", "SkyboxRes");
        System.out.println("Resolution: " + resolution);
        
        Texture west = assetManager.loadTexture("Textures/Skybox/" + resolution + "/posx.jpg");
        Texture east = assetManager.loadTexture("Textures/Skybox/" + resolution + "/negx.jpg");
        Texture north = assetManager.loadTexture("Textures/Skybox/" + resolution + "/negz.jpg");
        Texture south = assetManager.loadTexture("Textures/Skybox/" + resolution + "/posz.jpg");
        Texture up = assetManager.loadTexture("Textures/Skybox/" + resolution + "/posy.jpg");
        Texture down = assetManager.loadTexture("Textures/Skybox/" + resolution + "/negy.jpg");
        
        return SkyFactory.createSky(assetManager, west, east, north, south, up, down);
    }
}
