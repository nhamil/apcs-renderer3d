/**
 * 
 */
package nhamilton.game.math;

import java.text.DecimalFormat;

/**
 * @author Nicholas Hamilton
 *
 */
public class Matrix4f
{   
    private static DecimalFormat fmt = new DecimalFormat("0.00");
    
    private float m[][];
    
    public Matrix4f() 
    {
        m = new float[4][4];
    }
    
    public Matrix4f(float[][] m) 
    {
        this.m = m;
    }
    
    public Matrix4f initIdentity() 
    {
        m[0][0] = 1f; m[1][0] = 0f; m[2][0] = 0f; m[3][0] = 0f;
        m[0][1] = 0f; m[1][1] = 1f; m[2][1] = 0f; m[3][1] = 0f;
        m[0][2] = 0f; m[1][2] = 0f; m[2][2] = 1f; m[3][2] = 0f;
        m[0][3] = 0f; m[1][3] = 0f; m[2][3] = 0f; m[3][3] = 1f;
        
        return this;
    }
    
    public Matrix4f initTranslation(float tx, float ty, float tz) 
    {
        m[0][0] = 1f; m[1][0] = 0f; m[2][0] = 0f; m[3][0] = tx;
        m[0][1] = 0f; m[1][1] = 1f; m[2][1] = 0f; m[3][1] = ty;
        m[0][2] = 0f; m[1][2] = 0f; m[2][2] = 1f; m[3][2] = tz;
        m[0][3] = 0f; m[1][3] = 0f; m[2][3] = 0f; m[3][3] = 1f;
        
        return this;
    }
    
    public Matrix4f initScale(float sx, float sy, float sz) 
    {
        m[0][0] = sx; m[1][0] = 0f; m[2][0] = 0f; m[3][0] = 0f;
        m[0][1] = 0f; m[1][1] = sy; m[2][1] = 0f; m[3][1] = 0f;
        m[0][2] = 0f; m[1][2] = 0f; m[2][2] = sz; m[3][2] = 0f;
        m[0][3] = 0f; m[1][3] = 0f; m[2][3] = 0f; m[3][3] = 1f;
        
        return this;
    }
    
    public Matrix4f initRotation(float x, float y, float z) 
    {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();
        
        float cx = (float)Math.cos(Math.toRadians(x));
        float sx = (float)Math.sin(Math.toRadians(x));
        float cy = (float)Math.cos(Math.toRadians(y));
        float sy = (float)Math.sin(Math.toRadians(y));
        float cz = (float)Math.cos(Math.toRadians(z));
        float sz = (float)Math.sin(Math.toRadians(z));
        
        rx.m[0][0] = 1f; rx.m[1][0] = 0f; rx.m[2][0] = 0f; rx.m[3][0] = 0f;
        rx.m[0][1] = 0f; rx.m[1][1] = cx; rx.m[2][1] =-sx; rx.m[3][1] = 0f;
        rx.m[0][2] = 0f; rx.m[1][2] = sx; rx.m[2][2] = cx; rx.m[3][2] = 0f;
        rx.m[0][3] = 0f; rx.m[1][3] = 0f; rx.m[2][3] = 0f; rx.m[3][3] = 1f;
        
        ry.m[0][0] = cy; ry.m[1][0] = 0f; ry.m[2][0] =-sy; ry.m[3][0] = 0f;
        ry.m[0][1] = 0f; ry.m[1][1] = 1f; ry.m[2][1] = 0f; ry.m[3][1] = 0f;
        ry.m[0][2] = sy; ry.m[1][2] = 0f; ry.m[2][2] = cy; ry.m[3][2] = 0f;
        ry.m[0][3] = 0f; ry.m[1][3] = 0f; ry.m[2][3] = 0f; ry.m[3][3] = 1f;
        
        rz.m[0][0] = cz; rz.m[1][0] =-sz; rz.m[2][0] = 0f; rz.m[3][0] = 0f;
        rz.m[0][1] = sz; rz.m[1][1] = cz; rz.m[2][1] = 0f; rz.m[3][1] = 0f;
        rz.m[0][2] = 0f; rz.m[1][2] = 0f; rz.m[2][2] = 1f; rz.m[3][2] = 0f;
        rz.m[0][3] = 0f; rz.m[1][3] = 0f; rz.m[2][3] = 0f; rz.m[3][3] = 1f;
        
        m = rz.mul(ry).mul(rx).m;
        
        return this;
    }
    
    public Matrix4f initScreenTransform(float width, float height) 
    {
        m[0][0] = width/2f; m[1][0] = 0f; m[2][0] = 0f; m[3][0] = width/2f;
        m[0][1] = 0f; m[1][1] = -height/2f; m[2][1] = 0f; m[3][1] = height/2f;
        m[0][2] = 0f; m[1][2] = 0f; m[2][2] = 1f; m[3][2] = 0f;
        m[0][3] = 0f; m[1][3] = 0f; m[2][3] = 0f; m[3][3] = 1f;
        
        return this;
    }
    
