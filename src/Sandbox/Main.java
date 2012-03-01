package Sandbox;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication implements ActionListener {
    
    private BulletAppState bulletAppState;
    private RigidBodyControl landscape;
    private CharacterControl player;
    private boolean left = false, right = false, up = false, down = false;
    private Vector3f walkDirection = new Vector3f();

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        /**Set up physics*/
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        flyCam.setMoveSpeed(10.0f);
        setUpKeys();

        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(loadSkybox());
        Spatial terrainScene = assetManager.loadModel("Scenes/terrainTestScene.j3o");
        
        CollisionShape terrainShape = CollisionShapeFactory.createMeshShape((Node)terrainScene);
        landscape = new RigidBodyControl(terrainShape, 0);
        terrainScene.addControl(landscape);
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        player = new CharacterControl(capsuleShape, 0.05f);
        player.setJumpSpeed(20.0f);
        player.setFallSpeed(30.0f);
        player.setGravity(30.0f);
        player.setPhysicsLocation(new Vector3f(0f, 10f, 0f));
        bulletAppState.getPhysicsSpace().add(terrainScene);
        bulletAppState.getPhysicsSpace().add(player);
   
        rootNode.attachChild(terrainScene);
        rootNode.attachChild(geom);
        System.out.println("Hello GitHub");
    }

    @Override
    public void simpleUpdate(float tpf) {
        Vector3f camDir = cam.getDirection().clone().multLocal(0.6f);
        Vector3f camLeft = cam.getLeft().clone().multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if(left) {walkDirection.addLocal(camLeft);}
        if(right) {walkDirection.addLocal(camLeft.negate());}
        if(up) {walkDirection.addLocal(camDir);}
        if(down) {walkDirection.addLocal(camDir.negate());}
        player.setWalkDirection(walkDirection);
        cam.setLocation(player.getPhysicsLocation());
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
    
    /**
     * Set up end overwrite if necessary the key mappings
     */
    private void setUpKeys(){
        inputManager.addMapping("Left", new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(keyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(keyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(keyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(keyInput.KEY_A));
        inputManager.addMapping("Jump", new KeyTrigger(keyInput.KEY_SPACE));
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(name.equals("Left")){
            if(isPressed) {left = true;} else {left = false;}
        }
        if(name.equals("Right")){
            if(isPressed) {right = true;} else {right = false;}
        }
        if(name.equals("Up")){
            if(isPressed) {up = true;} else {up = false;}
        }
        if(name.equals("Down")){
            if(isPressed) {down = true;} else {down = false;}
        }
        if(name.equals("Jump")){
            player.jump();
        }
    }
}
