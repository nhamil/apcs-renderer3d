package com.tenikkan.abacus.graphics.lighting;

import com.tenikkan.abacus.math.Vector4f;

public class DirectionalLight implements ILight
{
    private Vector4f direction;
    
    public DirectionalLight(Vector4f direction) 
    {
        this.direction = direction;
    }
    
    public Vector4f getDirection(Vector4f dest) 
    {
        return direction;
    }
}