    public Matrix4f initPerspective(float fov, float ratio, float zNear, float zFar) 
    {
        float tanHalfFOV = (float)Math.tan(Math.toRadians(fov/2));
        float zRange = zNear - zFar;
        
        m[0][0] = 1f/(tanHalfFOV*ratio); m[1][0] = 0f;            m[2][0] = 0f;                     m[3][0] = 0f;
        m[0][1] = 0f;                    m[1][1] = 1f/tanHalfFOV; m[2][1] = 0f;                     m[3][1] = 0f;
        m[0][2] = 0f;                    m[1][2] = 0f;            m[2][2] = (-zNear - zFar)/zRange; m[3][2] = 2*zFar*zNear/zRange;
        m[0][3] = 0f;                    m[1][3] = 0f;            m[2][3] = 1f;                     m[3][3] = 0f;
        
        return this;
    }
    
    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) 
    {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;
        
        m[0][0] = 2f/width; m[1][0] = 0f;        m[2][0] = 0f;        m[3][0] = -(right + left)/width;
        m[0][1] = 0f;       m[1][1] = 2f/height; m[2][1] = 0f;        m[3][1] = -(top + bottom)/height;
        m[0][2] = 0f;       m[1][2] = 0f;        m[2][2] = -2f/depth; m[3][2] = -(far + near)/depth;
        m[0][3] = 0f;       m[1][3] = 0f;        m[2][3] = 0f;        m[3][3] = 1f;
        
        return this;
    }
    
    public Matrix4f initRotation(Vector4f forward, Vector4f up) 
    {
        Vector4f f = forward.normalized();
        Vector4f u = up.normalized();
        Vector4f r = u.cross(f).normalized();
        
        m[0][0] = r.getX(); m[1][0] = r.getY(); m[2][0] = r.getZ(); m[3][0] = 0f;
        m[0][1] = u.getX(); m[1][1] = u.getY(); m[2][1] = u.getZ(); m[3][1] = 0f;
        m[0][2] = f.getX(); m[1][2] = f.getY(); m[2][2] = f.getZ(); m[3][2] = 0f;
        m[0][3] = 0f;       m[1][3] = 0f;       m[2][3] = 0f;       m[3][3] = 1f;
        
//        m[0][0] = r.getX(); m[1][0] = u.getX(); m[2][0] = f.getX(); m[3][0] = 0f;
//        m[0][1] = r.getY(); m[1][1] = u.getY(); m[2][1] = f.getY(); m[3][1] = 0f;
//        m[0][2] = r.getZ(); m[1][2] = u.getZ(); m[2][2] = f.getZ(); m[3][2] = 0f;
//        m[0][3] = 0f;       m[1][3] = 0f;       m[2][3] = 0f;       m[3][3] = 1f;
        
        return this;
    }
    
    //TODO: reverse all xy (rows and cols instaed)
    public Matrix4f mul(final Matrix4f r) 
    {
        float res[][] = new float[4][4];
        
        for(int x = 0; x < 4; x++) 
            for(int y = 0; y < 4; y++) 
            {
                res[x][y] = m[x][0] * r.m[0][y] + 
                            m[x][1] * r.m[1][y] + 
                            m[x][2] * r.m[2][y] + 
                            m[x][3] * r.m[3][y];
            }
        
        return new Matrix4f(res);
    }
    
    public Vector4f mul(final Vector4f r) 
    {
        return new Vector4f(r.getX()*m[0][0] + r.getY()*m[1][0] + r.getZ()*m[2][0] + r.getW()*m[3][0],
                            r.getX()*m[0][1] + r.getY()*m[1][1] + r.getZ()*m[2][1] + r.getW()*m[3][1],
                            r.getX()*m[0][2] + r.getY()*m[1][2] + r.getZ()*m[2][2] + r.getW()*m[3][2],
                            r.getX()*m[0][3] + r.getY()*m[1][3] + r.getZ()*m[2][3] + r.getW()*m[3][3]);
    }
    
    public float get(int x, int y) 
    {
        return m[x][y];
    }
    
    public void set(int x, int y, float val) 
    {
        m[x][y] = (float)val;
    }
    
    //TODO: better copy
    public Matrix4f copy() 
    {
        Matrix4f r = new Matrix4f();
        r.m = m;
        return r;
    }
    
    public String toString() 
    {
        String str = "Mat4[";
        
        for(int y = 0; y < 4; y++) 
        {
            if(y != 0) str += "     ";
            str +=  "x=" + fmt.format(m[0][y]);
            str += ",y=" + fmt.format(m[1][y]);
            str += ",z=" + fmt.format(m[2][y]);
            str += ",w=" + fmt.format(m[3][y]);
            if(y != 3) str += "\n";
            else str += "]";
        }
        
        return str;
    }
}
