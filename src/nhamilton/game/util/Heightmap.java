/**
 * 
 */
package nhamilton.game.util;

/**
 * @author Nicholas Hamilton
 *
 */
public class Heightmap
{   
    private float map[];
    private int width, length;
    private float startX, startZ, endX, endZ;
    
    public Heightmap(int pointWidth, int pointLength, float startX, float startZ, float endX, float endZ) 
    {
        width = pointWidth;
        length = pointLength;
        this.startX = startX;
        this.startZ = startZ;
        this.endX = endZ;
        this.endZ = endZ;
        
        map = new float[width * length];
    }
    
    public float getStartX() { return startX; }
    public float getStartZ() { return startZ; }
    public float getEndX() { return endX; }
    public float getEndZ() { return endZ; }
    
    public float getPointsWide() { return width; }
    public float getPointsLong() { return length; }
    
    public float getHeightPoint(int x, int z) { return map[x + z*width]; }
    public void setHeightPoint(int x, int z, float height) { map[x + z * width] = height; }

    public void generateRandomHeightmap()
    {
        for(int i = 0; i < map.length; i++)
            map[i] = 0.0f;
            
        Random r = new Random();
        
        for(int z = 0; z < length; z++) 
        {
            for(int x = 0; x < width; x++) 
            {
                float p = 0.4f;
                
                for(int i = 0; i < 10; i++) 
                {
                    float freq = (float)Math.pow(2, i);
                    float amp = (float)Math.pow(p, i);
                    
                    map[x + z*width] += 8 * r.getNoise(x/5f * freq, z/5f * freq) * amp;
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
