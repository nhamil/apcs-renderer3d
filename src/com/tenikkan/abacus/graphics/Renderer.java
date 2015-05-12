/**
 * 
 */
package com.tenikkan.abacus.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tenikkan.abacus.math.Matrix4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Renderer extends Bitmap
{   
    private Bitmap tex = new Bitmap(1, 1);
    private Matrix4f screenTransform;
    
    float zBuffer[];
    
    private boolean clipping = false;
    private boolean wireframe = false;
    
    public Renderer(int width, int height) 
    {
        super(width, height);
        
        screenTransform = new Matrix4f().initScreenTransform(getWidth(), getHeight());
        
        zBuffer = new float[width*height];
    }
    
    public void clear() { clear(0xff111111); }
    
    public void clear(int col) 
    {
        fill(col);
        for(int i = 0; i < zBuffer.length; i++)
            zBuffer[i] = Float.MAX_VALUE;
    }
    
    public boolean isCullingEnabled() { return clipping; }
    public void setCullingEnabled(boolean clip) { clipping = clip; }
    
    private boolean setDepthBuffer(int x, int y, float val) 
    {
        int index = x + y*getWidth();
        if(zBuffer[index] > val) 
        {
            zBuffer[x + y*getWidth()] = val;
            return true;
        }
        return false;
    }
    
    public void setTexture(Bitmap b) { tex = b; }
    public Bitmap getTexture() { return tex; }
    
    public void drawRectangle(Vertex v1, Vertex v2, Vertex v3, Vertex v4) 
    {
        drawTriangle(v1, v2, v3);
        drawTriangle(v1, v3, v4);
    }
    
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) 
    {
        List<Vertex> vert = new ArrayList<Vertex>();
        List<Vertex> aux = new ArrayList<Vertex>();
        
        vert.add(v1);
        vert.add(v2);
        vert.add(v3);
        
        if(clipPolyAxis(vert, aux, 0) &&
           clipPolyAxis(vert, aux, 1) &&
           clipPolyAxis(vert, aux, 2)) 
        {
            Vertex initial = vert.get(0);
            
            for(int i = 1; i < vert.size() - 1; i++) 
            {
                fillTriangle(initial, vert.get(i), vert.get(i+1));
            }
        }
    }
    
    private void fillTriangle(Vertex v1, Vertex v2, Vertex v3) 
    {
        Vertex min = v1.getTransform(screenTransform).getPerspective();
        Vertex mid = v2.getTransform(screenTransform).getPerspective();
        Vertex max = v3.getTransform(screenTransform).getPerspective();
        
        if(clipping && min.getTriangleAreaDoubled(max, mid) >= 0) return;
        
        Vertex tmp;
        if(max.getY() < mid.getY()) 
        {
            tmp = max;
            max = mid;
            mid = tmp;
        }
        if(mid.getY() < min.getY()) 
        {
            tmp = mid;
            mid = min;
            min = tmp;
        }
        if(max.getY() < mid.getY()) 
        {
            tmp = max;
            max = mid;
            mid = tmp;
        }
        
        scanTriangle(min, mid, max);
    }
    
    private boolean clipPolyAxis(List<Vertex> vert, List<Vertex> aux, int index) 
    {
        clipPolyComponent(vert, index, 1.0f, aux);
        vert.clear();
        
        if(aux.isEmpty()) return false;
        
        clipPolyComponent(aux, index, -1.0f, vert);
        aux.clear();
        
        return !vert.isEmpty();
    }
    
    private void clipPolyComponent(List<Vertex> vert, int index, float factor, List<Vertex> res) 
    {
        Vertex prevVert = vert.get(vert.size() - 1);
        float prevComp = prevVert.get(index) * factor;
        boolean prevInside = prevComp <= prevVert.getPosition().getW();
        
        Iterator<Vertex> it = vert.iterator();
        while(it.hasNext()) 
        {
            Vertex curVert = it.next();
            float curComp = curVert.get(index) * factor;
            boolean curInside = curComp <= curVert.getPosition().getW();
            
            if(curInside ^ prevInside) 
            {
                float amt = (prevVert.getPosition().getW() - prevComp)
                        / ((prevVert.getPosition().getW() - prevComp) - 
                           (curVert.getPosition().getW() - curComp));
                
                res.add(prevVert.lerp(curVert, amt));
            }
            
            if(curInside) 
            {
                res.add(curVert);
            }
            
            prevVert = curVert;
            prevComp = curComp;
            prevInside = curInside;
        }
    }
    
    private void scanTriangle(Vertex yMin, Vertex yMid, Vertex yMax) 
    {
        boolean minMaxLeft = yMin.getTriangleAreaDoubled(yMax, yMid) <= 0;
        
        Gradients grad = new Gradients(yMin, yMid, yMax);
        
        Edge tm = new Edge(grad, yMin, yMid, 0);
        Edge tb = new Edge(grad, yMin, yMax, 0);
        Edge mb = new Edge(grad, yMid, yMax, 1);
        
        Edge left = minMaxLeft ? tb : tm;
        Edge right = minMaxLeft ? tm : tb;
        
        drawScanLines(grad, left, right, tm.getYStart(), (int)clamp(0, getHeight(), tm.getYEnd()));
        
        if(minMaxLeft) right = mb;
        else           left = mb;
        
        drawScanLines(grad, left, right, mb.getYStart(), (int)clamp(0, getHeight(), mb.getYEnd()));
    }
    
    private void drawScanLines(Gradients grad, Edge left, Edge right, int yStart, int yEnd) 
    {
        for(int y = yStart; y < yEnd; y++) 
        {
            drawScanLine(grad, left, right, y);
            left.next();
            right.next();
        }
    }
    
    private void drawScanLine(Gradients grad, Edge start, Edge end, int y) 
    {
        int xStart = (int)Math.ceil(start.getX());
        int xEnd   = (int)Math.ceil(end.getX());
        
        float xPrestep = xStart - start.getX();
        
        float tx = start.getTexCoordX() + grad.getTexCoordXXSlope()*xPrestep;
        float ty = start.getTexCoordY() + grad.getTexCoordYXSlope()*xPrestep;
        
        float invZ = start.getInverseZ() + grad.getInvZXSlope()*xPrestep;
        
        float depth = start.getDepth() + grad.getDepthXSlope()*xPrestep;
        
        float z;
        for(int x = xStart; x < xEnd; x++) 
        {
            if(!wireframe || (x == xStart || x == xEnd - 1)) 
            {
                z = 1f/invZ;
                int srcX = (int)clamp(0, tex.getWidth() - 1,  tx * z * (tex.getWidth()) + 0.0f);
                int srcY = (int)clamp(0, tex.getHeight() - 1, tex.getHeight() - ty * z * (tex.getHeight()) + 0.0f);
                
                if(!tex.isTransparent(srcX, srcY) && setDepthBuffer(x, y, depth)) 
                {
                    copyPixel(x, y, srcX, srcY, tex);
                }
            }
            
            tx += grad.getTexCoordXXSlope();
            ty += grad.getTexCoordYXSlope();
            invZ += grad.getInvZXSlope();
            depth += grad.getDepthXSlope();
        }
    }
    
    private float clamp(float min, float max, float val) 
    {
        return val < min ? min : val > max ? max : val;
    }
}
