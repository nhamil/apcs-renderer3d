/**
 * 
 */
package com.tenikkan.abacus.graphics.model;

import java.util.List;

import com.tenikkan.abacus.graphics.Vertex;

/**
 * @author Nicholas Hamilton
 *
 */
public class SimpleModel implements IModel
{   
    private Vertex[] v;
    private int[] i;
    
    public SimpleModel(List<Vertex> vert, List<Integer> ind) 
    {
        v = (Vertex[])vert.toArray(new Vertex[vert.size()]);
        Integer[] in = (Integer[])ind.toArray(new Integer[ind.size()]);
        i = new int[in.length];
        for(int j = 0; j < i.length; j++) 
            i[j] = in[j].intValue();
    }
    
    public Vertex[] getVertices() 
    {
        return v;
    }
    
    public int[] getIndices() 
    {
        return i;
    }
}
