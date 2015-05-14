/**
 * 
 */
package com.tenikkan.abacus.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tenikkan.abacus.graphics.Display;

/**
 * @author Nicholas Hamilton
 *
 */
public class Mouse extends MouseAdapter
{   
    private Display display;
    
    private boolean buttons[];
    
    private int dx, dy;
    
    private int oldGX, oldGY;
    private int gX, gY;
    
    public Mouse(Display display) 
    {
        buttons = new boolean[5];
        gX = oldGX = dx = 0;
        gY = oldGY = dy = 0;
        this.display = display;
    }
    
    public void move(int dx, int dy) 
    {
        this.dx = dx;
        this.dy = dy;
        gX += dx;
        gY += dy;
        display.setMousePosition(gX, gY);
    }
    
    public void setPosition(int gx, int gy) 
    {
        display.setMousePosition(gx, gy);
        oldGX = gX;
        oldGY = gY;
        gX = gx;
        gY = gy;
        dx = gX - oldGX;
        dy = gY - oldGY;
    }
    
    public void update() 
    {
        dx = gX - oldGX;
        dy = gY - oldGY;
        oldGX = gX;
        oldGY = gY;
    }
    
    public int getDX() { return dx; }
    public int getDY() { return dy; }
    
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
