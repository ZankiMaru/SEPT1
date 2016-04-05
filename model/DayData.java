package model;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;


public class DayData
{
    double min, max, wind, rain;
    
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
}
