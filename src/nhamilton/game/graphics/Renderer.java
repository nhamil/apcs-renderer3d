/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Matrix4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Renderer extends Bitmap
{   
    private Bitmap tex = new Bitmap(1, 1);
    private Matrix4f screenTransform;
    
    float zBuffer[];
    
    public Renderer(int width, int height) 
    {
        super(width, height);
        
        screenTransform = new Matrix4f().initScreenTransform(getWidth(), getHeight());
        
        zBuffer = new float[width*height];
    }
    
    public void clear() 
    {
        fill(0x111111);
        for(int i = 0; i < zBuffer.length; i++)
            zBuffer[i] = Float.MAX_VALUE;
    }
    
    public void setDepthBuffer(int x, int y, float val) 
    {
        zBuffer[x + y*getWidth()] = val;
    }
    
    public float getDepthBuffer(int x, int y) 
    {
        return zBuffer[x + y*getWidth()];
    }
    
    public void setTexture(Bitmap b) { tex = b; }
    public Bitmap getTexture() { return tex; }
    
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) 
    {
        Vertex min = v1.getTransform(screenTransform).getPerspective();
        Vertex mid = v2.getTransform(screenTransform).getPerspective();
        Vertex max = v3.getTransform(screenTransform).getPerspective();
                
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
    
    private void scanTriangle(Vertex yMin, Vertex yMid, Vertex yMax) 
    {
        boolean minMaxLeft = yMin.getTriangleAreaDoubled(yMax, yMid) <= 0;
        
        Gradients grad = new Gradients(yMin, yMid, yMax);
        
        Edge tm = new Edge(grad, yMin, yMid, 0);
        Edge tb = new Edge(grad, yMin, yMax, 0);
        Edge mb = new Edge(grad, yMid, yMax, 1);
        
        Edge left = minMaxLeft ? tb : tm;
        Edge right = minMaxLeft ? tm : tb;
        
        drawScanLines(grad, left, right, tm.getYStart(), tm.getYEnd());
        
        if(minMaxLeft) right = mb;
        else           left = mb;
        
        drawScanLines(grad, left, right, mb.getYStart(), mb.getYEnd());
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
        
        float z;
        for(int x = xStart; x < xEnd; x++) 
        {
            z = 1f/invZ;
            int srcX = (int)(tx * z * (tex.getWidth() - 1) + 0.5f);
            int srcY = (int)(ty * z * (tex.getHeight() - 1) + 0.5f);
            copyPixel(x, y, srcX, srcY, tex);
            tx += grad.getTexCoordXXSlope();
            ty += grad.getTexCoordYXSlope();
            invZ += grad.getInvZXSlope();
        }
    }
}
