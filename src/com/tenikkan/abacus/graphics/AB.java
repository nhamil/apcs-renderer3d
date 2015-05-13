package com.tenikkan.abacus.graphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Vector4f;

public final class AB
{
    public static final int AB_POINTS                       = 0x00;
    public static final int AB_LINES                        = 0x01;
    public static final int AB_TRIANGLES_WIREFRAME          = 0x02;
    public static final int AB_QUADS_WIREFRAME              = 0x03;
    public static final int AB_TRIANGLES                    = 0x04;
    public static final int AB_QUADS                        = 0x05;
    
    public static final int AB_FLAG_COLOR_BUFFER            = 0x01;
    public static final int AB_FLAG_DEPTH_BUFFER            = 0x02;
    
    public static final int AB_COLOR                        = 0x00;
    public static final int AB_DEPTH_TESTING                = 0x01;
    public static final int AB_TEXTURE_MAPPING              = 0x02;
    public static final int AB_CULLING                      = 0x03;
    
    public static final int AB_MODELVIEW                    = 0x00;
    public static final int AB_PROJECTION                   = 0x01;
    
    private static boolean colorEnabled = false;
    private static boolean textureMapping = false;
    private static boolean depthTesting = false;
    private static boolean culling = false;
    
    private static Bitmap texture;
    
    private static float[] zBuffer;
    private static int[] pixels;
    private static int width;
    private static int height;
    private static int clearColor = 0x111111;
    
    private static int drawMode = -1;
    
    private static Vector4f curPosition;
    private static Vector4f curColor;
    private static Vector4f curTexture;
    private static ABVertex[] vertList;
    
    private static int curIndex;
    
    private static int curMat = 0;
    private static Matrix4f[] mat;
    private static Matrix4f mvp;
    
    private static Matrix4f screenTransform;
    
    static 
    {
        mat = new Matrix4f[2];
        for(int i = 0; i < mat.length; i++)
            mat[i] = new Matrix4f();
        
        mvp = new Matrix4f();
        
        curPosition = new Vector4f(0, 0, 0, 1);
        curColor = new Vector4f(0, 0, 0, 0);
        curTexture = new Vector4f(0, 0, 0, 0);
        
        vertList = new ABVertex[0];
        
        screenTransform = new Matrix4f().initIdentity();
    }
    
    private AB() {}
    
    public static void abSetContext(Bitmap bmp) 
    {
        pixels = bmp.getRaster();
        width = bmp.getWidth();
        height = bmp.getHeight();
        zBuffer = new float[width*height];
        screenTransform.initScreenTransform(width - 1, height - 1);
    }
    
    public static void abMatrixMode(int id) 
    {
        curMat = id;
    }
    
    public static void abLoadIdentity() 
    {
        mat[curMat] = mat[curMat].initIdentity();
        updateMVP();
    }
    
    public static void abOrtho(float left, float right, float bottom, float top, float near, float far) 
    {
        mat[curMat] = new Matrix4f().initOrthographic(left, right, bottom, top, near, far);
        updateMVP();
    }
    
    public static void abPerspective(float fov, float ratio, float zNear, float zFar) 
    {
        mat[curMat] = new Matrix4f().initPerspective(fov, ratio, zNear, zFar);
        updateMVP();
    }
    
    public static void abEnable(int id) 
    {
        set(id, true);
    }
    
    public static void abDisable(int id) 
    {
        set(id, false);
    }
    
    public static boolean abGet(int id) 
    {
        switch(id) 
        {
        case AB_COLOR: return colorEnabled;
        case AB_DEPTH_TESTING: return depthTesting;
        case AB_TEXTURE_MAPPING: return textureMapping;
        case AB_CULLING: return culling;
        default: return false;
        }
    }
    
    public static void abLoadTexture(Bitmap bmp) 
    {
        texture = bmp;
    }
    
