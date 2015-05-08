/**
 * 
 */
package nhamilton.game.util;

import nhamilton.game.math.Matrix4f;
import nhamilton.game.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Vertex
{   
    private Vector4f pos;
    private Vector4f col;
    private float texX, texY;
    
    public Vertex(Vector4f pos, Vector4f col, float tx, float ty) 
    {
        this.pos = pos;
        this.col = col;
        texX = tx;
        texY = ty;
    }
    
    public float getX() { return pos.getX(); }
    public float getY() { return pos.getY(); }
    
    public Vector4f getColor() { return col; }
    public Vector4f getPosition() { return pos; }
    
    public float getTexCoordX() { return texX; }
    public float getTexCoordY() { return texY; }
    
    public Vertex getTransform(Matrix4f m) 
    {
        return new Vertex(m.mul(pos), col, texX, texY);
    }
    
    public Vertex getPerspective() 
    {
        return new Vertex(new Vector4f(pos.getX()/pos.getW(), 
                                       pos.getY()/pos.getW(), 
                                       pos.getZ()/pos.getW(), 
                                       pos.getW()), col, texX, texY);
    }
}
