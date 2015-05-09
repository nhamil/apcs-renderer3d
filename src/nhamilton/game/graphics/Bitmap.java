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
    
    public int getPixel(int x, int y) 
    {
        return pixels[x + y*width];
    }
    
    public void setPixel(int x, int y, int col) 
    {
        pixels[x + y*width] = col;
    }
    
    public void copyPixel(int dX, int dY, int sX, int sY, Bitmap src) 
    {
        pixels[dX + dY*width] = src.pixels[sX + sY*src.width];
    }
    
    public int[] getRaster() { return pixels; }
    
    public void copyToRaster(int[] a) 
    {
        System.arraycopy(a, 0, pixels, 0, a.length);
    }
}
