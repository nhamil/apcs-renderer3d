package nhamilton.game.level;


public class Level
{
    private int[] tileID;
    private int width, height;
    
    public Level(int w, int h) 
    {
        width = w;
        height = h;
        genBlankWorld();
    }
    
    public int getTileID(int x, int y) 
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return -1;
        return tileID[x + y*width];
    }
    
    public Tile getTile(int x, int y) 
    {
        return TileManager.getInstance().getTile(getTileID(x, y));
    }
    
    public void setTile(int x, int y, int id) 
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return;
        tileID[x + y*width] = id;
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void genBlankWorld() 
    {
        tileID = new int[width*height];
        for(int y = 0; y < height; y++) 
            for(int x = 0; x < width; x++) 
            {
                setTile(x, y, 0);
            }
    }
}
