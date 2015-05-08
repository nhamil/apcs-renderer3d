package nhamilton.game;

import nhamilton.game.graphics.Display;
import nhamilton.game.graphics.Bitmap;
import nhamilton.game.graphics.Renderer;
import nhamilton.game.math.Vector4f;
import nhamilton.game.util.Console;
import nhamilton.game.util.GameLoop;
import nhamilton.game.util.Timer;
import nhamilton.game.util.Vertex;

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
    
    private Bitmap bmp;
    
    public Game()
    {
        super(-1, 60);
    }
    
    public void init()
    {
        Console.outln("Initializing...", Console.DEBUG);
        
        timer = new Timer();
        display = new Display("Game", 800, 600, 320, 320);//380, 285);
        display.show();
        
        Console.outln("Done!", Console.DEBUG);
        
        bmp = new Bitmap(64, 64);
        int px[] = new int[64*64];
        for(int i = 0; i < 64*64; i++) 
        {
            px[i] = (int)(Math.random()*0x1000000);
        }
        bmp.copyToRaster(px);
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
        Renderer render = display.getScreen();
        
        render.fill(0x111111);
        
        float t = 5;
        float ticks = timer.getTicks()/t/5;
        
        float amt = .8f;
        
        render.setTexture(bmp);
        
        for(int i = 0; i < 1; i++){
            amt += .005f;
            ticks += 0.02f;;
        render.drawTriangle(new Vertex(new Vector4f(amt*(float)Math.cos(ticks + .33f * 6.28f), 
                                                    amt*(float)Math.sin(ticks + .33f * 6.28f), 
                                                    0, 1), new Vector4f(1f, 0f, 0f, 0f), 0f, 0f),
                            new Vertex(new Vector4f(amt*(float)Math.cos(ticks), 
                                                    amt*(float)Math.sin(ticks), 
                                                    0, 1), new Vector4f(0f, 1f, 0f, 0f), 0.5f, 1f),
                            new Vertex(new Vector4f(amt*(float)Math.cos(ticks - .33f * 6.28f), 
                                                    amt*(float)Math.sin(ticks - .33f * 6.28f), 
                                                    0, 1), new Vector4f(0f, 0f, 1f, 0f), 1f, 0f));
        }
        render.drawTriangle(new Vertex(new Vector4f(-.5f, .5f, 0, 1), new Vector4f(1f, 0f, 0f, 0f), 0f, 1f),
                new Vertex(new Vector4f(-.5f, -.5f, 0, 1), new Vector4f(0f, 1f, 0f, 0f), 0f, 0f),
                new Vertex(new Vector4f(.5f, -.5f, 0, 1), new Vector4f(0f, 0f, 1f, 0f), 1f, 0f));
        
        render.drawTriangle(new Vertex(new Vector4f(-.5f, .5f, 0, 1), new Vector4f(1f, 0f, 0f, 0f), 0f, 1f),
                new Vertex(new Vector4f(.5f, .5f, 0, 1), new Vector4f(0f, 1f, 0f, 0f), 1f, 1f),
                new Vertex(new Vector4f(.5f, -.5f, 0, 1), new Vector4f(0f, 0f, 1f, 0f), 1f, 0f));

        display.render();
    }
}
