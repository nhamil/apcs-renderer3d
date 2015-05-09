/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Matrix4f;
import nhamilton.game.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Vertex
{   
    private Vector4f pos;
    private Vector4f texCoord;
    
    public Vertex(Vector4f pos, Vector4f col) 
    {
        this.pos = pos;
        this.texCoord = col;
    }
    
    public float getX() { return pos.getX(); }
    public float getY() { return pos.getY(); }
    
    public float getTexCoordX() { return texCoord.getX(); }
    public float getTexCoordY() { return texCoord.getY(); }
    
    public Vector4f getPosition() { return pos; }
    public Vector4f getTexCoords() { return texCoord; }
    
    public Vertex getTransform(Matrix4f m) 
    {
        return new Vertex(m.mul(pos), texCoord);
    }
    
    public Vertex getPerspective() 
    {
        return new Vertex(new Vector4f(pos.getX()/pos.getW(), 
                                       pos.getY()/pos.getW(), 
                                       pos.getZ()/pos.getW(), 
                                       pos.getW()), texCoord);
    }
    
    public float getTriangleAreaDoubled(Vertex b, Vertex c) 
    {
        float x1 = b.getX() - pos.getX();
        float y1 = b.getY() - pos.getY();
        
        float x2 = c.getX() - pos.getX();
        float y2 = c.getY() - pos.getY();
        
        return x1*y2 - x2*y1;
    }
}
