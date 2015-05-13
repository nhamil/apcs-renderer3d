package com.tenikkan.abacus.graphics.ab;


public class ABGradient 
{
    public float rCol[]; public float rColXStep, rColYStep;
    public float gCol[]; public float gColXStep, gColYStep;
    public float bCol[]; public float bColXStep, bColYStep;
    public float invZ[]; public float invZXStep, invZYStep;
    public float depth[]; public float depthXStep, depthYStep;
    public float texX[]; public float texXXStep, texXYStep;
    public float texY[]; public float texYXStep, texYYStep;
    
    public ABGradient(ABVertex min, ABVertex mid, ABVertex max) 
    {
        float invDX = calcInvDX(min, mid, max);
        
        rCol = new float[3];
        gCol = new float[3];
        bCol = new float[3];
        invZ = new float[3];
        depth = new float[3];
        texX = new float[3];
        texY = new float[3];
        
        invZ[0] = 1f / min.position.getW();
        invZ[1] = 1f / mid.position.getW();
        invZ[2] = 1f / max.position.getW();
        invZXStep = calcXStep(invZ, min, mid, max, invDX);
        invZYStep = calcYStep(invZ, min, mid, max,-invDX);
        
        depth[0] = min.position.getZ();
        depth[1] = mid.position.getZ();
        depth[2] = max.position.getZ();
        depthXStep = calcXStep(depth, min, mid, max, invDX);
        depthYStep = calcYStep(depth, min, mid, max,-invDX);
        
        rCol[0] = min.color.getX() * invZ[0];
        rCol[1] = mid.color.getX() * invZ[1];
        rCol[2] = max.color.getX() * invZ[2];
        rColXStep = calcXStep(rCol, min, mid, max, invDX);
        rColYStep = calcYStep(rCol, min, mid, max,-invDX);
        
        gCol[0] = min.color.getY() * invZ[0];
        gCol[1] = mid.color.getY() * invZ[1];
        gCol[2] = max.color.getY() * invZ[2];
        gColXStep = calcXStep(gCol, min, mid, max, invDX);
        gColYStep = calcYStep(gCol, min, mid, max,-invDX);
        
        bCol[0] = min.color.getZ() * invZ[0];
        bCol[1] = mid.color.getZ() * invZ[1];
        bCol[2] = max.color.getZ() * invZ[2];
        bColXStep = calcXStep(bCol, min, mid, max, invDX);
        bColYStep = calcYStep(bCol, min, mid, max,-invDX);
        
        texX[0] = min.texture.getX() * invZ[0];
        texX[1] = mid.texture.getX() * invZ[1];
        texX[2] = max.texture.getX() * invZ[2];
        texXXStep = calcXStep(texX, min, mid, max, invDX);
        texXYStep = calcYStep(texX, min, mid, max,-invDX);
        
        texY[0] = min.texture.getY() * invZ[0];
        texY[1] = mid.texture.getY() * invZ[1];
        texY[2] = max.texture.getY() * invZ[2];
        texYXStep = calcXStep(texY, min, mid, max, invDX);
        texYYStep = calcYStep(texY, min, mid, max,-invDX);
    }
    
    private float calcInvDX(ABVertex min, ABVertex mid, ABVertex max) 
    {
        return 1.0f / (((mid.getX() - max.getX()) * (min.getY() - max
                .getY())) - ((min.getX() - max.getX()) * (mid.getY() - max
                .getY())));
    }
    
    private float calcYStep(float vals[], ABVertex min, ABVertex mid, ABVertex max, float invDX) 
    {
        return (((vals[1] - vals[2]) * 
                 (min.getX() - max.getX())) - 
                ((vals[0] - vals[2]) *
                 (mid.getX() - max.getX()))) * invDX;
    }
    
    private float calcXStep(float vals[], ABVertex min, ABVertex mid, ABVertex max, float invDX) 
    {
        return (((vals[1] - vals[2]) * 
                 (min.getY() - max.getY())) - 
                ((vals[0] - vals[2]) *
                 (mid.getY() - max.getY()))) * invDX;
    }
}
