import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a predator.
 * Predatores age, move, eat preys, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class PredatorParent extends Animal implements Predator
{
    // Characteristics shared by all predatores (class variables).

    // The age at which a predator can start to breed.
    private static int BREEDING_AGE;
    // The age to which a predator can live.
    private static int MAX_AGE;
    // The likelihood of a predator breeding.
    private static  double BREEDING_PROBABILITY;
    // The maximum number of births.
    private static int MAX_LITTER_SIZE;
    // The food value of a single prey. In effect, this is the
    // number of steps a predator can go before it has to eat again.
    private static int PREY_FOOD_VALUE;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    // The predator's age.
    private int age;
    // The predator's food level, which is increased by eating prey.
    private int foodLevel;

    //private boolean nocturnal;

    /**
     * Create a predator. A predator can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the predator will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    protected PredatorParent(boolean randomAge, Field field, Location location, int BREEDING_AGE, int MAX_AGE, double BREEDING_PROBABILITY, int MAX_LITTER_SIZE, int PREY_FOOD_VALUE)
    {
        super(field, location);
        this.BREEDING_AGE = BREEDING_AGE;
        this.MAX_AGE = MAX_AGE;
        this.BREEDING_PROBABILITY = BREEDING_PROBABILITY;
        this.MAX_LITTER_SIZE = MAX_LITTER_SIZE;
        this.PREY_FOOD_VALUE = PREY_FOOD_VALUE;
        //this.nocturnal = nocturnal;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PREY_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = PREY_FOOD_VALUE;
        }
    }

    /**
     * This is what the predator does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newPredatores A list to return newly born predatores.
     */
    public void act(List<Animal> newPredators)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newPredators);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Increase the age. This could result in the predator's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }

    /**
     * Make this predator more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for preys adjacent to the current location.
     * Only the first live prey is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Prey) {
                Prey prey = (Prey) animal;
                if(prey.isAlive()) { 
                    prey.setDead();
                    foodLevel = PREY_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }

    /**
     * Check whether or not this predator is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPredatores A list to return newly born predators.
     */
    abstract void giveBirth(List<Animal> newPredatores);

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * @return animal's gender;
     */
    public Sex getSex()
    {
        return super.getSex();
    } 

    /**
     * @return if an animal is nocturnal or not;
     
    public boolean isNocturnal()
    {
        return nocturnal;
    } 
    */
    
    /**
     * A prey can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        if(age >= BREEDING_AGE){
            Field field = getField();
            List<Location> adjacent = field.adjacentLocations(getLocation());
            Iterator<Location> it = adjacent.iterator();
            while(it.hasNext()) {
                Location where = it.next();
                Object animal = field.getObjectAt(where);
                if(animal instanceof Predator) {
                    Predator predator = (Predator) animal;
                    if(predator.getSex().equals(this.getSex())) { 
                        return true;
                    }
                }

            }

        }
        return false;
    }
}
