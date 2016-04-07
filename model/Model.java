package model;
import java.util.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;

public class Model {
	/*Convert JSON data into HashMap.*/
	public void init_data(JSONArray source){
		JSONParser parser = new JSONParser();
		Object jsonObject = parser.parse(source);
		JSONArray listOfStates = (JSONArray)jsonObject;
		
		HashMap states = new HashMap(); /*Declare outer HashMap: Key = state name, Value = HashMap stations*/
		
		for (int i=0; i < listOfStates.length(); i++) {
			JSONObject state = (JSONObject)listOfStates.get(i);
			String stateName = state.get("state");
			JSONArray listOfCities = (JSONArray)state.get("stations");
			
			HashMap stations = new HashMap(); /*Declare inner HashMap: Key = city name, Value = URL string*/
			
			for (int j=0; j < listOfCities.length(); j++) {
				JSONObject city = (JSONObject)listOfCities.get(j);
				String cityName = city.get("city");
				String jsonUrl = city.get("url");
				stations.put(ciyName, jsonUrl);
			}
			states.put(stateName, stations);
		}
		
		return states;
	}
	
	public String getUrl(HashMap model, String stateName, String cityName){
		String url;
		url = model.get(stateName).get(cityName);
		return url;
	}
	
	public void loadFaveList(JSONArray source){
		JSONParser parser = parser.parse();
		Object jsonObject = parser.parse(source);
		JSONArray listOfFaves = (JSONArray)jsonObject;
		
		HashMap faves = new HashMap();/*Declare inner HashMap: Key = city name, Value = state name (This information needed to call getUrl() method.)*/
		
		for (int i=0; i < listOfFaves.length(); i++){
			JSONObject fave = (JSONObject)listOfFaves.get(i);/*gets one JSON object, which is something within curly braces{}, from inside the JSON array[].*/
			faves.put(fave.get("city"),fave.get("state"));
		}
		
		return faves;
	}
	
	public void addFave(Map<String, String>faveList, String cityName, String stateName){
		faveList.put(cityName, stateName);
	}
	
	public void removeFave(Map<String, String>faveList, String cityName){
		faveList.remove(cityName);
	}
	
	public void saveFaveList(Map<String, String>faveList){
		JSONObject obj = new JSONObject(faveList);
		try (FileWriter outputFile = new FileWriter("faveList.json")){
			file.write(obj.toJSONString());
		}catch{
			e.printStackTrace();
		}finally{
			file.flush();
			file.close();
		}
	}
	
	public JSONArray getStationData(){
		return new Extraction.getStationData();
	}
	
	
}