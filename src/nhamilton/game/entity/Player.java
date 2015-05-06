package nhamilton.game.entity;

public class Player extends Entity
{
    private static String name = "Player";
    private static int colorCode = 0xff00ff;
    
    public Player(float x, float y) 
    {
        super(x, y, 1f, 2f);
    }
    
    public String getName() { return name; }
    
    public int getColorCode() { return colorCode; }
}
