package nhamilton.game.level;

public class Tile
{
    static
    {
        TileManager tm = TileManager.getInstance();
        
        tm.addTile("air", 0, false, 0x66bbff);
        tm.addTile("grass", 1, true, 0x11cc33);
        tm.addTile("stone", 2, true, 0x797979);
    }
    
    private String name;
    private int id;
    private boolean solid;
    private int colorCode;
    
    /**
     * Creates a new tile.
     * 
     * @param name Name of tile type
     * @param id ID of the tile.
     * @param solid If the tile is solid.
     * @param colorCode Color of the tile in hex
     */
    public Tile(String name, int id, boolean solid, int colorCode)
    {
        this.name = name;
        this.id = id;
        this.solid = solid;
        this.colorCode = colorCode;
    }
    
    /**
     * Returns name of the tile.
     * 
     * @return Name of the tile
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the ID of the tile.
     * 
     * @return ID of the tile
     */
    public int getID()
    {
        return id;
    }
    
    /**
     * Returns whether the tile can be passed through.
     * 
     * @return Whether tile is solid.
     */
    public boolean isSolid()
    {
        return solid;
    }
    
    /**
     * Returns the color of the tile in hex.
     * 
     * @return Color code of the tile
     */
    public int getColorCode()
    {
        return colorCode;
    }
}
