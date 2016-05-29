package main.model;
import java.text.DecimalFormat;
import java.util.Calendar;


public class ForecastDayData
{
    public Double min, max, wind, rain;
	public Calendar dateTime;
    
    public ForecastDayData()
    {
        
    }
    public ForecastDayData(Calendar dateTime, double min, double max, double wind, double rain)
    {
		this.dateTime = dateTime;
        this.min = min;
        this.max = max;
        this.wind = wind;
        this.rain = rain;
    }
    
    public String strMin()
    {
    	return new DecimalFormat("#.#").format(min);
    }
    
    public String strMax()
    {
    	return new DecimalFormat("#.#").format(max);
    }
    
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
	
	public int getMonth(){
		/*Get month of interval in string format*/
		int month;
		month = this.dateTime.get(Calendar.MONTH);
		month += 1;
		return month;
	}
	
	public int getDate(){
		/*Get date of interval in string format*/
		int date;
		date = this.dateTime.get(Calendar.DATE);
		return date;
	}
	
	public int getYear(){
		/*Get year of interval in string format*/
		int year;
		year = this.dateTime.get(Calendar.YEAR);
		return year;
	}
}
