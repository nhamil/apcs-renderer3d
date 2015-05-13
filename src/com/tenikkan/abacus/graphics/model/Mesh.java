package com.tenikkan.abacus.graphics.model;

import com.tenikkan.abacus.graphics.ab.ABVertex;

public class Mesh
{
    private ABVertex[] vert;
    private int[] ind;
    
    public Mesh(IModel model) 
    {
        vert = new ABVertex[model.getVertices().size()];
        ind = new int[model.getIndices().size()];
        
        for(int i = 0; i < vert.length; i++) 
        {
            vert[i] = new ABVertex(model.getVertices().get(i));
        }
        
        for(int i = 0; i < ind.length; i++) 
        {
            ind[i] = model.getIndices().get(i).intValue();
        }
    }
    
    public ABVertex[] getVertices() { return vert; }
    public int[] getIndices() { return ind; }
}
