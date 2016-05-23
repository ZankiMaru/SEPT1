package main.model;
import java.util.Calendar;
import java.io.*;

public class ForecastInterval
{
	/*intervals from DarkSkyAPI 
	starts at 7am today and ends at 7am 2 days later (every hour)
	intervals from OpenWeather
	starts from 9am today and ends at 9pm 4 days later (every 3 hours)*/
	
	Calendar dateTime = Calendar.getInstance();
	double temp, wind, rain;
	
	public ForecastInterval(long timestamp, double temperature, double windspeed, double precipIntensity){
		this.dateTime.setTimeInMillis(timestamp);
		this.temp = temperature;
		this.wind = windspeed;
		this.rain = precipIntensity;
	}
	
	public String getHour(){
		/*Get hour of interval in string format*/
		int hour;
		String hourString;
		hour = this.dateTime.get(Calendar.HOUR_OF_DAY);
		hourString = String.valueof(hour);
		return hourString;
	}
	
	public String getMonth(){
		/*Get month of interval in string format*/
		int month;
		String hourString;
		month = this.dateTime.get(Calendar.MONTH);
		month += 1;
		monthString = String.valueof(month);
		return monthString;
	}
	
	public String getDate(){
		/*Get hour of interval in string format*/
		int date;
		String dateString;
		date = this.dateTime.get(Calendar.DATE);
		dateString = String.valueof(date);
		return dateString;
	}
	
}