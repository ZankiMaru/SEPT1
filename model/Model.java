package model;
import java.util.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Model {
	private HashMap<String, HashMap<String, Station>> listOfStates = new HashMap<String, HashMap<String, Station>>();
	private HashMap<String, String> faves = new HashMap<String, String>();
	
	public void init_data(){
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(new FileReader("input.json"));
		JSONArray listOfStates = (JSONArray) Extraction.getAllStates();
		
		/* Reduce listOfStates.size() into 1-3 for a faster startup */
		for (int i=0; i < listOfStates.size(); i++) {
			JSONObject state = (JSONObject)listOfStates.get(i);
			String stateName = (String) state.get("state");
			System.out.println(stateName);
			JSONArray listOfStations = (JSONArray)state.get("stations");

			for (int j=0; j < listOfStations.size(); j++) {
			   
	         HashMap<String, Station> stations = new HashMap<String,Station>();

				JSONObject station = (JSONObject)listOfStations.get(j);
				String stationName = (String) station.get("city");
				String jsonUrl = (String) station.get("url");

				Station singleStation = new Station( Extraction.getStationData(jsonUrl) );
				
				singleStation.setState(stateName);
				singleStation.setStation(stationName);
				singleStation.setUrl(jsonUrl);

				stations.put(stationName, singleStation);
				
				this.listOfStates.put(stateName, stations);
			};
			
			
		};
	}
	
	public String getUrl(String stateName, String stationName){
		Station station;
		String url;
		station = listOfStates.get(stateName).get(stationName);
		url = station.getUrl();
		return url;
	}
	
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
	   for(HashMap x : listOfStates.values() ){
	      return (Station) x.get(stationName);
	   }
	   return null;
	}
	
}