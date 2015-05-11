/**
 * 
 */
package com.tenikkan.abacus.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import com.tenikkan.abacus.util.Console;

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
    
    public Bitmap(String fileName)
    {
        try 
        {
            File file = new File(fileName);
            
            BufferedImage img = ImageIO.read(file);
            
            this.width = img.getWidth();
            this.height = img.getHeight();
            pixels = new int[width * height];
            
            for(int y = 0; y < height; y++) 
                for(int x = 0; x < width; x++) 
                {
                    int col = img.getRGB(x, y);
                    pixels[x + y*width] = col & 0xffffff;
                    if((col & 0xff000000) == 0)
                        pixels[x + y*width] = 0x1000000;
                }
        } catch(IOException e) 
        {
            Console.outln("Could not load image \"" + fileName + "\"!", Console.WARNING);
            width = 1;
            height = 1;
            pixels = new int[1];
        }
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
        if(src.pixels[sX + sY*src.width] == 0x1000000) return;
        pixels[dX + dY*width] = src.pixels[sX + sY*src.width];
    }
    
    public boolean isTransparent(int x, int y) 
    {
        return pixels[x + y*width] == 0x1000000;
    }
    
    public int[] getRaster() { return pixels; }
    
    public void copyToRaster(int[] a) 
    {
        System.arraycopy(a, 0, pixels, 0, a.length);
    }
}
