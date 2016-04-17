package main.model;
import java.util.*;

import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Model {
	private HashMap<String, State> listOfStates = new HashMap<String, State>();
	private ArrayList<String> faves = new ArrayList<String>();
	public int xPosition;
	public int yPosition;
   String favouritesFile = "favourites.txt";
   String coordinateFile = "coordinates.txt";

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
			}

	public void init_faveList(){
		String line;
		BufferedReader br;
      try
      {
         br = new BufferedReader(new FileReader(favouritesFile));
         while((line = br.readLine()) != null){
            this.faves.add(line);
         };
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
	}
	
	public void addFave(String stationName){
		this.faves.add(stationName);
	}
		
	public void addRemoveFavourites(String stationName){
	   boolean found = false;
	   int stationIndex = 0;
	   
	   System.out.println("save fav - " + stationName);
	   String station = stationName.replaceAll("\\p{P}", "");
	   System.out.println("save fav trimmed - " + station);
	   
	   for(int i = 0 ; i < faves.size(); i++){
	      if(station.equalsIgnoreCase(faves.get(i))){
	         found = true;
	         stationIndex = i;
	      }
	   }
	   if(found){
	      faves.remove(stationIndex);
	   }
	   else{
	      addFave(station);
	   }
	}
		
	public void saveFaveList(){
		FileWriter fw;
		clearFile(favouritesFile);
      try {
         fw = new FileWriter(favouritesFile, true);
         for(String stationName : this.faves){
            fw.write(stationName+"\n");
         };
         fw.flush();
         fw.close();
      }
      catch (IOException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
//	public JSONArray getStationData(){
//      return null;
//		return Extraction.getStationData();
//	}
	
	public ArrayList<Station> getAllFave(){
	   ArrayList<Station> favourites = new ArrayList<Station>();
	   for(int i = 0; i<faves.size(); i++){
//	      System.out.println(faves.get(i));
	      favourites.add(getStation(faves.get(i)));
	   }
	   return favourites;
	}
	
	public Station getStation(String stationName){
		Station selectedStation;
		for(String currentState : this.listOfStates.keySet()){
		   System.out.println("searchin");
		   System.out.println(stationName);
         System.out.println("====================");

		   
			selectedStation = this.listOfStates.get(currentState).getStation(stationName);
			if(selectedStation != null){
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
		
	public void init_coordinates(){
		String x;
		String y;
		BufferedReader br;
		
		if(!new File(coordinateFile).exists()){
		   try {
            PrintWriter writer = new PrintWriter(coordinateFile, "UTF-8");
            writer.close();
         }
         catch (FileNotFoundException e) {
            e.printStackTrace();
         }
         catch (UnsupportedEncodingException e) {
            e.printStackTrace();
         }
		}
		
      try {
         br = new BufferedReader(new FileReader(coordinateFile));
         x = br.readLine();
         y = br.readLine();
         if(x != null && y != null) {
            this.xPosition = Integer.parseInt(x);
            this.yPosition = Integer.parseInt(y);
         }
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
	}
	
	public void saveCoordinates(int x, int y){
		FileWriter fw;
      try {
         clearFile(coordinateFile);
         fw = new FileWriter(coordinateFile, true);
         System.out.println(x+"\n"+y);
         fw.write(x+"\n"+y);
         fw.close();
      }
      catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	public void clearFile(String path){
	   PrintWriter writer;
      try
      {
         writer = new PrintWriter(path);
         writer.print("");
         writer.close();
      }
      catch (FileNotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
	}
	
	public Point getCoordinate(){	   
	   Point loc = new Point(xPosition, yPosition);
	   return loc;
	}
}