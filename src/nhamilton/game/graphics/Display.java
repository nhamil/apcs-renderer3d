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
        
        g.dispose();
        bs.show();
    }
}
