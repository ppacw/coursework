
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    private int hours;
    private int minutes;
    /**
     * Constructor for objects of class Time
     */
    public Time()
    {
        hours = 0;
        minutes = 0;
    }
    
    /**
     * Increment time by a minute.
     * If @minutes counter reaches 60, reset it to 0 and increment @hours
     * If @hours counter reaches 24, reset it to 0.
     */
    public void timeIncrement()
    {
        minutes += 5;
        if (minutes % 60 == 0){
            minutes = 0;
            hours++;
        }
        if (hours % 24 == 0){
            hours = 0;
        }
    }
    
    /**
     * @return the current time as a String in the HH:MM time format
     */
    public String getTimeString()
    {
        String hoursString = "" + hours;
        String minutesString = "" + minutes;
        if (hours < 10){
             hoursString = "0" + hours;
        }
        if (minutes < 10){
            minutesString = "0" + minutes;
        }
        return hoursString + ":" + minutesString;
    }
    
    public void resetTime()
    {
        hours = 8;
        minutes = 0;
    }
    
    public int getMinutes()
    {
        return minutes;
    }
    
    public int getHours()
    {
        return hours;
    }
    
    public boolean isNighttime()
    {
        if (hours < 6 || hours > 22){
            return true;
        }
        else{
            return false;
        }
    }
}
