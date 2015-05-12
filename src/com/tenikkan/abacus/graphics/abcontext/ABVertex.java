/**
 * 
 */
package com.tenikkan.abacus.graphics.abcontext;

import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class ABVertex
{   
    public Vector4f position;
    public Vector4f color;
    
    public ABVertex(Vector4f pos, Vector4f col) 
    {
        position = pos;
        color = col;
    }
    
    public ABVertex perspectiveDivide() 
    {
        float w = position.getW();
        return new ABVertex(
                new Vector4f(position.getX()/w, position.getY()/w, position.getZ()/w, position.getW()),
                color
                );
    }
    
    public ABVertex transform(Matrix4f m) 
    {
        return new ABVertex(
                m.mul(position),
                color
                );
    }
}
