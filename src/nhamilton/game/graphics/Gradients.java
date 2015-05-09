/**
 * 
 */
package nhamilton.game.graphics;



/**
 * @author Nicholas Hamilton
 *
 */
public class Gradients
{   
    private float[] texCoordX;
    private float[] texCoordY;
    private float texCoordXXSlope;
    private float texCoordXYSlope;
    private float texCoordYXSlope;
    private float texCoordYYSlope;
    
    public Gradients(Vertex min, Vertex mid, Vertex max) 
    {
        float invDX = 1.0f / 
                ( ( (mid.getX() - max.getX())   *
                    (min.getY() - max.getY()) ) -
                  ( (min.getX() - max.getX())   *
                    (mid.getY() - max.getY()) ) );
        
        float invDY = -invDX;
        
        texCoordX = new float[3];
        texCoordY = new float[3];
        
        texCoordX[0] = min.getTexCoordX();
        texCoordX[1] = mid.getTexCoordX();
        texCoordX[2] = max.getTexCoordX();
        texCoordXXSlope = calcXStep(texCoordX, min, mid, max, invDX);
        texCoordXYSlope = calcYStep(texCoordX, min, mid, max, invDY);
        
        texCoordY[0] = min.getTexCoordY();
        texCoordY[1] = mid.getTexCoordY();
        texCoordY[2] = max.getTexCoordY();
        texCoordYXSlope = calcXStep(texCoordY, min, mid, max, invDX);
        texCoordYYSlope = calcYStep(texCoordY, min, mid, max, invDY);
    }
    
    public float getTexCoordX(int index) { return texCoordX[index]; }
    public float getTexCoordY(int index) { return texCoordY[index]; }
    
    public float getTexCoordXXSlope() { return texCoordXXSlope; }
    public float getTexCoordXYSlope() { return texCoordXYSlope; }
    public float getTexCoordYXSlope() { return texCoordYXSlope; }
    public float getTexCoordYYSlope() { return texCoordYYSlope; }
    
    private float calcYStep(float vals[], Vertex min, Vertex mid, Vertex max, float invDX) 
    {
        return (((vals[1] - vals[2]) * 
                 (min.getX() - max.getX())) - 
                ((vals[0] - vals[2]) *
                 (mid.getX() - max.getX()))) * invDX;
    }
    
    private float calcXStep(float vals[], Vertex min, Vertex mid, Vertex max, float invDX) 
    {
        return (((vals[1] - vals[2]) * 
                 (min.getY() - max.getY())) - 
                ((vals[0] - vals[2]) *
                 (mid.getY() - max.getY()))) * invDX;
    }
}
