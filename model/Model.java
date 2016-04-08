package model;
import java.util.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import Station.java;
import java.io.FileWriter;
import java.io.IOException;

public class Model {
	private HashMap<String, HashMap<String, Station>> listOfStates = new HashMap<String, HashMap<String, Station>>;
	private HashMap<String, String> faves = new HashMap<String, String>;
	
	public void init_data(){
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader("input.json"));
		JSONArray listOfStates = (JSONArray)obj;
		
		for (int i=0; i < listOfStates.length(); i++) {
			JSONObject state = (JSONObject)listOfStates.get(i);
			String stateName = state.get("state");
			JSONArray listOfStations = (JSONArray)state.get("stations");
			
			this.listOfstates.put(stateName);
			
			for (int j=0; j < listOfStations.length(); j++) {
				Station singleStation = new Station();
				
				JSONObject station = (JSONObject)listOfStations.get(j);
				String stationName = station.get("city");
				String jsonUrl = station.get("url");
				
				singleStation.setState(stateName);
				singleStation.setStation(stationName);
				singleStation.setUrl(jsonUrl);
				
				this.listOfstates.get(stateName).put(stationName, singleStation);
			};
		};
	}
	
	public String getUrl(String stateName, String stationName){
		Station station;
		String url;
		station = this.listOfstates.get(stateName).get(stationName);
		url = station.getUrl();
		return url;
	}
	
	public void loadFaveList(){
		String state;
		String station;
		JSONParser parser = parser.parse();
		Object obj = parser.parse(new FileReader("input.json"));
		JSONArray listOfFaves = (JSONArray)obj;
		
		for (int i=0; i < listOfFaves.length(); i++){
			JSONObject fave = (JSONObject)listOfFaves.get(i);
			state = fave.get("state");
			station = fave.get("station");
			this.faves.put(state, station);
		};
	}
	
	public void addFave(Map<String, String>faveList, String cityName, String stateName){
		faveList.put(cityName, stateName);
	}
	
	public void removeFave(Map<String, String>faveList, String cityName){
		faveList.remove(cityName);
	}
	
	public void saveFaveList(){
		JSONObject obj = new JSONObject(this.faves);
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