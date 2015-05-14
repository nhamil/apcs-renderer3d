package com.tenikkan.abacus.graphics.ab;

public class ABEdge 
{
    public int yStart, yEnd;
    public float x, xStep;
    public float rCol, rColStep;
    public float gCol, gColStep;
    public float bCol, bColStep;
    public float invZ, invZStep;
    public float depth, depthStep;
    public float texX, texXStep;
    public float texY, texYStep;
    
    public ABEdge(ABGradient grad, ABVertex top, ABVertex bot, int topIndex) 
    {
        yStart = ABUtil.getCoordY(top.getY());
        yEnd = ABUtil.getCoordY(bot.getY());
        
        float distY = (float)bot.getY() - top.getY();
        float distX = (float)bot.getX() - top.getX();
        
        float yPrestep = yStart - top.getY();
        
        xStep = (float)distX / distY;
        x = top.getX();// + yPrestep * xStep; //ABUtil.getCoordX(top.getX());
        
        float xPrestep = x - top.getX();
        
        rCol = grad.rCol[topIndex] + grad.rColYStep * yPrestep + grad.rColXStep * xPrestep;
        rColStep = grad.rColYStep + grad.rColXStep * xStep;
        
        gCol = grad.gCol[topIndex] + grad.gColYStep * yPrestep + grad.gColXStep * xPrestep;
        gColStep = grad.gColYStep + grad.gColXStep * xStep;
        
        bCol = grad.bCol[topIndex] + grad.bColYStep * yPrestep + grad.bColXStep * xPrestep;
        bColStep = grad.bColYStep + grad.bColXStep * xStep;
        
        invZ = grad.invZ[topIndex] + grad.invZYStep * yPrestep + grad.invZXStep * xPrestep;
        invZStep = grad.invZYStep + grad.invZXStep * xStep;
        
        depth = grad.depth[topIndex] + grad.depthYStep * yPrestep + grad.depthXStep * xPrestep;
        depthStep = grad.depthYStep + grad.depthXStep * xStep;
        
        texX = grad.texX[topIndex] + grad.texXYStep * yPrestep + grad.texXXStep * xPrestep;
        texXStep = grad.texXYStep + grad.texXXStep * xStep;
        
        texY = grad.texY[topIndex] + grad.texYYStep * yPrestep + grad.texYXStep * xPrestep;
        texYStep = grad.texYYStep + grad.texYXStep * xStep;
    }
    
    public int getYStart() { return yStart; }
    public int getYEnd() { return yEnd; }
    
    public float getX() { return x; }
    
    public void step() 
    {
        x += xStep;
        rCol += rColStep;
        gCol += gColStep;
        bCol += bColStep;
        invZ += invZStep;
        depth += depthStep;
        texX += texXStep;
        texY += texYStep;
    }
}
