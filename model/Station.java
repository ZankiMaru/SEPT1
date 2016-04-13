package model;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class Station
{
    public String name;
    ArrayList<Interval> list = new ArrayList<Interval>();
	
	private String stateName;
	private String stationName;
	private String urlName; 
    
    public Station(String name, String urlName)
    {
       this.name = name;
       this.urlName = urlName;
    }

    public void getData()
    {
        JSONArray stationData = new Extraction().getStationData(urlName);

        /* Error handler in case if the station data is empty */
       if(stationData.size() != 0){
          JSONObject nameOfStation = (JSONObject) stationData.get(0);
          this.name = (String) nameOfStation.get("name");
          System.out.println(this.name);
          for(int i = 0; i < stationData.size(); i++){
             JSONObject j = (JSONObject) stationData.get(i);
            
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
       }
    }
    
    //String timeString, String temp, String wind, String rain
    public Interval getNow()
    {
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
        
        return day;
        
    }
	
	public void setState(String name){
		this.stateName = name;
	}
	
	public void setStation(String name){
		this.stationName = name;
	}
	
	public void setUrl(String name){
		this.urlName = name;
	}
	
	public String getState(String name){
		return this.stateName;
	}
	
	public String getStation(){
		return this.stationName;
	}
	
	public String getUrl(){
		return this.urlName;
	}
}
