package com.tenikkan.abacus.graphics.ab;

import com.tenikkan.abacus.graphics.model.Vertex;
import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Vector4f;

public class ABVertex
{   
    public Vector4f position;
    public Vector4f color;
    public Vector4f texture;
    
    public ABVertex(Vertex v) 
    {
        position = v.getPosition();
        color = v.getColor();
        texture = v.getTexture();
    }
    
    public ABVertex(Vector4f pos, Vector4f col, Vector4f tex) 
    {
        position = pos;
        color = col;
        texture = tex;
    }
    
    public ABVertex lerp(ABVertex r, float amt)
    {
        return new ABVertex(position.lerp(r.position, amt),
                            color.lerp(r.color, amt),
                            texture.lerp(r.texture, amt));
    }

    public float get(int index)
    {
        switch(index) 
        {
        case 0: return position.getX();
        case 1: return position.getY();
        case 2: return position.getZ();
        case 3: return position.getW(); 
        default: throw new RuntimeException("Index of of bounds");
        }
    }

    public boolean facingForward(ABVertex b, ABVertex c) 
    {
        float x1 = b.getX() - position.getX();
        float y1 = b.getY() - position.getY();
        
        float x2 = c.getX() - position.getX();
        float y2 = c.getY() - position.getY();
        
        return x1*y2 - x2*y1 < 0;
    }
    
    public float getX() { return position.getX(); }
    public float getY() { return position.getY(); }
    
    public ABVertex perspectiveDivide() 
    {
        float w = position.getW();
        return new ABVertex(
                new Vector4f(position.getX()/w, position.getY()/w, position.getZ()/w, position.getW()),
                color, texture
                );
    }
    
    public ABVertex transform(Matrix4f m) 
    {
        return new ABVertex(
                m.mul(position),
                color, texture
                );
    }
}
