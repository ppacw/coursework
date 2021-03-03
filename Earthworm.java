import java.util.List;
import java.util.Random;
import java.util.Iterator;

public class Earthworm extends PreyParent
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // Max steps rabbit can go without food
    private static final int PLANT_FOOD_VALUE = 15;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();


    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Earthworm(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, PLANT_FOOD_VALUE);

    }

    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Actor> newEarthworms)
    {
        // New earthworms are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = super.breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Earthworm young = new Earthworm(false, field, loc);
            newEarthworms.add(young);
        }
    }

    /**
     * A prey can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    public boolean canBreed()
    {
        if(getAge() >= BREEDING_AGE){
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object animal = field.getObjectAt(where);
                if(animal instanceof Earthworm) {
                    Earthworm earthworm = (Earthworm) animal;
                    if(!earthworm.getSex().equals(this.getSex())) { 
                        return true;
                  }
                }

            }
       }
       return false;
    }

    
        
}
