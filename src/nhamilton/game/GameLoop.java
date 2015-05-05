package nhamilton.game;

public abstract class GameLoop
{
    private double goalFrames;
    private double goalTicks;
    
    private double curFrames = 0.0;
    private double curTicks = 0.0;
    
    public GameLoop(double frames, double ticks) 
    {
        goalFrames = frames;
        goalTicks = ticks;
    }
    
    public double getGoalFPS() { return goalFrames; }
    public double getGoalUPS() { return goalTicks; }
    
    public double getCurrentFPS() { return curFrames; }
    public double getCurrentUPS() { return curTicks; }
    
    public void run() 
    {
        init();
        
        long time = System.nanoTime();
        
        double uTime = System.nanoTime();
        double fTime = System.nanoTime();
        
        double uStep = 1e9d / goalTicks;
        double fStep = 1e9d / goalFrames;
        
        int ups = 0, fps = 0;
        
        while(true) 
        {
            int loops = 0;
            while(loops++ < 10 && uTime < System.nanoTime()) 
            {
                update();
                ups++;
                
                uTime += uStep;
            }
        
            if(fTime < System.nanoTime()) 
            {
                render();
                fps++;
                
                fTime += fStep;
            }
            
            if(System.nanoTime() - time >= 1e9) 
            {
                curFrames = fps;
                curTicks = ups;
                fps = ups = 0;
                time = System.nanoTime();
            }
        }
    }
    
    public String getData() 
    {
        return getCurrentFPS() + " frames, " + getCurrentUPS() + " updates";
    }
    
    public abstract void init();
    
    public abstract void update();
    public abstract void render();
}
