package com.tenikkan.abacus.graphics.ab;

import com.tenikkan.abacus.math.Vector4f;

public class ABUtil
{
    public static int getCoordX(float x) 
    {
        return (int)Math.floor(x + 0.5f);
    }
    
    public static int getCoordY(float y) 
    {
        return (int)Math.floor(y + 0.5f);
    }
    
    public static int getColor(Vector4f col) 
    {
        int a = clampi(0, 255, (int)(col.getW() * 255));
        int r = clampi(0, 255, (int)(col.getX() * 255));
        int g = clampi(0, 255, (int)(col.getY() * 255));
        int b = clampi(0, 255, (int)(col.getZ() * 255));
        
        return a<<24|r<<16|g<<8|b;
    }
    
    public static int clampi(int min, int max, int val) 
    {
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }
    
    public static float clampf(float min, float max, float val) 
    {
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }
    
    public static boolean inBounds(Vector4f pos) 
    {
        return pos.getX() <= 1 && pos.getX() >= -1 && 
               pos.getY() <= 1 && pos.getY() >= -1 && 
               pos.getZ() <= 1 && pos.getZ() >= -1; 
    }
}
