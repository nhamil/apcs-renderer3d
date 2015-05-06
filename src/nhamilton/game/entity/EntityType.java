package nhamilton.game.entity;

/**
 * 
 * Base stat class for entities.
 * 
 * @author Nicholas Hamilton
 *
 */

public class EntityType
{
    private String name;
    private int id;
    
    private int maxHP = 0;
    private int startHP = 0;
    
    private float width = 1.0f;
    private float height = 1.0f;
    
    /**
     * Constructor, only requires a name. The rest is altered after
     * construction.
     * 
     * @param name Name of EntityType
     */
    public EntityType(String name)
    {
        this.name = name;
        id = EntityManager.getInstance().addEntityType(this);
    }
    
    /**
     * Returns name of EntityType.
     * 
     * @return Name of EntityType
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns ID of EntityType.
     * 
     * @return ID of EntityType
     */
    public int getID()
    {
        return id;
    }
    
    public float getWidth() 
    {
        return width;
    }
    
    public void setWidth(float w) 
    {
        width = w;
    }
    
    public float getHeight() 
    {
        return height;
    }
    
    public void setHeight(float h) 
    {
        height = h;
    }
    
    /**
     * Returns the maximum HP that entities of this type can have.
     * 
     * @return Maximum HP
     */
    public int getMaxHP()
    {
        return maxHP;
    }
    
    /**
     * Sets the maximum HP for the entity type.
     * 
     * @param max Maximum HP
     */
    public void setMaxHP(int max) 
    {
        maxHP = max;
    }
    
    /**
     * Returns the HP that entities of this type start with.
     * 
     * @return Starting HP
     */
    public int getStartHP()
    {
        return startHP;
    }
    
    /**
     * Sets the starting point of the entity type.
     * 
     * @param start Starting HP
     */
    public void setStartHP(int start) 
    {
        startHP = start;
    }
}
