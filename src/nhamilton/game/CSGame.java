package nhamilton.game;

import static com.tenikkan.abacus.graphics.AB.*;

import com.tenikkan.abacus.graphics.Bitmap;
import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;
import com.tenikkan.abacus.util.Heightmap;

public class CSGame extends GameLoop
{
    private Display display;
    private String title = "CS Game";
    
    private Bitmap heightmapBitmap;
    
    private Heightmap heightmap;
    
    @SuppressWarnings("unused")
    private Keyboard keyboard;
    @SuppressWarnings("unused")
    private Mouse mouse;
    
    public static void main(String args[]) 
    {
        new CSGame().run();
    }
    
    public CSGame()
    {
        super(-1, 60);
    }

    @Override
    public void init()
    {
        Console.show();
        
        display = new Display(title, 800, 600, 800, 600);
        display.show();
        
        keyboard = display.getKeyboard();
        mouse = display.getMouse();
        
        heightmap = new Heightmap(128, 128, -64f, -64f, 64f, 64f);
        heightmap.generateRandomHeightmap(); 
        
        heightmapBitmap = heightmap.toBitmap(); 
        
        initABContext();
    }
    
    private void initABContext() 
    {
        abSetContext(display.getScreen());
        
        abClearColor3i(16, 16, 16);
        
        abMatrixMode(AB_PROJECTION);
        abLoadIdentity();
        abOrtho(0, 800, 600, 0, -1, 1);
        
        abMatrixMode(AB_MODELVIEW);
        abLoadIdentity();
        
        abDisable(AB_CULLING);
        abDisable(AB_COLOR);
        abEnable(AB_TEXTURE_MAPPING);
        abEnable(AB_DEPTH_TESTING);
    }
    
    @Override
    public void update()
    {
        display.setTitle(title + " - " + getData());
    }

    @Override
    public void render()
    {
        abClear(AB_FLAG_COLOR_BUFFER | AB_FLAG_DEPTH_BUFFER);
        abLoadIdentity();
        
        abLoadTexture(heightmapBitmap);
        abBegin(AB_QUADS);
            abTexture2f(0, 0); abVertex2f(100, 0);
            abTexture2f(0, 1); abVertex2f(100, 600);
            abTexture2f(1, 1); abVertex2f(700, 600);
            abTexture2f(1, 0); abVertex2f(700, 0);
        abEnd();
        
        display.render();
    }
    
}
