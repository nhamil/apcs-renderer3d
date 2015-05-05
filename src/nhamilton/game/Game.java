package nhamilton.game;

public class Game extends GameLoop
{
    private long timer;
    
    public Game() 
    {
        super(-1, 60);
    }
    
    public void init() 
    {
        timer = System.currentTimeMillis();
    }
    
    public void update() 
    {
        if(System.currentTimeMillis() - timer >= 1000) 
        {
            timer = System.currentTimeMillis();
            System.out.println(getData());
        }
    }
    
    public void render() 
    {
        
    }
}
