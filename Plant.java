import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
//test comment
public class Plant extends Actor
{
   // Whether the plant is alive or not.
   private boolean alive;
   // The animal's field.
   private Field field;
   // The animal's position in the field.
   private Location location;
   //plant growth probability 
   private static final double GROWTH_PROBABILITY = 0.25;
   //plant growth probability 
   private static final int MAX_GROWTH_RATE = 1;
   // The age to which a rabbit can live.
   private static final int MAX_AGE = 5;
   // A shared random number generator to control breeding.
   private static final Random rand = Randomizer.getRandom();
   
   
   // The plant's age.
   private int age;


   /**
     * Create a new animal at location in field with sex(Male/Female).
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param sex The gender of the animal
     */
   public Plant(Field field, Location location)
   {
       super(field, location);
   }
   
   /**
     * Increase the age.
     * This could result in the rabbit's death.
     */
   private void incrementAge()
   {
       age++;
       if(age > MAX_AGE) {
           setDead();
       }
   }
    
   
    
   /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Actor> newPlants)
    {
        incrementAge();
        if(isAlive()) {
            grow(newPlants);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
            
        }
   
   /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected boolean growthPlant()
    {
        if(rand.nextDouble() <= GROWTH_PROBABILITY) {
            return true;
        }
        
        return false;
    }
   
        
        
   
    

   /**
     * Check whether or not this plant is to grow at this step.
     * New plants will be made into free adjacent locations.
     * @param newPlants A list to return newly grown plants.
     */
    public void grow(List<Actor> newPlants)
    {
        // New plants grow in adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int growth = 1;
        for(int b = 0; b < growth && free.size() > 0; b++) {
            if(growthPlant()){
                Location loc = free.remove(0);
                Plant plant = new Plant(field, loc);
                newPlants.add(plant);
            }
            
        }
    }
    
   
    

}

