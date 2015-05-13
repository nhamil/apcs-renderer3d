package com.tenikkan.abacus.graphics.model;

import java.util.ArrayList;
import java.util.List;

public class ArrayModel implements IModel
{
    private List<Vertex> vert;
    private List<Integer> ind;
    
    public ArrayModel() 
    {
        vert = new ArrayList<Vertex>();
        ind = new ArrayList<Integer>();
    }
    
    public int addVertex(Vertex v) 
    {
        vert.add(v);
        return vert.size() - 1;
    }
    
    public void addIndex(int i) 
    {
        ind.add(i);
    }
    
    public List<Vertex> getVertices() { return vert; }
    public List<Integer> getIndices() { return ind; }
}
