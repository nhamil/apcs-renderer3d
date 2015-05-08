/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Matrix4f;
import nhamilton.game.math.Vector4f;
import nhamilton.game.util.Console;
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
        int range = end.getX() - start.getX();
//        Vector4f slope = end.getColor().sub(start.getColor()).div(range);
//        Vector4f col = start.getColor().copy();
        float tx = start.getTexCoordX();
        float ty = start.getTexCoordY();
        float txSlope = (end.getTexCoordX()-tx)/(float)range;
        float tySlope = (end.getTexCoordY()-ty)/(float)range;
        
        for(int x = start.getX(); x < end.getX(); x++) 
        {
//            float amt = (float)(x-start.getX())/range;
//            col = col.add(slope);//start.getColor().lerp(end.getColor(), amt);
//            int r = (int)(col.getX()*255);
//            int g = (int)(col.getY()*255);
//            int b = (int)(col.getZ()*255);
//            if(r > 255 || g > 255 | b > 255 || r < 0 || g < 0 | b < 0) 
//            {
//                Console.outln(r + ", " + g + ", " + b, Console.WARNING);
//            }
//            setPixel(x, y, r<<16|g<<8|b);
            
            setPixel(x, y, tex.getPixel((int)(tx*(tex.getWidth()-1)), (int)(ty*(tex.getHeight()-1))));
            
            tx += txSlope;
            ty += tySlope;
        }
    }
}
