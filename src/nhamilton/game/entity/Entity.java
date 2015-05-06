package nhamilton.game.entity;

/**
 * 
 * Main entity class. This will eventually contain a reference to an EntityType,
 * as well the entity's personal stats.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Entity
{
    private float x, y;
    private int hp;
    
    private EntityType type;
    
    /**
     * Constructor, only takes a name of an EntityType.
     * 
     * @param name Name of EntityType
     */
    public Entity(String name)
    {
        type = EntityManager.getInstance().getEntityType(name);
        
        if(type == null)
            throw new RuntimeException("ERROR: EntityType '" + name + "' does not exist!");
        
        validate();
    }
    
    public Entity(EntityType e) 
    {
        type = e;
        
        validate();
    }
    
    public EntityType getEntityType() 
    {
        return type;
    }
    
    /**
     * Returns the x position of the entity.
     * 
     * @return X position
     */
    public float getPosX() 
    {
        return x;
    }
    
    //TODO: Finish comments, it just takes soooooo long
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
    
    public int getHP() 
    {
        return hp;
    }
    
    public void setHP(int nHP) 
    {
        hp = nHP;
        validateHP();
    }
    
    public void changeHP(int dHP) 
    {
        hp += dHP;
        validateHP();
    }
    
    private void validate() 
    {
        if(type == null)
            throw new RuntimeException("ERROR: Does not contain EntityType!");
        
        validateHP();
    }
    
    private void validateHP() 
    {
        if(hp < 0) hp = 0;
        if(hp > type.getMaxHP()) hp = type.getMaxHP();
    }
}
