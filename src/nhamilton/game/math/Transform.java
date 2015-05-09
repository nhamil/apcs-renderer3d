/**
 * 
 */
package nhamilton.game.math;

/**
 * @author Nicholas Hamilton
 *
 */
public class Transform
{   
    private Matrix4f translate;
    private Matrix4f rotate;
    private Matrix4f scale;
    private Matrix4f persp;
    
    public Transform() 
    {
        translate = new Matrix4f().initTranslation(0, 0, 0);
        rotate = new Matrix4f().initRotation(0, 0, 0);
        scale = new Matrix4f().initScale(1, 1, 1);
        persp = new Matrix4f().initPerspective(70.0f, 2.0f, 0.1f, 100f).initIdentity();
    }
    
    public void setTranslation(float tx, float ty, float tz) 
    {
        translate.initTranslation(tx, ty, tz);
    }
    
    public void setRotation(float rx, float ry, float rz) 
    {
        rotate.initRotation(rx, ry, rz);
    }
    
    public void setScale(float sx, float sy, float sz) 
    {
        scale.initScale(sx, sy, sz);
    }
    
    public void setPerspective(float fov, float ratio, float zNear, float zFar) 
    {
        persp.initPerspective(fov, ratio, zNear, zFar);
    }
    
    public Matrix4f getMatrix() 
    {
        return persp.mul(scale.mul(rotate).mul(translate));
    }
}
