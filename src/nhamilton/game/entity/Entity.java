package nhamilton.game.entity;

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
    private float x, y;
    private float dx, dy;
    private float w, h;
    private int id;
    
    public Entity(float x, float y, float w, float h) 
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        dx = dy = 0.0f;
        
        id = EntityManager.getInstance().addEntity(this);
    }
    
    public abstract String getName();
    public abstract int getColorCode();
    
    public void update() 
    {
        x += dx;
        y += dy;
    }
    
    public int getID() 
    {
        return id;
    }
    
    public float getWidth() 
    { 
        return w;
    }
    
    public void setWidth(float w) 
    {
        this.w = w;
    }
    
    public float getHeight() 
    {
        return h;
    }
    
    public void setHeight(float h) 
    {
        this.h = h;
    }
    
    public float getPosX() 
    {
        return x;
    }
    
    public void setPosX(float nx) 
    {
        x = nx;
    }
    
    public void changePosX(float dx) 
    {
        x += dx;
    }
    
    public float getPosY() 
    { 
        return y;
    }
    
    public void setPosY(float ny) 
    {
        y = ny;
    }
    
    public void changePosY(float dy) 
    {
        y += dy;
    }
    
    public float getVelX() 
    {
        return dx;
    }
    
    public void setVelX(float ndx) 
    {
        dx = ndx;
    }
    
    public void changeVelX(float ddx) 
    {
        dx += ddx;
    }
    
    public float getVelY() 
    {
        return dy;
    }
    
    public void setVelY(float ndy) 
    {
        dy = ndy;
    }
    
    public void changeVelY(float ddy) 
    {
        dy += ddy;
    }
}
