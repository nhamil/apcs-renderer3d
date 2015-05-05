package nhamilton.game.level;

import java.util.ArrayList;

/**
 * 
 * Manages all tiles.
 * 
 * @author Nicholas Hamilton
 *
 */

public class TileManager
{
    private static TileManager instance;
    
    private ArrayList<Tile> tiles;
    
    private TileManager()
    {
        tiles = new ArrayList<Tile>();
    }
    
    /**
     * Returns singleton instance of TileManager.
     * 
     * @return Instance of TileManager
     */
    public static TileManager getInstance()
    {
        if(instance == null)
            instance = new TileManager();
        
        return instance;
    }
    
    /**
     * Creates and adds a tile to the tile registry.
     * 
     * @param name Name of the tile
     * @param id ID of the tile
     * @param solid Whether the tile is solid or not
     * @param colorCode Color of the tile in hex
     */
    public void addTile(String name, int id, boolean solid, int colorCode)
    {
        addTile(new Tile(name, id, solid, colorCode));
    }
    
    /**
     * Adds already existing tile to the registry.
     * 
     * @param t Tile to be added
     */
    public void addTile(Tile t)
    {
        tiles.add(t.getID(), t);
    }
    
    /**
     * Returns a tile with the ID 'id'.
     * 
     * @param id ID of the tile
     * @return Tile with ID 'id'
     */
    public Tile getTile(int id)
    {
        if(id < 0 || id >= tiles.size())
            return null;
        return tiles.get(id);
    }
    
    /**
     * Returns a tile by name.
     * 
     * @param name Name of the tile.
     * @return Tile with name 'name'
     */
    public Tile getTile(String name)
    {
        for(Tile t : tiles)
        {
            if(t.getName().equalsIgnoreCase(name))
                return t;
        }
        return null;
    }
}
