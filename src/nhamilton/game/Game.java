package nhamilton.game;

import nhamilton.game.graphics.Display;

/**
 * 
 * Main class for the game, updating and rendering is done here.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Game extends GameLoop
{
    private long timer;
    
    private Display display;
    
    /**
     * Constructs class, sets frame rate to maximum and updates to 60Hz.
     */
    public Game()
    {
        super(-1, 60);
        
        display = new Display("Game", 800, 600);
        display.show();
    }
    
    public void init()
    {
        timer = System.currentTimeMillis();
    }
    
    public void update()
    {
        if(System.currentTimeMillis() - timer >= 1000)
        {
            timer = System.currentTimeMillis();
            System.out.println(getData());
        }
    }
    
    public void render()
    {
        display.render(this);
    }
}
