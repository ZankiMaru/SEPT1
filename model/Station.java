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
    
    public Station(JSONArray stationData)
    //public Station(String name)
    {
        JSONObject nameOfStation = (JSONObject) stationData.get(0);
        this.name = (String) nameOfStation.get("name");
                
        for(int i = 0; i < stationData.size(); i++){
            JSONObject j = (JSONObject) stationData.get(i);
            
            Interval interval = new Interval(
                    (String) j.get("local_date_time_full"),
                    (double) j.get("air_temp"),
                    (long) j.get("wind_spd_kmh"),
                    (String) j.get("rain_trace")
                    );
            
            list.add(interval);
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
    
    /*
    public DayData getToday()
    {
        Calendar date = Calendar.getInstance();
        DayData day = new DayData();
        
        ArrayList<Interval> todayList = new ArrayList<Interval>();
        
        //Find all of todays instances
        for (Interval i : list)
            if (date.DAY_OF_YEAR == i.date.DAY_OF_YEAR)
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
    
    public DayData getYesterday()
    {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DAY_OF_YEAR, -1);
        DayData day = new DayData();
        
        ArrayList<Interval> todayList = new ArrayList<Interval>();
        
        //Find all of todays instances
        
        for (Interval i : list)
            if (date.DAY_OF_YEAR == i.date.DAY_OF_YEAR)
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
    
    public DayData getDayBefore()
    {
        Calendar date = Calendar.getInstance();
        DayData day = new DayData();
        
        ArrayList<Interval> todayList = new ArrayList<Interval>();
        
        //Find all of todays instances
        for (Interval i : list)
            if (date.DAY_OF_YEAR - 2 == i.date.DAY_OF_YEAR)
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
    */
}
