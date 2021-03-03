import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Write a description of class Weather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Weather
{
    // instance variables - replace the example below with your own
    private Time time;
    //a boolean showing the weather dry/foggy. If foggy animals will be slowed down and move every 2 ticks.
    private boolean foggy;

    
    public Weather(Time time)
    {
        this.time = time;
        foggy = false;
    }
    
    public void changeWeather(){
       int rand;
       rand = ThreadLocalRandom.current().nextInt(0, 1 + 1);
       if(rand == 0){
            foggy = true;
        } else {
        foggy = false;
    }
    }
    
    /**
     * @return the current time as a String in the HH:MM time format
     */
    public String getWeatherString()
    {
        String weather = "Weather: ";
        if(foggy){
            return weather += "Foggy";
        } else {
            return weather += "Clear/Dry";
        }
    }
    
    public boolean isFoggy(){
        return foggy;
    }
    
    

    
}
