/**
 * 
 */
package com.tenikkan.abacus.math;

/**
 * @author Nicholas Hamilton
 *
 */
public class Quaternion
{   
    private float x;
    private float y;
    private float z;
    private float w;
    
    public Quaternion(float w, float x, float y, float z)
    {
        set(w, x, y, z);
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public float getZ() { return z; }
    public float getW() { return w; }
    
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setZ(float z) { this.z = z; }
    public void setW(float w) { this.w = w; }
    
    public void set(float w, float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Quaternion mul(Quaternion r)
    {
        float w_ = w * r.w - x * r.x - y * r.y - z * r.z;
        float x_ = w * r.x + x * r.w + y * r.z - z * r.y;
        float y_ = w * r.y + y * r.w + z * r.x - x * r.z;
        float z_ = w * r.z + z * r.w + x * r.y - y * r.x;
        
        return new Quaternion(w_, x_, y_, z_);
    }
    
    public Quaternion mul(Vector4f r)
    {
        float w_ =-x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = w * r.getZ() + x * r.getY() - y * r.getX();
        
        return new Quaternion(w_, x_, y_, z_);
    }
    
    public Quaternion conjugate() { return new Quaternion(w, -x, -y, -z); }
    
    public Quaternion copy() { return new Quaternion(w, x, y, z); }
    
    public String toString() { return w + " + " + x + "i + " + y + "j + " + z + "k"; }
}
