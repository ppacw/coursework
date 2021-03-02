import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;



/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29 (2)
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.02;
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.16;    
    // The probability that a eagle will be created in any given grid position.
    private static final double EAGLE_CREATION_PROBABILITY = 0.02 + 1;
    // The probability that a rabbit will be created in any given grid position.
    private static final double EARTHWORM_CREATION_PROBABILITY = 0.16 + 2;
    // The probability that a rabbit will be created in any given grid position.
    private static final double OWL_CREATION_PROBABILITY = 0.02 + 2;
    // The probability that a rabbit will be created in any given grid position.
    private static final double TORTOISE_CREATION_PROBABILITY = 0.08 + 3;
    // The probability that a rabbit will be created in any given grid position.
    private static final double PLANT_CREATION_PROBABILITY = 0.15 + 3;

    // List of actors in the field.
    private List<Actor> actors;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // An instance of time
    private Time time;
    //plant step counter, only grow plant every 5 steps.
    private int plantCounter = 0;
    
    Random rand = new Random();
    

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        actors = new ArrayList<>();
        field = new Field(depth, width);
        time = new Time();

        //Color rabbitColor = new Color(105, 85, 55);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Rabbit.class, new Color(155, 85, 55));
        view.setColor(Fox.class, new Color(212, 112, 68));
        view.setColor(Eagle.class, new Color(50, 13, 13));
        view.setColor(Earthworm.class, new Color(255, 255, 0));
        view.setColor(Owl.class, new Color(68, 57, 101));
        view.setColor(Plant.class, new Color(0, 255, 51));

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(20);   // uncomment this to run more slowly
        }
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * fox, rabbit and hare.
     */
    public void simulateOneStep()
    {
        step++;
        plantCounter++;

        // Provide space for newborn actors.
        List<Actor> newActors = new ArrayList<>();        
        // Let all actors act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            if (!actor.isNocturnal() && !time.isNighttime() || actor.isNocturnal() && time.isNighttime())
            if(!(actor instanceof Plant)){
                if(! actor.isAlive()) {
                    it.remove();
                } else {
                    actor.act(newActors);
                }
            } else {
                if (actor instanceof Plant){
                    if(plantCounter % 2 == 0){ actor.act(newActors); }
                } 
            }
        }
        
        Location location = new Location(rand.nextInt(field.getDepth()), rand.nextInt(field.getWidth()));
        Plant plant = new Plant(field, location);
        actors.add(plant);
        
        
       

        // Add the newly born foxes and rabbits to the main lists.
        actors.addAll(newActors);

        time.timeIncrement();
        view.showStatus(step, field, time.getTimeString());
        
        if (time.isNighttime()){
            view.setEmptyColorGray();
        }
        if (!time.isNighttime()){
            view.setEmptyColorWhite();
        }
        //if (time.getMinutes() % 59 == 0){
         //   changeBrightness();
        //}
       }
    

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        populate();
        time.resetTime();
        // Show the starting state in the view.
        view.showStatus(step, field, time.getTimeString());
    }

    private Sex chooseSex(){
        int randomSexNum;
        randomSexNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        if(randomSexNum == 0){
            return Sex.MALE;
        }

        return Sex.FEMALE;
    }
    
    
    private void  changeBrightness()
    {
        float brightness = 1;
        //(-24 + time.getHours()) * 0.01f + 1
        if (time.getHours() > 22 || time.getHours() <= 2){
            brightness = 0.9f;
        }
        else if (time.getHours() > 2 && time.getHours() <= 6){
            brightness = 1.11111111111111111111111111f;
        }
           
        
        /**
        view.setColor(Rabbit.class, new Color(view.getColor(Rabbit.class).getRed()-n, view.getColor(Rabbit.class).getGreen()-n, view.getColor(Rabbit.class).getBlue()-n));
        view.setColor(Fox.class, new Color(view.getColor(Fox.class).getRed()-n, view.getColor(Fox.class).getGreen()-n, view.getColor(Fox.class).getBlue()-n));
        view.setColor(Hare.class, new Color(view.getColor(Hare.class).getRed()-n, view.getColor(Hare.class).getGreen()-n, view.getColor(Hare.class).getBlue()-n));
        view.setColor(Eagle.class, new Color(view.getColor(Eagle.class).getRed()-n, view.getColor(Eagle.class).getGreen()-n, view.getColor(Eagle.class).getBlue()-n));
        view.setColor(Earthworm.class, new Color(view.getColor(Earthworm.class).getRed()-n, view.getColor(Earthworm.class).getGreen()-n, view.getColor(Earthworm.class).getBlue()-n));
        view.setColor(Owl.class, new Color(view.getColor(Owl.class).getRed()-n, view.getColor(Owl.class).getGreen()-n, view.getColor(Owl.class).getBlue()-n));
        */ 
        
        if (time.isNighttime()){
            view.setColor(Rabbit.class, new Color(view.getColor(Rabbit.class).getRed()*brightness, view.getColor(Rabbit.class).getGreen()*brightness, view.getColor(Rabbit.class).getBlue()*brightness));
            view.setColor(Fox.class, new Color(view.getColor(Fox.class).getRed()*brightness, view.getColor(Fox.class).getGreen()*brightness, view.getColor(Fox.class).getBlue()*brightness));
            
            view.setColor(Eagle.class, new Color(view.getColor(Eagle.class).getRed()*brightness, view.getColor(Eagle.class).getGreen()*brightness, view.getColor(Eagle.class).getBlue()*brightness));
            view.setColor(Earthworm.class, new Color(view.getColor(Earthworm.class).getRed()*brightness, view.getColor(Earthworm.class).getGreen()*brightness, view.getColor(Earthworm.class).getBlue()*brightness));
            view.setColor(Owl.class, new Color(view.getColor(Owl.class).getRed()*brightness, view.getColor(Owl.class).getGreen()*brightness, view.getColor(Owl.class).getBlue()*brightness));
        }
    }
    
    
    /**
     * Randomly populate the field with foxes and rabbits.
     */

    private void populate()
    {
        Random rand = Randomizer.getRandom();
        int randomNum;
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                randomNum = ThreadLocalRandom.current().nextInt(0, 3 + 1);

                switch(randomNum){
                    case 0:
                    if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Fox fox = new Fox(true, field, location);
                        actors.add(fox);
                    } else if(rand.nextDouble() + 2 <= EARTHWORM_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Earthworm earthworm = new Earthworm(true, field, location);
                        actors.add(earthworm);
                    }
                   
                    
                    case 1:
                    if(rand.nextDouble() + 1  <= EAGLE_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Eagle eagle = new Eagle(true, field, location);
                        actors.add(eagle);
                    }
                    
                    case 2:
                    if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Rabbit rabbit = new Rabbit(true, field, location);
                        actors.add(rabbit);
                    } else if(rand.nextDouble() + 2 <= OWL_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Owl owl = new Owl(true, field, location);
                        actors.add(owl);
                    }
                    case 3:
                    if(rand.nextDouble() + 3 <= PLANT_CREATION_PROBABILITY) {
                        Location location = new Location(row, col);
                        Plant plant = new Plant(field, location);
                        actors.add(plant);
                        // else leave the location empty.
                    }
              }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
