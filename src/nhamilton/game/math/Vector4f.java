/**
 * 
 */
package nhamilton.game.math;

import java.text.DecimalFormat;

/**
 * @author Nicholas Hamilton
 *
 */
public class Vector4f
{   
    private static DecimalFormat fmt = new DecimalFormat("0.00");
    
    private float x, y, z, w;
    
    public Vector4f() 
    {
        this(0, 0, 0, 1);
    }
    
    public Vector4f(final Vector4f r) 
    {
        this(r.x, r.y, r.z, r.w);
    }
    
    public Vector4f(float x, float y, float z, float w) 
    {
        set(x, y, z, w);
    }
    
    public Vector4f(float x, float y, float z) 
    {
        set(x, y, z, 1);
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }
    
    public void setX(float x) { this.x = (float)x; }
    public void setY(float y) { this.y = (float)y; }
    public void setZ(float z) { this.z = (float)z; }
    public void setW(float w) { this.w = (float)w; }
    
    public void set(float x, float y, float z, float w) 
    {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
        this.w = (float)w;
    }
    
    public float lengthSquared() { return x * x + y * y + z * z + w * w; }
    public float length() { return (float)Math.sqrt(x * x + y * y + z * z + w * w); }
    
    public Vector4f normalize() 
    {
        float len = length();
        if(len == 0) return this;
        x /= len; y /= len; z /= len; w /= len;
        return this;
    }
    
    public Vector4f cross(final Vector4f r) 
    {
        float nx = y*r.z - z*r.y;
        float ny = z*r.x - x*r.z;
        float nz = x*r.y - y*r.x;
        set(nx, ny, nz, 0);
        return this;
    }
    
    public Vector4f add(float r) { x += r; y += r; z += r; w += r; return this; }
    public Vector4f sub(float r) { x -= r; y -= r; z -= r; w -= r; return this; }
    public Vector4f mul(float r) { x *= r; y *= r; z *= r; w *= r; return this; }
    public Vector4f div(float r) { x /= r; y /= r; z /= r; w /= r; return this; }
    
    public Vector4f add(float rx, float ry, float rz, float rw) { x += rx; y += ry; z += rz; w += rw; return this; }
    public Vector4f sub(float rx, float ry, float rz, float rw) { x -= rx; y -= ry; z -= rz; w -= rw; return this; }
    public Vector4f mul(float rx, float ry, float rz, float rw) { x *= rx; y *= ry; z *= rz; w *= rw; return this; }
    public Vector4f div(float rx, float ry, float rz, float rw) { x /= rx; y /= ry; z /= rz; w /= rw; return this; }
    
    public Vector4f add(final Vector4f r) { x += r.x; y += r.y; z += r.z; w += r.w; return this; }
    public Vector4f sub(final Vector4f r) { x -= r.x; y -= r.y; z -= r.z; w -= r.w; return this; }
    public Vector4f mul(final Vector4f r) { x *= r.x; y *= r.y; z *= r.z; w *= r.w; return this; }
    public Vector4f div(final Vector4f r) { x /= r.x; y /= r.y; z /= r.z; w /= r.w; return this; }
    
    public Vector4f copy() { return new Vector4f(x, y, z, w); }
    
    @Override
    public String toString() { return "Vec4[x=" + fmt.format(x) + ",y=" + fmt.format(y) + ",z=" + fmt.format(z) + ",w=" + fmt.format(w) + "]"; }
}
