package main.model;
import java.util.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Model {
	private HashMap<String, State> listOfStates = new HashMap<String, State>();
	private HashMap<String, String> faves = new HashMap<String, String>();
	
	public void init_data(){
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(new FileReader("input.json"));
		JSONArray listOfStates = (JSONArray) Extraction.getAllStates();
		
		for (int i=0; i < listOfStates.size(); i++) {
			JSONObject state = (JSONObject)listOfStates.get(i);
			String stateName = (String) state.get("state");
			State newState = new State(stateName);
			JSONArray listOfStations = (JSONArray)state.get("stations");
			this.listOfStates.put(stateName, newState);
			
			for (int j=0; j < listOfStations.size(); j++) {
				JSONObject station = (JSONObject)listOfStations.get(j);
				String stationName = (String) station.get("city");
				String jsonUrl = (String) station.get("url");
				Station singleStation = new Station(stationName, jsonUrl);
				
				singleStation.setState(stateName);
				
				newState.addStation(stationName, singleStation);
			};
		};
		
		for(String x : this.listOfStates.keySet()){
			System.out.println("State = " + x);
			this.listOfStates.get(x).printAllStation();
		}
	}
	
//	public String getUrl(String stationName){ 
//		String url;
//		url = this.listOfAllUrls.get(stationName);
//		return url;
//	}
	
	public void loadFaveList(){
		String state;
		String station;
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(new FileReader("input.json"));
		JSONArray listOfFaves = (JSONArray) Extraction.getAllStates();
		
		for (int i=0; i < listOfFaves.size(); i++){
			JSONObject fave = (JSONObject)listOfFaves.get(i);
			state = (String) fave.get("state");
			station = (String) fave.get("station");
			this.faves.put(state, station);
		};
	}
	
	public void addFave(Map<String, String>faveList, String cityName, String stateName){
		faveList.put(cityName, stateName);
	}
	
	public void removeFave(Map<String, String>faveList, String cityName){
		faveList.remove(cityName);
	}
		
//	public void saveFaveList(){
//		JSONObject obj = new JSONObject(this.faves);
//		try (FileWriter outputFile = new FileWriter("faveList.json")){
//			file.write(obj.toJSONString());
//		}catch{
//			e.printStackTrace();
//		}finally{
//			file.flush();
//			file.close();
//		}
//	}
	
//	public JSONArray getStationData(){
//      return null;
//		return Extraction.getStationData();
//	}
	
	public Station getStation(String stationName){
		Station selectedStation;
		for(String currentState : this.listOfStates.keySet()){
			selectedStation = this.listOfStates.get(currentState).getStation(stationName);
			if(selectedStation != null){
				System.out.println("asdasd " + selectedStation.getStation());
				System.out.println("asdasd " + selectedStation.getUrl());

				return selectedStation;
			};
		};
		System.out.println("Station doesn't exist.\n");
		return null;
	}
	
	public State getState(String stateName){
		return this.listOfStates.get(stateName);
	}
	
	public int countStates(){
		return listOfStates.size();
	}
	
}