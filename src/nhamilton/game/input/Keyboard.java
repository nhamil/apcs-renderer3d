/**
 * 
 */
package nhamilton.game.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Nicholas Hamilton
 *
 */
public class Keyboard extends KeyAdapter
{   
    public static final int FORWARD = KeyEvent.VK_I;
    public static final int BACKWARD = KeyEvent.VK_K;
    public static final int LEFT = KeyEvent.VK_J;
    public static final int RIGHT = KeyEvent.VK_L;
    public static final int UP = KeyEvent.VK_SPACE;
    public static final int DOWN = KeyEvent.VK_SHIFT;
    public static final int ESCAPE = KeyEvent.VK_ESCAPE;
    
    private boolean keys[]; 
    
    public Keyboard() 
    {
        keys = new boolean[256];
    }
    
    public void update() {}
    
    public boolean isKeyDown(int code) 
    {
        return code < 0 ? false : code >= 256 ? false : keys[code];
    }
    
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if(code < 0 || code >= 256) return;
        keys[code] = true;
    }
    
    public void keyReleased(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if(code < 0 || code >= 256) return;
        keys[code] = false;
    }
}
