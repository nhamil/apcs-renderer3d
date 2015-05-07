package nhamilton.game;

import nhamilton.game.graphics.Display;
import nhamilton.game.util.Console;
import nhamilton.game.util.GameLoop;
import nhamilton.game.util.Timer;

/**
 * 
 * Main class for the game, updating and rendering is done here.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Game extends GameLoop
{
    private Timer timer;
    private Display display;
    
    public Game()
    {
        super(-1, 60);
    }
    
    public void init()
    {
        Console.outln("Initializing...", Console.DEBUG);
        
        timer = new Timer();
        display = new Display("Game", 800, 600, 380, 285);
        display.show();
        
        Console.outln("Done!", Console.DEBUG);
    }
    
    public void update()
    {
        timer.tick();
        if(timer.getDelta() >= Timer.SECOND)
        {
            timer.resetTime();
            Console.outln(getData(), Console.STATS);
        }
    }
    
    public void render()
    {
        display.render(this);
    }
}
