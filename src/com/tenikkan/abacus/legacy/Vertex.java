/**
 * 
 */
package com.tenikkan.abacus.legacy;

import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Vertex
{   
    private Vector4f pos;
    private Vector4f texCoord;
    
    public Vertex(Vector4f pos, Vector4f tex) 
    {
        this.pos = pos;
        this.texCoord = tex;
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
    
    public float get(int index) 
    {
        switch(index) 
        {
        case 0: return pos.getX();
        case 1: return pos.getY();
        case 2: return pos.getZ();
        case 3: return pos.getW();
        default: return 0.0f;
        }
    }

    public Vertex lerp(Vertex r, float amt)
    {
        return new Vertex(pos.lerp(r.pos, amt), 
                          texCoord.lerp(r.texCoord, amt));
    }
    
    public boolean equals(Vertex v) 
    {
        return pos.equals(v.pos) && texCoord.equals(v.texCoord);
    }
}
