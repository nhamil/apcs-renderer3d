package nhamilton.game.entity;

import nhamilton.game.Controller;

public class Player extends Entity
{
    private static String name = "Player";
    private static int colorCode = 0x7f007f;
    
    public Player(float x, float y, Controller c) 
    {
        super(x, y, 1f, 2f, c);
    }
    
    public String getName() { return name; }
    
    public int getColorCode() { return colorCode; }
}
