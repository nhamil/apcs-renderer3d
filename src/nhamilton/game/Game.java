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
        
        display = new Display("Game", 800, 600, 320, 160);
        display.show();
        
        display.setPixelsPerUnit(16);
    }
    
    public void init()
    {
        timer = System.currentTimeMillis();
        
        player = new Player(0.0f, 0.0f, new JumpingController());
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
        
        
        float lowerBound = -display.getUnitHeight()/2;
        if(player.getPosY()-player.getHeight()/2 < lowerBound) 
        {
            player.setOnGround(true);
            player.setPosY(lowerBound+player.getHeight()/2);
            player.setVelY(0);
        }
    }
    
    public void render()
    {
        display.render(this);
    }
    
    public Player getPlayer() { return player; }
}
