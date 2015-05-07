package nhamilton.game.util;

/**
 * 
 * Main loop of the game. Subclasses must decide what to do with the init(),
 * update() and render() methods.
 * 
 * @author Nicholas Hamilton
 *
 */

public abstract class GameLoop
{
    private double goalFrames;
    private double goalTicks;
    
    private double curFrames = 0.0;
    private double curTicks = 0.0;
    
    /**
     * Initializes the game loop.
     * 
     * @param frames Ideal frame rate
     * @param ticks Ideal updates per second
     */
    public GameLoop(double frames, double ticks)
    {
        goalFrames = frames;
        goalTicks = ticks;
    }
    
    /**
     * Returns the set frames per second.
     * 
     * @return Ideal frames per second
     */
    public double getGoalFPS()
    {
        return goalFrames;
    }
    
    /**
     * Returns the set updates per second.
     * 
     * @return Ideal updates per second
     */
    public double getGoalUPS()
    {
        return goalTicks;
    }
    
    /**
     * Returns the current FPS.
     * 
     * @return Frame rate
     */
    public double getCurrentFPS()
    {
        return curFrames;
    }
    
    /**
     * Returns the current updates per second.
     * 
     * @return Updates per second
     */
    public double getCurrentUPS()
    {
        return curTicks;
    }
    
    /**
     * Main method of the class. Calls init, update and render.
     */
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
    
    /**
     * Prints the current frame rate and update rate.
     * 
     * @return Frame rate data
     */
    public String getData()
    {
        return getCurrentFPS() + " frames, " + getCurrentUPS() + " updates";
    }
    
    /**
     * Called at the start of run().
     */
    public abstract void init();
    
    /**
     * Called when the loop should update.
     */
    public abstract void update();
    
    /**
     * Called when the loop should render.
     */
    public abstract void render();
}
