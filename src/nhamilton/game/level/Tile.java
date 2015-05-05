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
    
    public Tile(String name, int id, boolean solid, int colorCode) 
    {
        this.name = name;
        this.id = id;
        this.solid = solid;
        this.colorCode = colorCode;
    }
    
    public String getName() { return name; }
    public int getID() { return id; }
    public boolean isSolid() { return solid; }
    public int getColorCode() { return colorCode; }
}
