/**
 * 
 */
package com.tenikkan.abacus.util;

/**
 * @author Nicholas Hamilton
 *
 */
public class Timer
{   
    public static final long SECOND = 1000L;
    
    private long ticks;
    private long time;
    
    public Timer() 
    {
        ticks = 0;
        time = System.currentTimeMillis();
    }
    
    public void tick() { ticks++; }
    
    public long getTicks() { return ticks; }
    public long getTimeMillis() { return time; }
    
    public void resetTicks() { ticks = 0; }
    public void resetTime() { time = getTime(); }
    
    public long getDelta() { return getTime() - time; }
    
    private long getTime() { return System.currentTimeMillis(); }
}
