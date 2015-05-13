package nhamilton.game;

import static com.tenikkan.abacus.graphics.AB.*;

import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;

public class CSGame extends GameLoop
{
    private Display display;
    private String title = "CS Game";
    
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
        
        display = new Display(title, 800, 600, 1920, 1020);
        display.show();
        
        keyboard = display.getKeyboard();
        mouse = display.getMouse();
        
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
        abEnable(AB_COLOR);
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
        
        display.render();
    }
    
}
