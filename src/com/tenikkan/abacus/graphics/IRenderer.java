package com.tenikkan.abacus.graphics;

import com.tenikkan.abacus.graphics.lighting.*;
import com.tenikkan.abacus.graphics.model.*;

public interface IRenderer
{
    public static final int AB_CULLING                      = 0x00;
    public static final int AB_TEXTURE                      = 0x01;
    public static final int AB_COLOR                        = 0x02;
    public static final int AB_LIGHTING                     = 0x03;
    
    public static final int AB_POINTS                       = 0x00;
    public static final int AB_LINES                        = 0x01;
    public static final int AB_TRIANGLES                    = 0x02;
    public static final int AB_RECTANGLES                   = 0x03;
    public static final int AB_ABMIENT_LIGHT                = 0x04;
    public static final int AB_DIRECTIONAL_LIGHT            = 0x05;
    public static final int AB_POINT_LIGHT                  = 0x06;
    
    public void enable(int id);
    public void disable(int id);
    
    public void translate(float tx, float ty, float tz);
    public void rotate(float rx, float ry, float rz);
    public void scale(float sx, float sy, float sz);
    
    public void begin(int id);
    public void end();
    
    public void vertex3f(float x, float y, float z);
    public void color3i(int r, int g, int b);
    public void color4i(int r, int g, int b, int a);
    public void normal3f(float x, float y, float z);
    public void texture2f(float x, float y);
    public void set(int index);
    
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3);
    public void drawRectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4);
    public void drawMesh(Mesh mesh);
    
    public void setLight(int index, ILight light);
}
