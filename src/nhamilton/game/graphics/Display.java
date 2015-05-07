/**
 * 
 */
package nhamilton.game.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

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
    
    private BufferedImage screen;
    
    public Display(String title, int width, int height, int sWidth, int sHeight) 
    {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        canvas = new Canvas();
        canvas.setSize(width, height);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        
        screen = new BufferedImage(sWidth, sHeight, BufferedImage.TYPE_INT_RGB);
    }
    
    public int getWidth() { return screen.getWidth(); }
    public int getHeight() { return screen.getHeight(); }
    
    public int getFullWidth() { return canvas.getWidth(); }
    public int getFullHeight() { return canvas.getHeight(); }
    
    public float getUnitWidth() { return getWidth() / ppu; }
    public float getUnitHeight() { return getHeight() / ppu; }
    
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
        
        Graphics gfx = screen.getGraphics();
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getFullWidth(), getFullHeight());
        
        gfx.setColor(Color.BLACK.brighter());
        gfx.fillRect(0, 0, getWidth(), getHeight());
        
        renderUnitGrid(gfx);
        ////////////////////////////
        
        renderEntity(game.getPlayer(), gfx);
        
        ////////////////////////////
        gfx.dispose();
        
        float ratio = (float)getFullWidth()/getFullHeight() - (float)getWidth()/getHeight();
        if(ratio > 0) 
        {
            int width = (int)((float)getFullHeight()/getHeight() * getWidth());
            g.drawImage(screen, getFullWidth()/2 - width/2, 0, width, canvas.getHeight(), canvas);
        } else 
        {
            int height = (int)((float)getFullWidth()/getWidth() * getHeight());
            g.drawImage(screen, 0, getFullHeight()/2 - height/2, canvas.getWidth(), height, canvas);
        }
        
        g.dispose();
        bs.show();
    }
    
    private void renderUnitGrid(Graphics g) 
    {
        g.setColor(new Color(0x221111));
        
        int amt = (int)(getHeight() /2 / ppu);
        for(int i = -amt; i <= amt; i++) 
        {
            g.drawRect(0, getHeight()/2 + (int)(i*ppu), getWidth(), 0);
        }
        
        amt = (int)(getWidth() /2 / ppu);
        for(int i = -amt; i <= amt; i++) 
        {
            g.drawRect(getWidth()/2 + (int)(i*ppu), 0, 0, getHeight());
        }
    }
    
    private void renderEntity(Entity e, Graphics g) 
    {
        int x = getScreenX(e.getPosX() - e.getWidth()/2);
        int y = getScreenY(e.getPosY() + e.getHeight()/2);
        int w = (int)(e.getWidth() * ppu - 1);
        int h = (int)(e.getHeight() * ppu - 1);
        g.setColor(new Color(e.getColorCode()));
        g.drawRect(x, y, w, h);
    }
    
    private int getScreenX(float x) 
    {
        return (int)(getWidth()/2 - (camX - x)*ppu);
    }
    
    private int getScreenY(float y) 
    {
        return (int)(getHeight()/2 + (camX - y)*ppu);
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
