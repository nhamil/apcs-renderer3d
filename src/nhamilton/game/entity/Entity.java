package nhamilton.game.entity;

import nhamilton.game.Controller;

/**
 * 
 * Main entity class. This will eventually contain a reference to an EntityType,
 * as well the entity's personal stats.
 * 
 * @author Nicholas Hamilton
 *
 */

public abstract class Entity
{
    private float      x, y;
    private float      dx, dy;
    private float      w, h;
    private int        id;
    private boolean    onGround;
    private Controller controller;
    
    public Entity(float x, float y, float w, float h, Controller controller)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.controller = controller;
        
        dx = dy = 0.0f;
        
        id = EntityManager.getInstance().addEntity(this);
    }
    
    public abstract String getName();
    
    public abstract int getColorCode();
    
    public void update()
    {
        if(controller != null) 
        {
            if(controller.getJump() && onGround) 
            {
                changeVelY(10 / 60f);
            }
        }
        
        x += dx;
        y += dy;
        
        onGround = false;
    }
    
    public Controller getController() { return controller; }
    public void setController(Controller c) { controller = c; }
    
    public int getID() { return id; }
    
    public float getWidth() { return w; }
    public float getHeight() { return h; }
    
    public void setWidth(float w) { this.w = w; }
    public void setHeight(float h) { this.h = h; }
    
    public float getPosX() { return x; }
    public float getPosY() { return y; }
    public float getVelX() { return dx; }
    public float getVelY() { return dy; }
    
    public void setPosX(float nx) { x = nx; }
    public void setPosY(float ny) { y = ny; }
    public void setVelX(float ndx) { dx = ndx; }
    public void setVelY(float ndy) { dy = ndy; }
    
    public void changePosX(float dx) { x += dx; }
    public void changePosY(float dy) { y += dy; }
    public void changeVelX(float ddx) { dx += ddx; }
    public void changeVelY(float ddy) { dy += ddy; }
    
    public boolean isOnGround() { return onGround; }
    public void setOnGround(boolean on) { onGround = on; }
}
