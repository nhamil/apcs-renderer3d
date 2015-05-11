package nhamilton.game;

import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.util.GameLoop;

public class CSGame extends GameLoop
{
    private Display display;
    private String title = "CS Game";
    
    public CSGame()
    {
        super(-1, 60);
    }

    @Override
    public void init()
    {
        display = new Display(title, 800, 600, 380, 285);
        display.show();
    }

    @Override
    public void update()
    {
        
    }

    @Override
    public void render()
    {
        display.render();
    }
    
}