    public static void abClear(int id) 
    {
        if((id & AB_FLAG_COLOR_BUFFER) != 0) 
        {
            for(int i = 0; i < pixels.length; i++) 
                pixels[i] = clearColor;
        }
        if((id & AB_FLAG_DEPTH_BUFFER) != 0) 
        {
            for(int i = 0; i < zBuffer.length; i++) 
                zBuffer[i] = Float.MAX_VALUE;
        }
    }
    
    public static void abClearColor3i(int r, int g, int b) 
    {
        r &= 255;
        g &= 255;
        b &= 255;
        clearColor = r<<16|g<<8|b;
    }
    
    public static void abTranslate3f(float x, float y, float z) 
    {
        mat[curMat] = new Matrix4f().initTranslation(x, y, z).mul(mat[curMat]);
        updateMVP();
    }
    
    public static void abRotate3f(float x, float y, float z) 
    {
        mat[curMat] = new Matrix4f().initRotation(x, y, z).mul(mat[curMat]);
        updateMVP();
    }
    
    public static void abScale3f(float x, float y, float z) 
    {
        mat[curMat] = new Matrix4f().initScale(x, y, z).mul(mat[curMat]);
        updateMVP();
    }
    
    public static void abBegin(int id) 
    {
        int oldMode = drawMode;
        int oldIndex = curIndex;
        curIndex = 0;
        drawMode = id;
        switch(id) 
        {
        case AB_POINTS:
            setListIndices(1);
            return;
        case AB_LINES:
            setListIndices(2);
            return;
        case AB_TRIANGLES_WIREFRAME:
            setListIndices(3);
            return;
        case AB_QUADS_WIREFRAME:
            setListIndices(4);
            return;
        case AB_TRIANGLES:
            setListIndices(3);
            return;
        case AB_QUADS:
            setListIndices(4);
            return;
        default:
            drawMode = oldMode;
            curIndex = oldIndex;
            return;
        }
    }
    
    public static void abEnd() 
    {
        curIndex = -1;
        drawMode = -1;
        setListIndices(0);
    }
    
    public static void abVertex2f(float x, float y) 
    {
        abVertex3f(x, y, 0);
    }
    
    public static void abVertex3f(float x, float y, float z) 
    {
        curPosition = new Vector4f(x, y, z, 1);
        
        vertList[curIndex++] = 
                new ABVertex(curPosition, curColor, curTexture);
        
        if(curIndex == vertList.length) 
        {
            curIndex = 0;
            switch(drawMode) 
            {
            case AB_POINTS:
                drawPoint(vertList[0]);
                break;
            case AB_LINES:
                drawLine(vertList[0], vertList[1]);
                break;
            case AB_TRIANGLES_WIREFRAME:
                drawLine(vertList[0], vertList[1]);
                drawLine(vertList[1], vertList[2]);
                drawLine(vertList[2], vertList[0]);
                break;
            case AB_QUADS_WIREFRAME:
                drawLine(vertList[0], vertList[1]);
                drawLine(vertList[1], vertList[2]);
                drawLine(vertList[2], vertList[0]);
                
                drawLine(vertList[2], vertList[3]);
                drawLine(vertList[3], vertList[0]);
                break;
            case AB_TRIANGLES:
                fillTriangle(vertList[0], vertList[1], vertList[2]);
                break;
            case AB_QUADS:
                fillTriangle(vertList[0], vertList[1], vertList[2]);
                fillTriangle(vertList[0], vertList[2], vertList[3]);
                break;
            }
        }
    }
    
    public static void abColor3i(int r, int g, int b) 
    {
        curColor = new Vector4f(r/255f, g/255f, b/255f, 1);
    }
    
    public static void abTexture2f(float x, float y) 
    {
        curTexture = new Vector4f(x, y, 0, 0);
    }
    
    private static void drawPoint(ABVertex v1) 
    {
        ABVertex point = v1.transform(mvp).perspectiveDivide();
        
        int x = getCoordX(point.position.getX());
        int y = getCoordY(point.position.getY());
        int color = getColor(point.color);
        
        if(inBounds(x, y)) 
        {
            pixels[x + y*width] = color;
        }
    }
    
