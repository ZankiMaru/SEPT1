package main.model;
import java.text.DecimalFormat;


public class DayData
{
    public Double min, max, wind, rain;
    
    public DayData()
    {
        
    }
    public DayData(double min, double max, double wind, double rain)
    {
        this.min = min;
        this.max = max;
        this.wind = wind;
        this.rain = rain;
    }
    
    public String strMin()
    {return Double.toString(min);}
    
    public String strMax()
    {return Double.toString(max);}
    
    public String strRain()
    {return Double.toString(rain);}
    
    
    public String getMinMax()
    {
        return strMin() + "/" + strMax() + " °C";
    }
    
    public String getWind()
    {
        return new DecimalFormat("#.#").format(wind) + "km/h";
    }
    
    public String getRain()
    {
        return strRain() + "mm";
    }
    
    public boolean isNotNull()
    {
        if (min != null && max != null && wind != null && rain != null)
            return true;
        else
            return false;
    }
}
