package nhamilton.game;

import static com.tenikkan.abacus.graphics.ab.AB.*;

import com.tenikkan.abacus.graphics.Bitmap;
import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.graphics.model.ArrayModel;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.graphics.model.Vertex;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.math.Vector4f;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;
import com.tenikkan.abacus.util.Heightmap;

public class ArcanaGame extends GameLoop
{
    private Display display;
    private String title = "Arcana: Wrath of the Mad King";
    
    private Bitmap heightmapBitmap;
    
    private Heightmap heightmap;
    
    private Mesh mesh;
    
    @SuppressWarnings("unused")
    private Keyboard keyboard;
    @SuppressWarnings("unused")
    private Mouse mouse;
    
    public static void main(String args[]) 
    {
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
        
        display = new Display(title, 800, 600, 380, 285);
        display.show();
        
        keyboard = display.getKeyboard();
        mouse = display.getMouse();
        
        heightmap = new Heightmap(128, 128, -64f, -64f, 64f, 64f);
        heightmap.generateRandomHeightmap(); 
        
        heightmapBitmap = heightmap.toBitmap(); 
        
        initMesh();
        initABContext();
    }
    
    private void initABContext() 
    {
        abSetContext(display.getScreen());
        
        abClearColor3i(16, 16, 16);
        
        abMatrixMode(AB_PROJECTION);
        abLoadIdentity();
        abPerspective(70, display.getRatio(), 0.1f, 1000f);
        
        abMatrixMode(AB_MODELVIEW);
        abLoadIdentity();
        
        abDisable(AB_CULLING);
        abDisable(AB_COLOR);
        
        abEnable(AB_TEXTURE_MAPPING);
        abEnable(AB_DEPTH_TESTING);
    }
    
    float ticks = 0;
    
    @Override
    public void update()
    {
        ticks += 1;
        display.setTitle(title + " - " + getData());
    }

    @Override
    public void render()
    {
        abClear(AB_FLAG_COLOR_BUFFER | AB_FLAG_DEPTH_BUFFER);
        abLoadIdentity();
        
        abLoadTexture(heightmapBitmap);
        abRotate3f(0, ticks, 0);
        abTranslate3f(0, 0, 3);
        abDrawMesh(mesh);
//        abBegin(AB_QUADS);
//            abTexture2f(0, 0); abVertex2f(100, 0);
//            abTexture2f(0, 1); abVertex2f(100, 600);
//            abTexture2f(1, 1); abVertex2f(700, 600);
//            abTexture2f(1, 0); abVertex2f(700, 0);
//        abEnd();
        
        display.render();
    }
    
    private void initMesh() 
    {
        ArrayModel model = new ArrayModel();
        model.addVertex(new Vertex(new Vector4f(-1,-1, 0, 1), new Vector4f(0, 0, 0, 0), new Vector4f(0, 0, 0, 0)));
        model.addVertex(new Vertex(new Vector4f(-1, 1, 0, 1), new Vector4f(0, 0, 0, 0), new Vector4f(0, 1, 0, 0)));
        model.addVertex(new Vertex(new Vector4f( 1, 1, 0, 1), new Vector4f(0, 0, 0, 0), new Vector4f(1, 1, 0, 0)));
        model.addVertex(new Vertex(new Vector4f( 1,-1, 0, 1), new Vector4f(0, 0, 0, 0), new Vector4f(1, 0, 0, 0)));
        model.addIndex(0);
        model.addIndex(1);
        model.addIndex(2);
        model.addIndex(0);
        model.addIndex(2);
        model.addIndex(3);
        mesh = new Mesh(model);
    }
    
}
