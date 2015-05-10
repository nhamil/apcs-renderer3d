/**
 * 
 */
package nhamilton.game.graphics;

import nhamilton.game.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class Camera
{   
    public static final Vector4f Y_AXIS = new Vector4f(0, 1, 0, 0);
    
//TODO: Clean up

    private Vector4f pos;
    private Vector4f forward;
    private Vector4f up;
    
    public Camera() 
    {
        this(new Vector4f(0, 0, 0, 0), new Vector4f(0, 0,-1, 0), Y_AXIS.copy());
    }
    
    public Camera(Vector4f pos, Vector4f forward, Vector4f up) 
    {
        forward = forward.normalized();
        up = up.normalized();
        
        this.pos = pos;
        this.forward = forward;
        this.up = up;
    }
    
    public void move(final Vector4f dir, float amt) 
    {
        pos = pos.add(dir.mul(amt));
    }
    
    public Vector4f getLeft() 
    {
        return forward.cross(up).normalized();
    }
    
    public Vector4f getRight() 
    {
        return up.cross(forward).normalized();
    }
    
    public void rotateY(float angle) 
    {
        Vector4f hAxis = Y_AXIS.cross(forward).normalized();
        
        forward = forward.rotate(Y_AXIS, angle).normalized();
        
        up = forward.cross(hAxis).normalized();
    }
    
    public void rotateX(float angle) 
    {
        Vector4f hAxis = Y_AXIS.cross(forward).normalized();
        
        forward = forward.rotate(hAxis, angle);
        
        up = forward.cross(hAxis).normalized();
    }

    public Vector4f getPosition()
    {
        return pos;
    }

    public void setPosition(Vector4f pos)
    {
        this.pos = pos;
    }
    
    public Vector4f getForwardNoY() 
    {
        Vector4f f = forward.copy();
        f.setY(0);
        return f.normalized();
    }

    public Vector4f getForward()
    {
        return forward;
    }

    public void setForward(Vector4f forward)
    {
        this.forward = forward;
    }

    public Vector4f getUp()
    {
        return up;
    }

    public void setUp(Vector4f up)
    {
        this.up = up;
    }
}
