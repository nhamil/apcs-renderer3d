package com.tenikkan.abacus.graphics.model;

import com.tenikkan.abacus.math.Vector4f;

public class Vertex
{
    private Vector4f position;
    private Vector4f color;
    private Vector4f texture;
    
    public Vertex(Vector4f position, Vector4f color, Vector4f texture) 
    {
        this.position = position;
        this.color = color;
        this.texture = texture;
    }
    
    public Vector4f getPosition() { return position; }
    public Vector4f getColor() { return color; }
    public Vector4f getTexture() { return texture; }
    
    public boolean equals(final Vertex r) 
    {
        return position.equals(r.position) &&
               color.equals(r.color) &&
               texture.equals(r.texture);
    }
}
