package model;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class Interval
{
    String timeString;
    Calendar date = Calendar.getInstance();
    double temp, wind, rain;
    
    public Interval(String timeString, double temp, long wind, String rain)
    {
        this.timeString = timeString;
        date.clear();
        date.set(year(), 
                month(), 
                day(), 
                hourOfDay(), 
                minute());
        
        this.temp = temp; //comes as double
        this.wind = wind; //comes as long
        
        if(rain.contains("[0-1]+") == true)
           this.rain = Double.valueOf(rain); //comes as String
      
        else
           this.rain = 0;
    }
    
    //Methods to set date
    public int year()
    { return Integer.valueOf(timeString.substring(0, 4)); }    
    public int month()
    { return Integer.valueOf(timeString.substring(4, 6)); }    
    public int day()
    { return Integer.valueOf(timeString.substring(6, 8)); }    
    public int hourOfDay()
    { return Integer.valueOf(timeString.substring(8, 10)); }    
    public int minute()
    { return Integer.valueOf(timeString.substring(10, 12)); }
    
    //Formatters
    public String strTemp()
    {return Double.toString(temp);}    
    public String strWind()
    {return Double.toString(wind);}    
    public String strRain()
    {return Double.toString(rain);}
    
    public String getTemp()
    {
        return strTemp() + " °C";
    }
    
    public String getWind()
    {
        return strWind() + "km/h";
    }
    
    public String getRain()
    {
        return strRain() + "mm";
    }
}
