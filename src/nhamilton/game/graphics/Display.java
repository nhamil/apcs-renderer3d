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
        ////////////////////////////
        
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
}