    // TODO add depth buffer
    private static void drawLine(ABVertex v1, ABVertex v2) 
    {
        ABVertex pt1 = v1.transform(mvp).perspectiveDivide();
        ABVertex pt2 = v2.transform(mvp).perspectiveDivide();
        
        int x1 = getCoordX(pt1.position.getX());
        int y1 = getCoordY(pt1.position.getY());
        int x2 = getCoordX(pt2.position.getX());
        int y2 = getCoordY(pt2.position.getY());
        
        float invZ1 = 1f/pt1.position.getZ();
        float invZ2 = 1f/pt2.position.getZ();
        
        //TODO lerp color
        int col = getColor(pt1.color);
        if(!colorEnabled) col = 0;
        
        //TODO implement calculated clipping
        float slope = (float)(y2 - y1)/(x2 - x1);
        
        //TODO fix depth buffer
        if(slope > 1 || -slope > 1) 
        {
            slope = 1f/slope;
            float fx = x1;
            int startY = y1;
            int endY = y2;
            float iz = invZ1;
            float dz = (invZ2 - invZ1)/(endY - startY);
            if(y2 < y1) 
            {
                fx = x2;
                startY = y2;
                endY = y1;
                iz = invZ2;
                dz = (invZ1 - invZ2)/(endY - startY);
            }
            for(int y = startY; y <= endY; y++) 
            {
                int x = getCoordX(fx);
                if(inBounds(x, y) && (!depthTesting || setZBuffer(x, y, 1f/iz))) 
                {
                    set(x, y, col);
                }
                fx += slope;
                iz += dz;
            }
        } else 
        {
            float fy = y1;
            int startX = x1;
            int endX = x2;
            float iz = invZ1;
            float dz = (invZ2 - invZ1)/(endX - startX);
            if(x2 < x1) 
            {
                fy = y2;
                startX = x2;
                endX = x1;
                iz = invZ2;
                dz = (invZ1 - invZ2)/(endX - startX);
            }
            for(int x = startX; x <= endX; x++) 
            {
                int y = getCoordY(fy);
                if(inBounds(x, y) && (!depthTesting || setZBuffer(x, y, 1f/iz))) 
                {
                    set(x, y, col);
                }
                fy += slope;
                iz += dz;
            }
        }
    }
    
    private static void fillTriangle(ABVertex v1, ABVertex v2, ABVertex v3) 
    {
        List<ABVertex> vert = new ArrayList<ABVertex>();
        List<ABVertex> aux = new ArrayList<ABVertex>();
        
        vert.add(v1.transform(mvp));
        vert.add(v2.transform(mvp));
        vert.add(v3.transform(mvp));
        
        if(clipPolyAxis(vert, aux, 0) &&
           clipPolyAxis(vert, aux, 1) &&
           clipPolyAxis(vert, aux, 2)) 
        {
            ABVertex initial = vert.get(0).transform(screenTransform);
            ABVertex last = vert.get(1).transform(screenTransform);
            ABVertex newV;
            
            for(int i = 2; i < vert.size(); i++) 
            {
                newV = vert.get(i).transform(screenTransform);
                drawTriangle(initial, last, newV);
                last = newV;
            }
        }
    }
    
    private static boolean clipPolyAxis(List<ABVertex> vert, List<ABVertex> aux, int index)
    {
        clipPolyComponent(vert, index, 1.0f, aux);
        vert.clear();
        
        if(aux.isEmpty()) return false;
        
        clipPolyComponent(aux, index, -1.0f, vert);
        aux.clear();
        
        return !vert.isEmpty();
    }

