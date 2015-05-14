/**
 * 
 */
package com.tenikkan.abacus.util;

import com.tenikkan.abacus.graphics.Bitmap;

/**
 * @author Nicholas Hamilton
 *
 */
public class Heightmap
{   
    private float map[];
    private int width, length;
    private float startX, startZ, endX, endZ;
    private float rangeX, rangeZ;
    
    public Heightmap(int pointWidth, int pointLength, float startX, float startZ, float endX, float endZ) 
    {
        width = pointWidth;
        length = pointLength;
        this.startX = startX;
        this.startZ = startZ;
        this.endX = endZ;
        this.endZ = endZ;
        
        rangeX = endX - startX;
        rangeZ = endZ - startZ;
        
        map = new float[width * length];
    }
    
    public float getStartX() { return startX; }
    public float getStartZ() { return startZ; }
    public float getEndX() { return endX; }
    public float getEndZ() { return endZ; }
    
    public int getPointsWide() { return width; }
    public int getPointsLong() { return length; }
    
    public float getHeightPoint(int x, int z) { return map[x + z*width]; }
    public void setHeightPoint(int x, int z, float height) { map[x + z * width] = height; }
    
    public float getHeight(float xPos, float zPos) 
    {
//        if(xPos < startX || xPos > endX || zPos < startZ || zPos > endZ) return 0;
//        
//        float x[] = new float[3];
//        float h[] = new float[3];
//        float z[] = new float[3];
        
        float xIndex = (xPos - startX) / rangeX * width - 0;
        float zIndex = (zPos - startZ) / rangeZ * length - 0;
        
        if(xIndex < 0 || xIndex >= width - 1 || zIndex < 0 || zIndex >= length - 1) return 0;
        
        int x1 = (int)Math.floor(xIndex);
        int z1 = (int)Math.floor(zIndex);
        int x2 = (int)Math.floor(xIndex + 1);
        int z2 = (int)Math.floor(zIndex + 1);
        
        float xAmt = xIndex - x1;
        float zAmt = zIndex - z1;
        
        System.out.print(xPos + " " + zPos + " ");
        System.out.print(xIndex + " " + zIndex + " ");
        System.out.println(xAmt + " " + zAmt);
        
        float i1 = lerp(getHeightPoint(x1, z1), getHeightPoint(x2, z1), xAmt);
        float i2 = lerp(getHeightPoint(x1, z2), getHeightPoint(x2, z2), xAmt);
        
        return lerp(i1, i2, zAmt);
        
//        find x and z
//        
//        float det = (z[1] - z[2])*(x[0] - x[2]) - (x[1] - x[2])*(z[0] - z[2]);
//        float g1 = (z[1] - z[2])*(xPos - x[2]) - (x[1] - x[2])*(zPos - z[2]) / det;
//        float g2 = (z[2] - z[0])*(xPos - x[2]) - (x[2] - x[0])*(zPos - z[2]) / det;
//        float g3 = 1 - g1 - g2;
//        
//        return g1*h[0] + g2*h[1] + g3*h[2];
    }
    
    private float lerp(float a, float b, float x) 
    {
        return (1-x)*a + x*b;
    }
    
    public float getHighestPoint() 
    {
        float highest = map[0];
        for(int i = 0; i < map.length; i++) 
            if(map[i] > highest) highest = map[i];
        return highest;
    }
    
    public float getLowestPoint() 
    {
        float lowest = map[0];
        for(int i = 0; i < map.length; i++) 
            if(map[i] < lowest) lowest = map[i];
        return lowest;
    }
    
    public Bitmap toBitmap(int r, int g, int b) 
    {
        Bitmap bmp = new Bitmap(width, length);
        float highest = getHighestPoint();
        float lowest = getLowestPoint();
        float range = highest - lowest;
        int[] p = bmp.getRaster(); 
        for(int i = 0; i < p.length; i++) 
        {
            int col = (int)((map[i] - lowest) / range * 255) & 255;
            p[i] = col*r<<16|col*g<<8|col*b;
        }
        return bmp;
    }
    
    public void generateRandomHeightmap()
    {
        for(int i = 0; i < map.length; i++)
            map[i] = 0.0f;
            
        Random r = new Random();
        
        for(int z = 0; z < length; z++) 
        {
            for(int x = 0; x < width; x++) 
            {
                float p = 0.5f;
                
                for(int i = 0; i < 5; i++) 
                {
                    float freq = (float)Math.pow(2, i);
                    float amp = (float)Math.pow(p, i);
                    
                    map[x + z*width] += 8 * r.getNoise(x/16f * freq, z/16f * freq) * amp;
                }
            }
        }
    }
    
    private class Random 
    {
        private int r[];
        
        private final int max = 256;
        
        public Random() 
        {
            r = new int[max*2];
            for(int i = 0; i < max; i++) 
            {
                r[i] = r[max + i] = (int)(Math.random()*max);
            }
        }
        
        private int get(int i) 
        {
            return r[i % max + max];
        }
        
        private float get(int x, int y) 
        {
            return get(get(x) + y) / (float)max * 2 - 1;
        }
        
        public float getNoise(float x, float y) 
        {
            int ix = (int)x;
            float fx = x - ix;
            int iy = (int)y;
            float fy = y - iy;
            
            float n1 = get(ix, iy);
            float n2 = get(ix + 1, iy);
            float n3 = get(ix, iy + 1);
            float n4 = get(ix + 1, iy + 1);
            
            float i1 = lerp(n1, n2, fx);
            float i2 = lerp(n3, n4, fx);
            
            return lerp(i1, i2, fy);
        }
        
        private float lerp(float a, float b, float x) 
        {
            return (1-x)*a + b*x;
        }
    }
}
