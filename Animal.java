import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

//tests
/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
//test comment
public abstract class Animal extends Actor
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // The animal's gender
    private Sex sex;
    
    private boolean nocturnal;
    // Decides if the animal has the insomnia disease, which makes it 
    // stay up during the night, act twice as slow and also die after a given amount of time.
    private boolean diseased;
    private int deathTimer;
        
    /**
     * Create a new animal at location in field with sex(Male/Female).
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param sex The gender of the animal
     */
    public Animal(Field field, Location location)
    {
        //Randomises the sex of an animal and stores it in its field.
        super(field, location);
        int randomSexNum;
        randomSexNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        if(randomSexNum == 0){
            sex = Sex.MALE;
        } else { 
            sex = Sex.FEMALE; }

        
        nocturnal = false;
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
     * 
     * @return animal's gender;
     */
    protected Sex getSex()
    {
        return sex;
    } 
    
    
    
    

    

    

    
}
