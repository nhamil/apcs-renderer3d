package com.tenikkan.abacus.graphics;

import com.tenikkan.abacus.graphics.lighting.ILight;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.math.Vector4f;

public class ABContext implements IRenderer
{
    private boolean culling = true;
    private boolean texture = true;
    private boolean color   = false;
    private boolean light   = false;
    
    private int drawMode = -1;
    
    private Vector4f[] vecList;
    private Vector4f[] colList;
    private Vector4f[] normList;
    private Vector4f[] texList;
    
    private ILight[] lightList;
    private int curIndex;
    
    public void enable(int id) {}
    public void disable(int id) {}
    
    public void translate(float x, float y, float z) {}
    public void rotate(float x, float y, float z) {}
    public void scale(float x, float y, float z) {}
    
    public void begin(int id) {}
    public void end() {}
    
    public void vertex3f(float x, float y, float z) 
    {
        
    }
    
    public void color3i(int r, int g, int b) {}
    public void color4i(int r, int g, int b, int a) {}
    public void normal3f(float x, float y, float z) {}
    public void texture2f(float x, float y) {}
    public void set(int index) {}
    
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) {}
    public void drawRectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) {}
    public void drawMesh(Mesh mesh) {}
    
    public void setLight(int index, ILight light) {}
}
