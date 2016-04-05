import java.util.*;
import org.json.*;
import Extraction.java

public class model {
	/*Convert JSON data into HashMap.*/
	public void init_data(JSONArray source){
		JSONParser parser = parser.parse();
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
	
	/*
	Converts JSON file containing favourites into HashMap.
	e.g.
	[
		{
			"city" : "middle of nowhere",
			"state" : "somewhere in Tasmania"
		},
		{
			"city" : "Melbourne",
			"state" : "Victoria"
		},
		{
			"city" : "potato",
			"state" : "tomato"
		}
	]
	This can be called an array of objects, anything within [] is called a JSONArray, and anything within {} is called a JSONObject.
	So this would be a JSONArray filled with JSONObjects, whereas a file such as:
	{
		"city" : "middle of nowhere",
		"state" : "somewhere in Tasmania"
	}
	Would only be called a JSONObject because there are no [], also:
	{
		[
			{
				"city" : "middle of nowhere",
				"state" : "somewhere in Tasmania"
			},
			{
				"city" : "Melbourne",
				"state" : "Victoria"
			}
		]
	}
	Would mean it's a JSONObject that contains a JSONArray, and the JSONArray contains 2 JSONObjects.
	Hope this helps, please delete this comment eventually.
	*/
	public void init_fave(JSONArray source){
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
	
	public void addFave(Map<String, String>faveList, String cityName){
		
	}
	
	public void removeFave(){
		
	}
	
	public JSONArray getStationData(){
		return new Extraction.getStationData();
	}
	
	
}