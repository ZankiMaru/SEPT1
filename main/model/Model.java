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
	private ArrayList<String> faves = new ArrayList<String>();
	public int xPosition;
	public int yPosition;
	
	public void init_data(){
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

	public void init_faveList(String favourites){
		String line;
		BufferedReader br = new BufferedReader(new FileReader(favourites));
		while((line = br.readLine()) != null){
			this.faves.add(line);
		};
	}
	
	public void addFave(String stationName){
		this.faves.add(stationName);
	}
	
	public void removeFave(String stationName){
		this.faves.remove(stationName);
	}
		
	public void saveFaveList(String favourites){
		FileWriter fw = new FileWriter(favourites, true);
		for(String stationName : this.faves){
			fw.write(stationName+"\n");
		};
		fw.flush();
		fw.close();
	}
	
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
	
	public void setCoordinates(int x, int y){
		this.xPosition = x;
		this.yPosition = y;
	}
	
	public void init_coordinates(String coordinates){
		String x;
		String y;
		BufferedReader br = new BufferedReader(new FileReader(coordinates));
		x = br.readLine();
		y = br.readLine();
		this.xPosition = Integer.parseInt(x);
		this.xPosition = Integer.parseInt(y);
	}
	
	public void saveCoordinates(String coordinates){
		FileWriter fw = new FileWriter(coordinates, true);
		fw.write(xPosition+"\n");
		fw.write(yPosition);
		fw.flush();
		fw.close();
	}
	
}