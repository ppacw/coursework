import java.util.List;
import java.util.Random;

public class Hare extends PreyParent
{
   // Characteristics shared by all rabbits (class variables).

   // The age at which a rabbit can start to breed.
   private static final int BREEDING_AGE = 5;
   // The age to which a rabbit can live.
   private static final int MAX_AGE = 40;
   // The likelihood of a rabbit breeding.
   private static final double BREEDING_PROBABILITY = 0.12;
   // The maximum number of births.
   private static final int MAX_LITTER_SIZE = 4;
   // A shared random number generator to control breeding.
   private static final Random rand = Randomizer.getRandom();
    
   // Individual characteristics (instance fields).
    
   // The rabbit's age.
   private int age;

   /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Hare(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location, BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    

   /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void giveBirth(List<Animal> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = super.breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Hare young = new Hare(false, field, loc);
            newRabbits.add(young);
        }
    }
        

}