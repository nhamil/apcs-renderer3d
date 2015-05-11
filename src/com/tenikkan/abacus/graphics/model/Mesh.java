/**
 * 
 */
package com.tenikkan.abacus.graphics.model;

import com.tenikkan.abacus.graphics.Renderer;
import com.tenikkan.abacus.graphics.Vertex;
import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Transform;


/**
 * @author Nicholas Hamilton
 *
 */
public class Mesh
{   
    private Vertex[] vertices;
    private int[] indices;
    
    public Mesh(String name) 
    {
        IModel model = new NMModel(name);
        vertices = model.getVertices();
        indices = model.getIndices();
    }
    
    public Mesh(IModel model) 
    {
        vertices = model.getVertices();
        indices = model.getIndices();
    }
    
    public void render(Renderer render, Transform transform) 
    {
        Matrix4f m = transform.getMatrix();
        
        if(vertices == null | indices == null) return;
        for(int i = 0; i < indices.length; i += 3) 
        {
            render.drawTriangle(vertices[indices[i    ]].getTransform(m),
                                vertices[indices[i + 1]].getTransform(m),
                                vertices[indices[i + 2]].getTransform(m));
        }
    }
}
