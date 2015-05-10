/**
 * 
 */
package nhamilton.game.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Nicholas Hamilton
 *
 */
public class Mouse extends MouseAdapter
{   
    private boolean buttons[];
    
    private int gX, gY;
    
    public Mouse() 
    {
        buttons = new boolean[5];
        gX = 0;
        gY = 0;
    }
    
    public void move(int dx, int dy) 
    {
        gX += dx;
        gY += dy;
    }
    
    public int getGlobalX() { return gX; }
    public int getGlobalY() { return gY; }
    
    public boolean isButtonDown(int code) 
    {
        return code < 0 ? false : code >= 5 ? false : buttons[code];
    }
    
    public void mouseMoved(MouseEvent e) 
    {
        gX = e.getXOnScreen();
        gY = e.getYOnScreen();
    }
    
    public void mousePressed(MouseEvent e) 
    {
        int code = e.getButton();
        if(code < 0 || code >= 5) return;
        buttons[code] = true;
    }
    
    public void mouseReleased(MouseEvent e) 
    {
        int code = e.getButton();
        if(code < 0 || code >= 5) return;
        buttons[code] = false;
    }
}
