package nhamilton.game.level;

import java.util.ArrayList;

public class TileManager
{
    private static TileManager instance;
    
    private ArrayList<Tile> tiles;
    
    private TileManager() 
    {
        tiles = new ArrayList<Tile>();
    }
    
    public static TileManager getInstance() 
    { 
        if(instance == null) 
            instance = new TileManager();
        
        return instance;
    }
    
    public void addTile(String name, int id, boolean solid, int colorCode) 
    {
        addTile(new Tile(name, id, solid, colorCode));
    }
    
    public void addTile(Tile t) 
    {
        tiles.add(t.getID(), t);
    }
    
    public Tile getTile(int id) 
    {
        if(id < 0 || id >= tiles.size()) return null;
        return tiles.get(id);
    }
    
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
