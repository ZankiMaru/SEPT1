package model;

import java.util.HashMap;

public class State {
	private String name;
	public HashMap<String, Station> stations = new HashMap<String, Station>();
	
	public State(String name){
		this.name = name;
	}
	
	public Station getStation(String stationName){
		Station station = stations.get(stationName);
		return station;
	}
	
	public void addStation(String stationName, Station station){
		stations.put(stationName, station);
	}
	
	public void printAllStation(){
		for(String x : stations.keySet()){
			System.out.println(x);
		}
	}
}