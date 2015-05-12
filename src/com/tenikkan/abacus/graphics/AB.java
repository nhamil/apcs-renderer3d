package com.tenikkan.abacus.graphics;

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
    
    public static final int AB_MODELVIEW                    = 0x00;
    public static final int AB_PROJECTION                   = 0x01;
    
    private static boolean color = true;
    
    private static int[] pixels;
    private static int width;
    private static int height;
    private static int clearColor = 0x111111;
    
    private static int drawMode = -1;
    
    private static Vector4f curPosition;
    private static Vector4f curColor;
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
        
        vertList = new ABVertex[0];
        
        screenTransform = new Matrix4f().initIdentity();
    }
    
    private AB() {}
    
    public static void abSetContext(Bitmap bmp) 
    {
        pixels = bmp.getRaster();
        width = bmp.getWidth();
        height = bmp.getHeight();
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
    
    public static void abClear(int id) 
    {
        if((id & AB_FLAG_COLOR_BUFFER) != 0) 
        {
            for(int i = 0; i < pixels.length; i++) 
                pixels[i] = clearColor;
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
    
    public static void abVertex3f(float x, float y, float z) 
    {
        curPosition = new Vector4f(x, y, z, 1);
        
        vertList[curIndex++] = 
                new ABVertex(curPosition, curColor);
        
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
                drawLine(vertList[0], vertList[1]);
                drawLine(vertList[1], vertList[2]);
                drawLine(vertList[2], vertList[0]);
                
                fillTriangle(vertList[0], vertList[1], vertList[2]);
                break;
            case AB_QUADS:
                drawLine(vertList[0], vertList[1]);
                drawLine(vertList[1], vertList[2]);
                drawLine(vertList[2], vertList[0]);
                
                drawLine(vertList[2], vertList[3]);
                drawLine(vertList[3], vertList[0]);
                
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
        
        //TODO lerp color
        int col = getColor(pt1.color);
        if(!color) col = 0;
        
        //TODO implement calculated clipping
        float slope = (float)(y2 - y1)/(x2 - x1);
        
        if(slope > 1 || -slope > 1) 
        {
            slope = 1f/slope;
            float fx = x1;
            int startY = y1;
            int endY = y2;
            if(y2 < y1) 
            {
                fx = x2;
                startY = y2;
                endY = y1;
            }
            for(int y = startY; y <= endY; y++) 
            {
                int x = getCoordX(fx);
                if(inBounds(x, y)) 
                {
                    set(x, y, col);
                }
                fx += slope;
            }
        } else 
        {
            float fy = y1;
            int startX = x1;
            int endX = x2;
            if(x2 < x1) 
            {
                fy = y2;
                startX = x2;
                endX = x1;
            }
            for(int x = startX; x <= endX; x++) 
            {
                int y = getCoordY(fy);
                if(inBounds(x, y)) 
                {
                    set(x, y, col);
                }
                fy += slope;
            }
        }
    }
    
    private static void fillTriangle(ABVertex v1, ABVertex v2, ABVertex v3) 
    {
        ABVertex min = v1.transform(mvp).perspectiveDivide();
        ABVertex mid = v2.transform(mvp).perspectiveDivide();
        ABVertex max = v3.transform(mvp).perspectiveDivide();
        
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
        System.out.println("scan");
        
        boolean tbLeft = min.facingForward(max, mid);
        
        System.out.println(tbLeft);
        
        ABEdge tm = new ABEdge(min, mid);
        ABEdge mb = new ABEdge(mid, max);
        ABEdge tb = new ABEdge(min, max);
        
        ABEdge left = tbLeft ? tb : tm;
        ABEdge right = tbLeft ? tm : tb;
        
        drawScanLines(left, right, tb.getYStart(), tm.getYEnd());
        
        if(tbLeft) right = mb;
        else        left = mb;
        
        drawScanLines(left, right, mb.getYStart(), tb.getYEnd());
    }
    
    private static void drawScanLines(ABEdge left, ABEdge right, int yStart, int yEnd) 
    {
        for(int y = yStart; y < yEnd; y++) 
        {
            drawScanLine(left, right, y);
            left.step();
            right.step();
        }
    }
    
    private static void drawScanLine(ABEdge left, ABEdge right, int y) 
    {
        y *= width;
        
        for(int x = left.getX(); x <= right.getX(); x++) 
        {
            pixels[x + y] = 0xff0000;
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
        case AB_COLOR: color = val; return;
        }
    }
    
    private static void updateMVP() 
    {
        mvp = screenTransform.mul(mat[AB_PROJECTION].mul(mat[AB_MODELVIEW]));
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
        int a = (int)(col.getW() * 255) & 255;
        int r = (int)(col.getX() * 255) & 255;
        int g = (int)(col.getY() * 255) & 255;
        int b = (int)(col.getZ() * 255) & 255;
        
        return a<<24|r<<16|g<<8|b;
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
    
    private static class ABVertex
    {   
        public Vector4f position;
        public Vector4f color;
        
        public ABVertex(Vector4f pos, Vector4f col) 
        {
            position = pos;
            color = col;
        }
        
        public boolean facingForward(ABVertex b, ABVertex c) 
        {
            float x1 = b.getX() - position.getX();
            float y1 = b.getY() - position.getY();
            
            float x2 = c.getX() - position.getX();
            float y2 = c.getY() - position.getY();
            
            return x1*y2 - x2*y1 > 0;
        }
        
        public float getX() { return position.getX(); }
        public float getY() { return position.getY(); }
        
        public ABVertex perspectiveDivide() 
        {
            float w = position.getW();
            return new ABVertex(
                    new Vector4f(position.getX()/w, position.getY()/w, position.getZ()/w, position.getW()),
                    color
                    );
        }
        
        public ABVertex transform(Matrix4f m) 
        {
            return new ABVertex(
                    m.mul(position),
                    color
                    );
        }
    }
    
    private static class ABEdge 
    {
        public float x, xStep;
        public int yStart, yEnd;
        
        public ABEdge(ABVertex top, ABVertex bot) 
        {
            yStart = getCoordY(top.position.getY());
            yEnd = getCoordY(top.position.getY());
            
            float distY = (float)(bot.position.getY() - top.position.getY());
            float distX = (float)(bot.position.getX() - top.position.getX());
            
            x = getCoordX(top.position.getX());
            xStep = distX / distY;
        }
        
        public int getYStart() { return yStart; }
        public int getYEnd() { return yEnd; }
        
        public int getX() { return getCoordX(x); }
        
        public void step() 
        {
            x += xStep;
        }
    }
}
