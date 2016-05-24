package main.model;
import java.util.Calendar;

public class ForecastInterval
{
	String timeString;
    Calendar date = Calendar.getInstance();
    double temp, wind, rain;

    public ForecastInterval(String timeString, double temp, long wind, String rain)
    {
        this.timeString = timeString;
        date.clear();
        date.set(timeString);
        
        this.temp = temp; //comes as double
        this.wind = wind; //comes as long
        
        if(rain.contains("[0-1]+") == true)
           this.rain = Double.valueOf(rain); //comes as String
      
        else
           this.rain = 0;
    }
}