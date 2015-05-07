/**
 * 
 */
package nhamilton.game.graphics;

import java.util.Arrays;

/**
 * @author Nicholas Hamilton
 *
 */
public class Bitmap
{   
    private int width, height;
    private int pixels[];
    
    public Bitmap(int width, int height) 
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void fill(int col) 
    {
        Arrays.fill(pixels, col);
    }
    
    public void setPixel(int x, int y, int col) 
    {
        pixels[x + y*width] = col;
    }
    
    public void copy(int[] a) 
    {
        a = Arrays.copyOf(pixels, pixels.length);
    }
}
