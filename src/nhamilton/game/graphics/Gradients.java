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
    private float[] invZ;
    private float[] depth;
    
    private float texCoordXXSlope;
    private float texCoordXYSlope;
    private float texCoordYXSlope;
    private float texCoordYYSlope;
    private float invZXSlope;
    private float invZYSlope;
    private float depthXSlope;
    private float depthYSlope;
    
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
        invZ = new float[3];
        depth = new float[3];
        
        invZ[0] = 1f/min.getPosition().getW();
        invZ[1] = 1f/mid.getPosition().getW();
        invZ[2] = 1f/max.getPosition().getW();
        invZXSlope = calcXStep(invZ, min, mid, max, invDX);
        invZYSlope = calcYStep(invZ, min, mid, max, invDY);
        
        texCoordX[0] = min.getTexCoordX() * invZ[0];
        texCoordX[1] = mid.getTexCoordX() * invZ[1];
        texCoordX[2] = max.getTexCoordX() * invZ[2];
        texCoordXXSlope = calcXStep(texCoordX, min, mid, max, invDX);
        texCoordXYSlope = calcYStep(texCoordX, min, mid, max, invDY);
        
        texCoordY[0] = min.getTexCoordY() * invZ[0];
        texCoordY[1] = mid.getTexCoordY() * invZ[1];
        texCoordY[2] = max.getTexCoordY() * invZ[2];
        texCoordYXSlope = calcXStep(texCoordY, min, mid, max, invDX);
        texCoordYYSlope = calcYStep(texCoordY, min, mid, max, invDY);
        
        depth[0] = min.getPosition().getZ();
        depth[1] = mid.getPosition().getZ();
        depth[2] = max.getPosition().getZ();
        depthXSlope = calcXStep(depth, min, mid, max, invDX);
        depthYSlope = calcYStep(depth, min, mid, max, invDY);
    }
    
    public float getTexCoordX(int index) { return texCoordX[index]; }
    public float getTexCoordY(int index) { return texCoordY[index]; }
    public float getInvZ(int index) { return invZ[index]; }
    public float getDepth(int index) { return depth[index]; }
    
    public float getTexCoordXXSlope() { return texCoordXXSlope; }
    public float getTexCoordXYSlope() { return texCoordXYSlope; }
    public float getTexCoordYXSlope() { return texCoordYXSlope; }
    public float getTexCoordYYSlope() { return texCoordYYSlope; }
    public float getInvZXSlope() { return invZXSlope; }
    public float getInvZYSlope() { return invZYSlope; }
    public float getDepthXSlope() { return depthXSlope; }
    public float getDepthYSlope() { return depthYSlope; }
    
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
