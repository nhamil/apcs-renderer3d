package nhamilton.game.entity;

import java.util.ArrayList;

/**
 * 
 * Manages all entities as well as entity types.
 * 
 * @author Nicholas Hamilton
 *
 */

public class EntityManager
{
    private static EntityManager instance;
    
    private ArrayList<Entity> entityRegister;
    private int nextID;
    
    private EntityManager()
    {
        entityRegister = new ArrayList<Entity>();
        nextID = 0;
    }
    
    public static EntityManager getInstance()
    {
        if(instance == null)
            instance = new EntityManager();
        
        return instance;
    }
    
    public int addEntity(Entity e)
    {
        entityRegister.add(nextID, e);
        return nextID++;
    }
    
    public Entity getEntity(int id)
    {
        if(id < 0 || id >= entityRegister.size())
            return null;
        return entityRegister.get(id);
    }
}
