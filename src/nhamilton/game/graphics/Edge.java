/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.util.Vertex;

/**
 * @author Nicholas Hamilton
 *
 */
public class Edge
{   
    private int y;
    private float x;
    private float xSlope;
    private float tx;
    private float txSlope;
    private float ty;
    private float tySlope;
    private float inverseZ;
    private float inverseZSlope;
    
    public Edge(Vertex start, Vertex end) 
    {
        y = (int)start.getY();
        x = start.getX();
        xSlope = (end.getX() - start.getX()) /
                 (end.getY() - start.getY());
        
        float range = end.getY()-start.getY();
        
        inverseZ = 1;//1f/start.getPosition().getW();
        inverseZSlope = 0;//(1f/end.getPosition().getW() - inverseZ)/range;
        
        tx = start.getTexCoordX()*inverseZ;
        ty = start.getTexCoordY()*inverseZ;
        txSlope = (end.getTexCoordX()*inverseZ-tx)/range;
        tySlope = (end.getTexCoordY()*inverseZ-ty)/range;
    }
    
    public int getX() { return (int)x; }
    public int getY() { return y; }
    
    public float getTexCoordX() { return tx; }
    public float getTexCoordY() { return ty; }
    
    public float getInverseZ() { return inverseZ; }
    
    public void next() 
    { 
        y++;
        x += xSlope; 
        tx += txSlope;
        ty += tySlope;
        
        inverseZ += inverseZSlope;
    }
}
