package nhamilton.game.level;

/**
 * 
 * Contains a level of tiles for the game.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Level
{
    private int[] tileID;
    private int width, height;
    
    /**
     * Creats the level class with size w x h.
     * 
     * @param w Width of tile map
     * @param h Height of tile map
     */
    public Level(int w, int h)
    {
        width = w;
        height = h;
        genBlankWorld();
    }
    
    /**
     * Returns the ID of the tile at position (x, y).
     * 
     * @param x X position
     * @param y Y position
     * @return ID of tile at (x, y)
     */
    public int getTileID(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return -1;
        return tileID[x + y * width];
    }
    
    /**
     * Returns reference to the type of tile at position (x, y).
     * 
     * @param x X position
     * @param y Y position
     * @return Tile at (x, y)
     */
    public Tile getTile(int x, int y)
    {
        return TileManager.getInstance().getTile(getTileID(x, y));
    }
    
    /**
     * Sets the value at (x, y) to the ID of a tile.
     * 
     * @param x X position
     * @param y Y position
     * @param id ID of tile
     */
    public void setTile(int x, int y, int id)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return;
        tileID[x + y * width] = id;
    }
    
    /**
     * Returns the width of the level.
     * 
     * @return Width of map
     */
    public int getWidth()
    {
        return width;
    }
    
    /**
     * Returns the height of the level.
     * 
     * @return Height of map
     */
    public int getHeight()
    {
        return height;
    }
    
    /**
     * Creates a new tile map, and fills it with ID 0.
     */
    public void genBlankWorld()
    {
        tileID = new int[width * height];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
            {
                setTile(x, y, 0);
            }
    }
}
