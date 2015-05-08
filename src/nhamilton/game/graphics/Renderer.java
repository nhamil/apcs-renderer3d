/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Matrix4f;
import nhamilton.game.math.Vector4f;
import nhamilton.game.util.Vertex;

/**
 * @author Nicholas Hamilton
 *
 */
public class Renderer extends Bitmap
{   
//    private int scanBuffer[];
    
    private Bitmap tex = new Bitmap(0, 0);
    
    public Renderer(int width, int height) 
    {
        super(width, height);
        
//        scanBuffer = new int[height * 2];
    }
    
    public void setTexture(Bitmap b) 
    {
        tex = b;
    }
    
    public void drawTriangle(Vertex v1, Vertex v2, Vertex v3) 
    {
        Matrix4f t = new Matrix4f().initScreenTransform(getWidth(), getHeight());
        Vertex min = v1.getTransform(t).getPerspective();
        Vertex mid = v2.getTransform(t).getPerspective();
        Vertex max = v3.getTransform(t).getPerspective();
        
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
        Vector4f v1 = yMin.getPosition();
        Vector4f v2 = yMid.getPosition();
        Vector4f v3 = yMax.getPosition();
        Vector4f cross = v2.sub(v1).cross(v3.sub(v1));
        boolean minMaxLeft = cross.equals(cross.abs());
        
        Edge left = new Edge(yMin, yMax);
        Edge right = new Edge(yMin, yMid);
        if(!minMaxLeft) 
        {
            Edge tmp = left;
            left = right;
            right = tmp;
        }
        for(int y = (int)yMin.getY(); y < (int)yMid.getY(); y++) 
        {
            drawScanLine(left, right);
            left.next();
            right.next();
        }
        
        if(minMaxLeft) right = new Edge(yMid, yMax);
        else left = new Edge(yMid, yMax);
        
        for(int y = (int)yMid.getY(); y < (int)yMax.getY(); y++) 
        {
            drawScanLine(left, right);
            left.next();
            right.next();
        }
    }
    
    private void drawScanLine(Edge start, Edge end) 
    {
        int y = start.getY();
        float range = (float)end.getX() - start.getX();
        
        float tx = start.getTexCoordX();
        float ty = start.getTexCoordY();
        float txSlope = (end.getTexCoordX()-tx)/range;
        float tySlope = (end.getTexCoordY()-ty)/range;
        
        float invZ = 1;//start.getInverseZ();
        float invZSlope = 0;//(end.getInverseZ()-invZ)/range;
        
        float z;
        for(int x = start.getX(); x < end.getX(); x++) 
        {
            z = 1.0f/invZ;
            
            int srcX = (int)(tx*z*(tex.getWidth()-1));
            int srcY = (int)(ty*z*(tex.getHeight()-1));
            
            setPixel(x, y, tex.getPixel(srcX, srcY));
            
            tx += txSlope;
            ty += tySlope;
            invZ += invZSlope;
        }
    }
}
