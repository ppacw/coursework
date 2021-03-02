import java.util.List;
/**
 * A class representing an existent object in the field.
 * 
 * @author Aadam Sheikh
 * @version 2021-03-01 (1.0)
 */


public abstract class Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The entities field.
    private Field field;
    // The entities position in the field.
    private Location location;
    
    private boolean nocturnal;
    
    
    /**
     * Create a new entity at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * 
     */
    public Actor(Field field, Location location){
        this.field = field;
        setLocation(location);
        alive = true;
    }
    
    public void setNocturnal()
    {
        nocturnal = true;
    }
    
    public boolean isNocturnal()
    {
        return nocturnal;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Actor> newActors);
    
    /**
     * Place the entity at the new location in the given field.
     * @param newLocation The entities new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Check whether the entity is alive or not.
     * @return true if the entity is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }
    
    /**
     * Indicate that the entity is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    public Field getField()
    {
        return field;
    }
    
}
