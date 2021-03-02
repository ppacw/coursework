import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.lang.NullPointerException;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public abstract class PreyParent extends Animal implements Prey
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private  int BREEDING_AGE;
    // The age to which a rabbit can live.
    private  int MAX_AGE;
    // The likelihood of a rabbit breeding.
    private double BREEDING_PROBABILITY;
    // The maximum number of births.
    private int MAX_LITTER_SIZE;
    // Number of steps it can go without eating
    private int PLANT_FOOD_VALUE;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // Individual characteristics (instance fields).
    
    // The rabbit's age.
    private int age;
    // animals food level
    protected int foodLevel;


    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public PreyParent(boolean randomAge, Field field, Location location, int BREEDING_AGE, int MAX_AGE, double BREEDING_PROBABILITY, int MAX_LITTER_SIZE, int PLANT_FOOD_VALUE)
    {
        super(field, location);
        this.BREEDING_AGE = BREEDING_AGE;
        this.MAX_AGE = MAX_AGE;
        this.BREEDING_PROBABILITY = BREEDING_PROBABILITY;
        this.MAX_LITTER_SIZE = MAX_LITTER_SIZE;
        this.PLANT_FOOD_VALUE = 8;

        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
            foodLevel = rand.nextInt(PLANT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevel = PLANT_FOOD_VALUE;
        }
    }
    
    public int getAge(){
        return age;
    }
    
    /**
     * This is what the predator does most of the time: it hunts for
     * preys. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newPredatores A list to return newly born predatores.
     */
    public void act(List<Actor> newPrey)
    {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newPrey);            
            // Move towards a source of food if found.
            Location newLocation = findPlants();
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
     * Make this prey more hungry. This could result in the predator's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for plants adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    public Location findPlants()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object object = field.getObjectAt(where);
            if(object instanceof Plant) {
                Plant plant = (Plant) object;
                if(plant.isAlive()) { 
                    plant.setDead();
                    foodLevel = PLANT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
    public void setPlantFoodValue(int n){
        PLANT_FOOD_VALUE += n;
    }
    
    public boolean isAlive(){
        return super.isAlive();
    }
    
    public void setDead(){
        super.setDead();
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
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    abstract public void giveBirth(List<Actor> newRabbits);
    
    

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
    
    public Sex getSex(){
        return super.getSex();
    }

    /**
     * A prey can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    abstract public boolean canBreed();
    
}
