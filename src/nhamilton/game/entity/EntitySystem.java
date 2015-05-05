package nhamilton.game.entity;

import java.util.HashMap;

public class EntitySystem
{
    private static EntitySystem instance;
    
    private HashMap<String, Entity> entityRegister;
    
    private EntitySystem() 
    {
        entityRegister = new HashMap<String, Entity>();
    }
    
    public static EntitySystem getInstance() 
    {
        if(instance == null) 
            instance = new EntitySystem();
        
        return instance;
    }
    
    public void registerEntity(Entity e) 
    {
        
    }
}
