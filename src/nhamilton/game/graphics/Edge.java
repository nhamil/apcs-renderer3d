/**
 * 
 */
package nhamilton.game.graphics;



/**
 * @author Nicholas Hamilton
 *
 */
public class Edge
{   
    private int yStart;
    private int yEnd;
    
    private float x;
    private float xSlope;
    
    private float texCoordX;
    private float texCoordXSlope;
    
    private float texCoordY;
    private float texCoordYSlope;
    
    public Edge(Gradients grad, Vertex start, Vertex end, int top) 
    {
        yStart = (int)Math.ceil(start.getY());
        yEnd   = (int)Math.ceil(end.getY());
        
        float yDist = end.getY() - start.getY();
        float xDist = end.getX() - start.getX();
        
        float yPrestep = yStart - start.getY();
        
        xSlope = (float)xDist/yDist;
        x = start.getX() + yPrestep*xSlope;
        
        float xPrestep = x - start.getX();
        
        texCoordX = grad.getTexCoordX(top) + 
                    grad.getTexCoordXXSlope() * xPrestep + 
                    grad.getTexCoordXYSlope() * yPrestep;
        texCoordXSlope = grad.getTexCoordXYSlope() + grad.getTexCoordXXSlope() * xSlope;
        
        texCoordY = grad.getTexCoordY(top) + 
                grad.getTexCoordYXSlope() * xPrestep + 
                grad.getTexCoordYYSlope() * yPrestep;
        texCoordYSlope = grad.getTexCoordYYSlope() + grad.getTexCoordYXSlope() * xSlope;
        
//        tex = grad.getTexCoords(topIndex).add(grad.getTexCoordYSlope().mul(yPrestep)
//                                         .add(grad.getTexCoordXSlope().mul(xPrestep)));
//        
//        tSlope = grad.getTexCoordYSlope().add(grad.getTexCoordXSlope().mul(xSlope));
    }
    
    public int getYStart() { return yStart; }
    public int getYEnd() { return yEnd; }
    
    public int getX() { return (int)x; }
    public float getTexCoordX() { return texCoordX; }
    public float getTexCoordY() { return texCoordY; }
    
    public void next() 
    { 
        x += xSlope; 
        texCoordX += texCoordXSlope;
        texCoordY += texCoordYSlope;
    }
}
