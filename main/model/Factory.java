package main.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Factory {

   /* Calling openweather Data will return a JSONArray with structure like
    * - dt (unix date)
    *    - main
    *       - temp
    *       - etc
    *    - weather
    *       - id
    *       - etc
    *    - clouds
    *       - all
    *    - wind
    *       - speed
    *       - deg
    *    - snow
    *       -
    *    - sys
    *       - pod
    *    - dt_txt (date as text)
    *    */
   
	public JSONArray getDataOpenweather (double lat, double lon)
	{
		String siteUrl = "http://api.openweathermap.org/data/2.5/forecast?lat="+lat+"&lon="+lon+"&APPID=73649a79abc2d1c4c7f8ef94b656c69d&units=metric";
		
		JSONParser parser = new JSONParser();
        String text = "";
        /* First, connect to the website using an url and pass the whole page
         * as a string. Using StringBuilder to remove all the extra characters
         * and new line character to compile the JSON.*/
        try
        {
            URL oracle = new URL(siteUrl);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String x = "";
            StringBuilder inputLine = new StringBuilder();
            
            while ((x = in.readLine()) != null) {       
                inputLine.append(x);
            }   
            text = inputLine.toString();
        }
        catch (MalformedURLException e1){
            e1.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }   
        
        /* Next is to parse the whole page string as a JSONObject and extract
         * the needed data. In this example, it prints the name of the site 
         * and the weather at every half hour. */
        try{
            Object obj = parser.parse(text);
            JSONObject result = (JSONObject)obj;            
            JSONArray datas = (JSONArray) result.get("list");
            return datas;
            
        }catch(ParseException pe){
            System.out.println(pe);
        }
        return null;
	}
	
	/* Calling data from Forecast.io will return JSON array with this structure.
	 * - time (unix time)
	 * - summary
    * - icon
    * - precipIntensity
    * - precipProbability
    * - precipType
    * - precipAccumulation
    * - temperature
    * - apparentTemperature
    * - dewPoint
    * - humidity
    * - windSpeed
    * - windBearing
    * - cloudCover
    * - pressure
    * - ozone */
	
	public JSONArray getDataForecast (double lat, double lon)
	{
		String siteUrl = "https://api.forecast.io/forecast/46b25d6dfa9543b25e49011c87d42733/"+lat+","+lon+"?units=si&exclude=minutely,daily,alerts,flags";
		
		JSONParser parser = new JSONParser();
        String text = "";
        /* First, connect to the website using an url and pass the whole page
         * as a string. Using StringBuilder to remove all the extra characters
         * and new line character to compile the JSON.*/
        try
        {
            URL oracle = new URL(siteUrl);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String x = "";
            StringBuilder inputLine = new StringBuilder();
            
            while ((x = in.readLine()) != null) {       
                inputLine.append(x);
            }   
            text = inputLine.toString();
        }
        catch (MalformedURLException e1){
            e1.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }   
        
        /* Next is to parse the whole page string as a JSONObject and extract
         * the needed data. In this example, it prints the name of the site 
         * and the weather at every half hour. */
        try{
            Object obj = parser.parse(text);
            JSONObject result = (JSONObject)obj;
            JSONObject obs = (JSONObject) result.get("hourly");
            
            JSONArray datas = (JSONArray) obs.get("data");
            return datas;
            
        }catch(ParseException pe){
            System.out.println(pe);
        }
        return null;
	}
}

