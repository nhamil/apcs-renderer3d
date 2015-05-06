package nhamilton.game;

import nhamilton.game.entity.Player;
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
    private int ticks = 0;
    
    private long timer;
    
    private Display display;
    
    private Player player;
    
    /**
     * Constructs class, sets frame rate to maximum and updates to 60Hz.
     */
    public Game()
    {
        super(-1, 60);
        
        display = new Display("Game", 800, 600);
        display.show();
        
        display.setPixelsPerUnit(16);
    }
    
    public void init()
    {
        timer = System.currentTimeMillis();
        
        player = new Player(0.0f, 5.0f);
    }
    
    public void update()
    {
        ticks++;
        
        if(System.currentTimeMillis() - timer >= 1000)
        {
            timer = System.currentTimeMillis();
            System.out.println(getData());
        }
        
        player.changeVelY(-9.81f / 60f / 60f);
        
        player.update();
        
//        if(player.getPosY() <= -5) 
//        {
//            System.out.println("Ticks: " + ticks + ", Seconds: " + (ticks / 60f));
//            System.exit(0);
//        }
    }
    
    public void render()
    {
        display.render(this);
    }
    
    public Player getPlayer() { return player; }
}
