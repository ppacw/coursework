import java.util.List;
import java.util.Random;
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
    // Decides if the animal is active at daytime or nighttime
    private boolean nocturnal;
    // Decides if the animal has the insomnia disease, which makes it 
    // stay up during the night, act twice as slow and also die after a given amount of time.
    private boolean diseased;
    private int deathTimer;
    private Random rand = new Random();
    
    
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
        
        deathTimer = -1;
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
     * Make this actor act - that is: make it do
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
    
    public void catchDisease()
    {
        diseased = true;
        deathTimer = 12;
    }

    public boolean isDiseased()
    {
        return diseased;
    }

    public void decrementDeathTimer()
    {
        deathTimer--;
    }

    public int getDeathTimer()
    {
        return deathTimer;
    }
}
