/**
 * 
 */
package nhamilton.game.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import nhamilton.game.Game;
import nhamilton.game.entity.Entity;

/**
 * 
 * @author Nicholas Hamilton
 * 
 */
public class Display
{
    private boolean showing = false;
    
    private JFrame frame;
    private Canvas canvas;
    
    private float ppu = 32.0f;
    
    private float camX = 0.0f;
    private float camY = 0.0f;
    
    public Display(String title, int width, int height) 
    {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    public void show() 
    {
        show(true);
    }
    
    public void hide() 
    {
        show(false);
    }
    
    private void show(boolean show) 
    {
        showing = show;
        
        if(show) 
        {
            frame.setVisible(true);
        } else 
        {
            frame.setVisible(false);
        }
    }
    
    public void render(Game game) 
    {
        if(!showing) return;
        
        BufferStrategy bs = canvas.getBufferStrategy();
        if(bs == null) 
        {
            canvas.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ////////////////////////////
        
        renderEntity(game.getPlayer(), g);
        
        renderUnitGrid(g);
        
        ////////////////////////////
        g.dispose();
        bs.show();
    }
    
    private void renderUnitGrid(Graphics g) 
    {
        g.setColor(new Color(0x442222));
        
        int amt = (int)(canvas.getHeight() /2 / ppu);
        for(int i = -amt; i <= amt; i++) 
        {
            g.drawRect(0, canvas.getHeight()/2 + (int)(i*ppu), canvas.getWidth(), 1);
        }
        
        amt = (int)(canvas.getWidth() /2 / ppu);
        for(int i = -amt; i <= amt; i++) 
        {
            g.drawRect(canvas.getWidth()/2 + (int)(i*ppu), 0, 1, canvas.getHeight());
        }
    }
    
    private void renderEntity(Entity e, Graphics g) 
    {
        int x = getScreenX(e.getPosX() - e.getWidth()/2);
        int y = getScreenY(e.getPosY() + e.getHeight()/2);
        int w = (int)(e.getWidth() * ppu);
        int h = (int)(e.getHeight() * ppu);
        g.setColor(new Color(e.getColorCode()));
        g.drawRect(x, y, w, h);
    }
    
    private int getScreenX(float x) 
    {
        return (int)(canvas.getWidth()/2 - (camX - x)*ppu);
    }
    
    private int getScreenY(float y) 
    {
        return (int)(canvas.getHeight()/2 + (camX - y)*ppu);
    }
    
    public float getPixelsPerUnit() 
    {
        return ppu;
    }
    
    public void setPixelsPerUnit(float nppu) 
    {
        ppu = nppu;
    }
}
