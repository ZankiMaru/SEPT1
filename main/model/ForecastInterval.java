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

	public Calendar dateTime = Calendar.getInstance();
	public double temp, wind, rain;
	
	public ForecastInterval(long timestamp, double temperature, double windspeed, double precipIntensity){
		this.dateTime.setTimeInMillis(timestamp);
		this.temp = temperature;
		this.wind = windspeed;
		this.rain = precipIntensity;
		this.timex = timestamp;
	}
	
	public void check(){
		SimpleDateFormat format1 = new SimpleDateFormat("dd/MM - yyyy");
//		System.out.println(format1.format(this.dateTime.getTime()));
		System.out.println(timex);
		System.out.println(temp + "c | " + wind + " | " + rain);
	}
	
	public String getHour(){
		/*Get hour of interval in string format*/
		int hour;
		String hourString;
		hour = this.dateTime.get(Calendar.HOUR_OF_DAY);
		hourString = String.valueOf(hour);
		return hourString;
	}
	
	public String getMonth(){
		/*Get month of interval in string format*/
		int month;
		String monthString;
		month = this.dateTime.get(Calendar.MONTH);
		month += 1;
		monthString = String.valueOf(month);
		return monthString;
	}
	
	public String getDate(){
		/*Get date of interval in string format*/
		int date;
		String dateString;
		date = this.dateTime.get(Calendar.DATE);
		dateString = String.valueOf(date);
		return dateString;
	}
	
	public String getYear(){
		/*Get year of interval in string format*/
		int year;
		String yearString;
		year = this.dateTime.get(Calendar.YEAR);
		yearString = String.valueof(year);
		return yearString;
	}
	
}