    private static void clipPolyComponent(List<ABVertex> vert, int index, float factor, List<ABVertex> res)
    {
        ABVertex prevVert = vert.get(vert.size() - 1);
        float prevComp = prevVert.get(index) * factor;
        boolean prevInside = prevComp <= prevVert.position.getW();
        
        Iterator<ABVertex> it = vert.iterator();
        while(it.hasNext()) 
        {
            ABVertex curVert = it.next();
            float curComp = curVert.get(index) * factor;
            boolean curInside = curComp <= curVert.position.getW();
            
            if(curInside ^ prevInside) 
            {
                float amt = (prevVert.position.getW() - prevComp)
                        / ((prevVert.position.getW() - prevComp) - 
                           (curVert.position.getW() - curComp));
                
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

    private static void drawTriangle(ABVertex v1, ABVertex v2, ABVertex v3) 
    {
        ABVertex min = v1.perspectiveDivide();
        ABVertex mid = v2.perspectiveDivide();
        ABVertex max = v3.perspectiveDivide();
        
        if(culling && !min.facingForward(max, mid)) return;
        
        ABVertex tmp;
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
    
    private static void scanTriangle(ABVertex min, ABVertex mid, ABVertex max) 
    {
        boolean tbLeft = min.facingForward(max, mid);
        
        ABGradient grad = new ABGradient(min, mid, max);
        
        ABEdge tm = new ABEdge(grad, min, mid, 0);
        ABEdge tb = new ABEdge(grad, min, max, 0);
        ABEdge mb = new ABEdge(grad, mid, max, 1);
        
        ABEdge left = tbLeft ? tb : tm;
        ABEdge right = tbLeft ? tm : tb;
        
        drawScanLines(grad, left, right, tb.getYStart(), tm.getYEnd());
        
        if(tbLeft) right = mb;
        else        left = mb;
        
        drawScanLines(grad, left, right, mb.getYStart(), tb.getYEnd());
    }
    
    private static void drawScanLines(ABGradient grad, ABEdge left, ABEdge right, int yStart, int yEnd) 
    {
        for(int y = yStart; y < yEnd; y++) 
        {
            drawScanLine(grad, left, right, y);
            left.step();
            right.step();
        }
    }
    
    private static void drawScanLine(ABGradient grad, ABEdge left, ABEdge right, int y) 
    {
        int startX = left.getX(); 
        int endX = right.getX();
        
        float xPrestep = left.x - startX;
        
        Vector4f colVec = new Vector4f();
        float rCol = left.rCol + grad.rColXStep * xPrestep;
        float gCol = left.gCol + grad.gColXStep * xPrestep;
        float bCol = left.bCol + grad.bColXStep * xPrestep;
        
        float texX = left.texX + grad.texXXStep * xPrestep;
        float texY = left.texY + grad.texYXStep * xPrestep;
        
        float invZ = left.invZ + grad.invZXStep * xPrestep;
        
        float depth = left.depth + grad.depthXStep * xPrestep;
        
        float z;
        for(int x = startX; x <= endX; x++) 
        {
            z = 1f / invZ;
            colVec.set(rCol * z, gCol * z, bCol * z, 1);
            
            if(!depthTesting || setZBuffer(x, y, depth)) 
            {
                int col;
                if(!colorEnabled && textureMapping) 
                {
                    int srcX = (int)clampf(0, texture.getWidth() - 1f, texX * z * texture.getWidth());
                    int srcY = (int)clampf(0, texture.getHeight() - 1f, texY * z * texture.getHeight());
                    col = texture.getPixel(srcX, srcY);
                } else if(colorEnabled && textureMapping)
                {
                    int srcX = (int)clampf(0, texture.getWidth() - 1f, texX * z * texture.getWidth());
                    int srcY = (int)clampf(0, texture.getHeight() - 1f, texture.getHeight() - texY * z * texture.getHeight());
                    int srcC = texture.getPixel(srcX, srcY);
                    Vector4f texCol = new Vector4f(((srcC>>16)&255)/255f, ((srcC>>8)&255)/255f, (srcC&255)/255f, 1);
                    col = getColor(colVec.mul(texCol));
                } else if(colorEnabled && !textureMapping) 
                {
                    col = getColor(colVec);
                } else 
                {
                    col = 0;
                } 
                
                pixels[x + y*width] = col;
            }
            
            rCol += grad.rColXStep;
            gCol += grad.gColXStep;
            bCol += grad.bColXStep;
            depth += grad.depthXStep;
            invZ += grad.invZXStep;
            texX += grad.texXXStep;
            texY += grad.texYXStep;
        }
    }
    
    private static void setListIndices(int num) 
    {
        vertList = new ABVertex[num];
    }
    
    private static void set(int id, boolean val) 
    {
        switch(id) 
        {
        case AB_COLOR: colorEnabled = val; return;
        case AB_DEPTH_TESTING: depthTesting = val; return;
        case AB_TEXTURE_MAPPING: textureMapping = val; return;
        case AB_CULLING: culling = val; return;
        }
    }
    
    private static void updateMVP() 
    {
        mvp = mat[AB_PROJECTION].mul(mat[AB_MODELVIEW]);
    }
    
    private static boolean inBounds(int x, int y) 
    {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    private static int getCoordX(float x) 
    {
        return (int)Math.floor(x + 0.5f);
    }
    
    private static int getCoordY(float y) 
    {
        return (int)Math.floor(y + 0.5f);
    }
    
    private static int getColor(Vector4f col) 
    {
        int a = clampi(0, 255, (int)(col.getW() * 255));
        int r = clampi(0, 255, (int)(col.getX() * 255));
        int g = clampi(0, 255, (int)(col.getY() * 255));
        int b = clampi(0, 255, (int)(col.getZ() * 255));
        
        return a<<24|r<<16|g<<8|b;
    }
    
    private static int clampi(int min, int max, int val) 
    {
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }
    
    private static float clampf(float min, float max, float val) 
    {
        if(val < min) return min;
        if(val > max) return max;
        return val;
    }
    
    @SuppressWarnings("unused")
    private static void set(int x, int y, int r, int g, int b) 
    {
        pixels[x + y*width] = r<<16|g<<8|b;
    }
    
    private static void set(int x, int y, int val) 
    {
        pixels[x + y*width] = val;
    }
    
    private static boolean setZBuffer(int x, int y, float val) 
    {
        int index = x + y*width;
        if(zBuffer[index] >= val) 
        {
            zBuffer[index] = val;
            return true;
        }
        return false;
    }
    
    private static class ABVertex
    {   
        public Vector4f position;
        public Vector4f color;
        public Vector4f texture;
        
        public ABVertex(Vector4f pos, Vector4f col, Vector4f tex) 
        {
            position = pos;
            color = col;
            texture = tex;
        }
        
        public ABVertex lerp(ABVertex r, float amt)
        {
            return new ABVertex(position.lerp(r.position, amt),
                                color.lerp(r.color, amt),
                                texture.lerp(r.texture, amt));
        }

        public float get(int index)
        {
            switch(index) 
            {
            case 0: return position.getX();
            case 1: return position.getY();
            case 2: return position.getZ();
            case 3: return position.getW(); 
            default: throw new RuntimeException("Index of of bounds");
            }
        }

        public boolean facingForward(ABVertex b, ABVertex c) 
        {
            float x1 = b.getX() - position.getX();
            float y1 = b.getY() - position.getY();
            
            float x2 = c.getX() - position.getX();
            float y2 = c.getY() - position.getY();
            
            return x1*y2 - x2*y1 < 0;
        }
        
        public float getX() { return position.getX(); }
        public float getY() { return position.getY(); }
        
        public ABVertex perspectiveDivide() 
        {
            float w = position.getW();
            return new ABVertex(
                    new Vector4f(position.getX()/w, position.getY()/w, position.getZ()/w, position.getW()),
                    color, texture
                    );
        }
        
        public ABVertex transform(Matrix4f m) 
        {
            return new ABVertex(
                    m.mul(position),
                    color, texture
                    );
        }
    }
    
    private static class ABEdge 
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
            yStart = getCoordY(top.getY());
            yEnd = getCoordY(bot.getY());
            
            float distY = bot.getY() - top.getY();
            float distX = bot.getX() - top.getX();
            
            float yPrestep = yStart - top.getY();
            
            x = getCoordX(top.getX());
            xStep = distX / distY;
            
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
        
        public int getX() { return getCoordX(x); }
        
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
    
    private static class ABGradient 
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
}
