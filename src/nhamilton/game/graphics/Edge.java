/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Vector4f;
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
    private Vector4f color;
    private Vector4f colorSlope;
    private float tx;
    private float txSlope;
    private float ty;
    private float tySlope;
    
    public Edge(Vertex start, Vertex end) 
    {
        y = (int)start.getY();
        x = start.getX();
        xSlope = (end.getX() - start.getX()) /
                 (end.getY() - start.getY());
        
        float range = end.getY()-start.getY();
        
        color = start.getColor().copy();
        colorSlope = end.getColor().sub(color).div(range);
        
        tx = start.getTexCoordX();
        ty = start.getTexCoordY();
        txSlope = (end.getTexCoordX()-tx)/range;
        tySlope = (end.getTexCoordY()-ty)/range;
    }
    
    public int getX() { return (int)x; }
    public int getY() { return y; }
    
    public float getTexCoordX() { return tx; }
    public float getTexCoordY() { return ty; }
    
    public Vector4f getColor() { return color; }
    
    public void next() 
    { 
        y++;
        x += xSlope; 
        color = color.add(colorSlope);
        
        tx += txSlope;
        ty += tySlope;
    }
}
