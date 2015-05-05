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
    
    private ArrayList<EntityType> entityTypeRegister;
    private int nextID;
    
    private EntityManager()
    {
        entityTypeRegister = new ArrayList<EntityType>();
        nextID = 0;
    }
    
    /**
     * Returns the singleton instance of the class.
     * 
     * @return Instance of EntityManager
     */
    public static EntityManager getInstance()
    {
        if(instance == null)
            instance = new EntityManager();
        
        return instance;
    }
    
    /**
     * Registers an EntityType so it can be used to create new entities. The
     * method returns the ID of the entity type.
     * 
     * @param e EntityType object
     * @return ID of the EntityType
     */
    public int addEntityType(EntityType e)
    {
        entityTypeRegister.add(nextID, e);
        return nextID++;
    }
    
    /**
     * Returns a registered EntityType with the ID 'id'.
     * 
     * @param id ID of the EntityType
     * @return EntityType with ID of 'id'
     */
    public EntityType getEntityType(int id)
    {
        if(id < 0 || id >= entityTypeRegister.size())
            return null;
        return entityTypeRegister.get(id);
    }
    
    /**
     * Returns a registered EntityType with the name 'name'.
     * 
     * @param name Name of the EntityType
     * @return EntityType with name of 'name'
     */
    public EntityType getEntityType(String name)
    {
        for(EntityType e : entityTypeRegister)
            if(e.getName().equalsIgnoreCase(name))
                return e;
        return null;
    }
}
