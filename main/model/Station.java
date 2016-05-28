package main.model;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.lang.*;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import main.model.*;

public class Station
{
	public static Logger dataLogger = Logger.getLogger(Station.class.getName());
	
    public String name;
	private String stateName;
	public String urlName;
	public double lon,lat;
	private Model model;
    ArrayList<Interval> list = new ArrayList<Interval>();
	ArrayList<ForecastInterval> list2 = new ArrayList<ForecastInterval>();
	public ArrayList<ForecastDayData> forecastDays = new ArrayList<ForecastDayData>();/*Used to populate forecast table*/
    private boolean faved = false;

    public Station(String name, String urlName, Model model)
    {
       this.name = name;
       this.urlName = urlName;
       this.model = model;
    }

    public void getData()
    {    	
        JSONArray stationData = Extraction.getStationData(urlName);
        
        JSONObject lonlat = (JSONObject) stationData.get(0);

        this.lon = (double) lonlat.get("lon");
        this.lat = (double) lonlat.get("lat");  
        System.out.println(lat + ", " + lon);
        
        /* This is a test line of code to call for data */
//       JSONArray test = Extraction.getStationDataSite(lat, lon);
//       System.out.println(test);
        
        /* Error handler in case if the station data is empty */
       if(stationData.size() != 0){   
          JSONObject nameOfStation = (JSONObject) stationData.get(0);
          this.name = (String) nameOfStation.get("name");
          for(int i = 0; i < stationData.size(); i++){
             JSONObject j = (JSONObject) stationData.get(i);
            
             this.lon = (double) j.get("lon");
             this.lat = (double) j.get("lat");
             
             String localDateTime = (String) j.get("local_date_time_full");

             /* Some station have a null as the air temperature */
             double airTemp;
             if(j.get("air_temp") == null)
                airTemp = 0;
             else
                airTemp = (double) j.get("air_temp");

             /* Some station have a null as the wind speed */
             long windSpd;
             if( j.get("wind_spd_kmh") == null)
                windSpd = 0;
             else
                windSpd = (long) j.get("wind_spd_kmh");
          
             String rainTrace = (String) j.get("rain_trace");
             Interval interval = new Interval(
                    localDateTime,
                    airTemp,
                    windSpd,
                    rainTrace
                    );
            
             list.add(interval);
          }
		  dataLogger.info("Successfully populated observation interval list.");
       }
    }
    
    //String timeString, String temp, String wind, String rain
    public Interval getNow()
    {
       if(list.size() == 0) {
          SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmm00");
          return new Interval(format.format(new Date()), 0, 0, "0");
       }
       else
            return list.get(0);        
    }
    
    
    public DayData getDay(String whichDay)
    {
        Calendar date = Calendar.getInstance();
        switch (whichDay) {
        case "yesterday": 
            date.add(Calendar.DAY_OF_YEAR, -1);
            break;
        case "dayBefore": 
            date.add(Calendar.DAY_OF_YEAR, -2);
            break;
        }

        DayData day = new DayData();
        
        ArrayList<Interval> todayList = new ArrayList<Interval>();
        
        //Find all of todays instances
        for (Interval i : list)
            if (date.get(Calendar.DAY_OF_MONTH) == i.date.get(Calendar.DAY_OF_MONTH))
                todayList.add(i);
        
        if (todayList.size() == 0)
            return new DayData(0, 0, 0, 0);
        
        day.min = todayList.get(0).temp;
        day.max = todayList.get(0).temp;
        day.wind = todayList.get(0).wind;
        day.rain = todayList.get(0).rain;
        
        for (Interval i : todayList)
        {
            if (i.temp < day.min)
                day.min = i.temp;
            if (i.temp > day.max)
                day.max = i.temp;
            day.wind += i.wind;
        }
        day.wind /= todayList.size();
        day.rain = todayList.get(0).rain;
        
        if (day.isNotNull())
            return day;
        else {
            return new DayData(0, 0, 0, 0);
        } 
    }
	
	public boolean getFaved(){
		return faved;
	}
	
	public void toogleFaved(){
		if(faved)
			faved = false;
		else
			faved = true;
	}
    
	public void getForecastData(){
		JSONArray data = Extraction.getStationDataSite(this.lat, this.lon);
		String site = model.getSite();
		/*Fill up hourly/3hour interval list*/
		for(int i = 0; i < data.size(); i++){
			long timestamp = 0;
			double windspeed = 0.0, temperature = 0.0, precipIntensity = 0.0;
			if(site.equals("forecast")){
				JSONObject intervalobj = (JSONObject) data.get(i);
				timestamp = (long) intervalobj.get("time");
				windspeed = ((Number) intervalobj.get("windSpeed")).doubleValue();
				temperature = ((Number) intervalobj.get("temperature")).doubleValue();
				precipIntensity = ((Number) intervalobj.get("precipIntensity")).doubleValue();
			};
			if(site.equals("openweather")){
				JSONObject intervalobj = (JSONObject) data.get(i);
				timestamp = (long) intervalobj.get("dt");
				windspeed = (double) ((JSONObject) intervalobj.get("wind")).get("speed");
				temperature = (double) ((JSONObject) intervalobj.get("main")).get("temp");
				JSONObject rainObj = (JSONObject) intervalobj.get("rain");
				if(rainObj != null){
					precipIntensity = (double) rainObj.get("3h");
				}
				else{
					precipIntensity = 0.0;
				};
			};
			ForecastInterval newInterval = new ForecastInterval(timestamp, temperature, windspeed, precipIntensity);

			System.out.println(name);
			newInterval.check();
			
			list2.add(newInterval);

		};
		dataLogger.info("Successfully populated forecast interval list.");
		
		/*Fill up day-by-day list (based on the hourly/3hour interval list)*/
		Calendar today = Calendar.getInstance();
		
		for(int i = 0; i < 5; i++){
			ArrayList<ForecastInterval> cache = new ArrayList<ForecastInterval>();
			ForecastDayData newDay = new ForecastDayData();
			/*Fill cache (an array of intervals within the same day)*/
			for( ForecastInterval hourInterval : this.list2 ){
				if( (hourInterval.dateTime.get(Calendar.DAY_OF_YEAR)) == (today.get(Calendar.DAY_OF_YEAR) + i) ){
					cache.add(hourInterval);
				}
			}
			/*Get min/max data from cache to add ForecastDayData object to forecastDays list*/
			if (cache.size() != 0){
				newDay.dateTime = cache.get(0).dateTime;
				newDay.min = cache.get(0).temp;
				newDay.max = cache.get(0).temp;
				newDay.wind = cache.get(0).wind;
				newDay.rain = cache.get(0).rain;
				for(ForecastInterval j : cache){
					if(newDay.min > j.temp)
						newDay.min = j.temp;
					if(newDay.max < j.temp)
						newDay.max = j.temp;
					if(newDay.wind < j.wind)
						newDay.wind = j.wind;
					if(newDay.rain < j.rain)
						newDay.rain = j.rain;
				}
				this.forecastDays.add(newDay);
			}
			else{
				break;
			};	
		}
		dataLogger.info("Successfully populated forecast day list.");
	}

	public void setState(String name){
		this.stateName = name;
	}
	
	public void setStation(String name){
		this.name = name;
	}
	
	public void setUrl(String name){
		this.urlName = name;
	}
	
	public String getState(String name){
		return this.stateName;
	}
	
	public String getStation(){
		return this.name;
	}
	
	public String getUrl(){
		return this.urlName;
	}
	
	public boolean checkData(){
		if(list.isEmpty() && list == null)
			return false;
		else
			return true;
	}
}
