package nhamilton.game;

import static com.tenikkan.abacus.graphics.ab.AB.*;

import com.tenikkan.abacus.graphics.Bitmap;
import com.tenikkan.abacus.graphics.Camera;
import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.graphics.model.MeshMaker;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Vector4f;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;
import com.tenikkan.abacus.util.Heightmap;

public class ArcanaGame extends GameLoop
{
    private Display display;
    private String title = "Arcana: King of Madness";
    
    private Camera camera;
    
    private Keyboard keyboard;
    private Mouse mouse;
    
    public static void main(String args[]) 
    {
        Console.hide();
        new ArcanaGame().run();
    }
    
    public ArcanaGame()
    {
        super(-1, 60);
    }

    @Override
    public void init()
    {
        Console.show();
        
        display = new Display(title, 800, 600, 400, 300);
        display.show();
        display.hideCursor();
        
        keyboard = display.getKeyboard();
        mouse = display.getMouse();
        mouse.setPosition(display.getX() + display.getFullWidth()/2, display.getY() + display.getFullHeight()/2);
        
        camera = new Camera();
        camera.setPosition(new Vector4f(0, 5, 3, 1));
        
        ht = new Heightmap(32, 32, -100, -100, 100, 100);
        ht.generateRandomHeightmap();
        bmp = ht.toBitmap(0, 1, 0);
        htmapMesh = MeshMaker.generateHeightmapMesh(ht);
        
        initABContext();
    }
    
    private void initABContext() 
    {
        abSetContext(display.getScreen());
        
        abClearColor3i(100, 200, 255);
        
        abMatrixMode(AB_PROJECTION);
        abLoadIdentity();
        abPerspective(70, display.getRatio(), 0.1f, 1000f);
        
        abMatrixMode(AB_MODELVIEW);
        abLoadIdentity();
        
        abEnable(AB_COLOR);
        abDisable(AB_CULLING);
        abDisable(AB_TEXTURE_MAPPING);
        abEnable(AB_DEPTH_TESTING);
    }
    
    @Override
    public void update()
    {
        keyboard.update();
        mouse.update();
        if(display.isFocused())
            mouse.setPosition(display.getX() + display.getFullWidth()/2, display.getY() + display.getFullHeight()/2);
        display.setTitle(title + " - " + getData());
        
        if(keyboard.isKeyDown(Keyboard.ESCAPE)) System.exit(0);
        if(keyboard.isKeyDown(Keyboard.FORWARD)) 
        {
            camera.move(camera.getForwardNoY(), 0.2f);
        }
        if(keyboard.isKeyDown(Keyboard.BACKWARD)) 
        {
            camera.move(camera.getForwardNoY(), -0.2f);
        }
        if(keyboard.isKeyDown(Keyboard.LEFT)) 
        {
            camera.move(camera.getLeft(), 0.2f);
        }
        if(keyboard.isKeyDown(Keyboard.RIGHT)) 
        {
            camera.move(camera.getRight(), 0.2f);
        }
        if(keyboard.isKeyDown(Keyboard.UP)) 
        {
            camera.move(Camera.Y_AXIS, 0.2f);
        }
        if(keyboard.isKeyDown(Keyboard.DOWN)) 
        {
            camera.move(Camera.Y_AXIS, -0.2f);
        }
        
        float rotYAxis = -mouse.getDX() * 0.001f;
        float rotXAxis = -mouse.getDY() * 0.001f;
        
        camera.rotateY(rotYAxis);
        camera.rotateX(rotXAxis);
        
        camera.getPosition().setY(2 + ht.getHeight(camera.getPosition().getX(), camera.getPosition().getZ()));
    }

    Mesh htmapMesh;
    Heightmap ht;
    Bitmap bmp;
    
    @Override
    public void render()
    {
        Matrix4f camMat = new Matrix4f().initRotation(camera.getForward(), camera.getUp());
        camMat = camMat.mul(new Matrix4f().initTranslation(-camera.getPosition().getX(), -camera.getPosition().getY(), -camera.getPosition().getZ()));
        
        abClear(AB_FLAG_COLOR_BUFFER | AB_FLAG_DEPTH_BUFFER);
        
        abEnable(AB_TEXTURE_MAPPING);
        abDisable(AB_COLOR);
        abEnable(AB_CULLING);
        
        abLoadIdentity();
        abLoadTexture(bmp);
        abMatrix4f(camMat);
        abDrawMesh(htmapMesh);
        
        display.render();
    }
    
}
