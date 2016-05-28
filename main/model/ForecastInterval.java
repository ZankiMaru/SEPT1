package main.model;
import java.util.Calendar;
import java.io.*;
import java.text.SimpleDateFormat;

public class ForecastInterval
{
	/*intervals from DarkSkyAPI 
	starts at 7am today and ends at 7am 2 days later (every hour)
	intervals from OpenWeather
	starts from 9am today and ends at 9pm 4 days later (every 3 hours)*/
	
	public long tstamp;
	public Calendar dateTime = Calendar.getInstance();
	public double temp, wind, rain;
	
	public ForecastInterval(long timestamp, double temperature, double windspeed, double precipIntensity){
		this.dateTime.setTimeInMillis(timestamp);
		this.temp = temperature;
		this.wind = windspeed;
		this.rain = precipIntensity;
		this.tstamp = timestamp;
	}
	
	public void check(){
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM - yyyy");
		System.out.println("Date: "+this.getDate()+"/"+this.getMonth()+"/"+this.getYear()+" Hour: "+this.getHour());
		System.out.println("Timestamp: " + tstamp);
		System.out.println("Measurements: " + temp + " | " + wind + " | " + rain);
	}
	
	public int getHour(){
		/*Get hour of interval in string format*/
		int hour;
		hour = this.dateTime.get(Calendar.HOUR_OF_DAY);
		return hour;
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