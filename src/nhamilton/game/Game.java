package nhamilton.game;

import nhamilton.game.graphics.Bitmap;
import nhamilton.game.graphics.Display;
import nhamilton.game.graphics.Renderer;
import nhamilton.game.graphics.Vertex;
import nhamilton.game.math.Matrix4f;
import nhamilton.game.math.Vector4f;
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
    
    private Bitmap bmp;
    
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
        
        int s = 16;
        bmp = new Bitmap(s, s);
        int px[] = new int[s*s];
        for(int i = 0; i < s*s; i++) 
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
        
        float tick = timer.getTicks() / 0.5f;
        float amt = 0.4f;
        render.setTexture(bmp);
        
        Matrix4f rotate = new Matrix4f().initRotation(0, tick, 0);//tick, tick, tick);
        Matrix4f pos = new Matrix4f().initTranslation(0, 0.2f, 1.5f);
        Matrix4f proj = new Matrix4f().initPerspective(70f, (float)render.getWidth()/render.getHeight(), 0.1f, 1000f);
        
        Matrix4f t = rotate.mul(pos.mul(proj));
        
        Vector4f top = new Vector4f(0, amt, 0.0f, 1.0f);
        Vector4f left = new Vector4f(-amt, -amt, -amt, 1.0f);
        Vector4f right = new Vector4f(amt, -amt, -amt, 1.0f);
        
        Vertex vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        Vertex vLeft = new Vertex(t.mul(left),   new Vector4f(0f, 0f, 0f, 0f));
        Vertex vRight = new Vertex(t.mul(right), new Vector4f(1f, 0f, 0f, 0f));
        
        render.drawTriangle(vTop, vLeft, vRight);

        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(1,1,-1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vLeft, vRight);
        
        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(-1,1,1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vLeft, vRight);
        
        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(1,1,1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(-1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vLeft, vRight);
        
        display.render();
    }
}
