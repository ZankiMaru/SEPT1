package main.model;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.logging.Logger;
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
	public static Logger dataLogger = Logger.getLogger(Model.class.getName());
	
	private HashMap<String, State> listOfStates = new HashMap<String, State>();
	private ArrayList<String> faves = new ArrayList<String>();
	public int xPosition;
	public int yPosition;
	String favouritesFile = "favourites.txt";
   	String coordinateFile = "coordinates.txt";
   	String site = "forecast";
   	Extraction extraction = new Extraction(this);
   	
   /* init_data function is called to initialise the model's data. */
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
				Station singleStation = new Station(stationName, jsonUrl, this);
				
				singleStation.setState(stateName);
				newState.addStation(stationName, singleStation);
			};
		};
		dataLogger.info("Initialized model data.");
	}

	/* init_faveList function is used to initialise the model's favourites.
	 * It first check if there is favourites.txt, if not, create a new one.
	 * If yes, it will clear the content and fill with favourites station. */
	public void init_faveList(){
		String line;
		BufferedReader br;
		
		if(!new File(favouritesFile).exists()){
	         try {
	            PrintWriter writer = new PrintWriter(favouritesFile, "UTF-8");
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
	  dataLogger.info("Initialized favourite list.");
	}
	
	/* addFave function used to add station to fave list. */
	public void addFave(String stationName){
		this.faves.add(stationName);
	}
		
	/* addRemoveFavourites checks for existing station in fave list. If there
	 * is, it will remove the station. Otherwise, it will add the station. */
	public void addRemoveFavourites(String stationName){
	   boolean found = false;
	   int stationIndex = 0;
	   String station = stationName.replaceAll("\\p{P}", "");
	   for(int i = 0 ; i < faves.size(); i++){
	      if(station.equalsIgnoreCase(faves.get(i))){
	         found = true;
	         stationIndex = i;
	      }
	   }
	   if(found)
	      faves.remove(stationIndex);
	   else
	      addFave(station);
	  
	  dataLogger.info("Updated favourite list.");
	}
		
	/* saveFaveList save the favourites from the list to text file at the end. */
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
         e.printStackTrace();
      }
	  dataLogger.info("Stored favourite list.");
	}
	
	/* getAllFave returns an ArrayList of stations from favourites. */
	public ArrayList<Station> getAllFave(){
	   ArrayList<Station> favourites = new ArrayList<Station>();
	   for(int i = 0; i<faves.size(); i++){
	      favourites.add(getStation(faves.get(i)));
	   }
	   return favourites;
	}
	
	/* getStation search and returns Station object from station name. */
	public Station getStation(String stationName){
		Station selectedStation;
		for(String currentState : this.listOfStates.keySet()){		   
			selectedStation = this.listOfStates.get(currentState).getStation(stationName);
			if(selectedStation != null){
				return selectedStation;
			};
		};
		System.out.println("Station doesn't exist.\n");
		return null;
	}
	
	/* getState returns State class from state name. */
	public State getState(String stateName){
		return this.listOfStates.get(stateName);
	}
	
	/* countStates count the number of states. */
	public int countStates(){
		return listOfStates.size();
	}
		
	/* init_coordinates function was initialise before opening the main window.
	 * It will look for coordinates.txt and get the last position of the window.
	 * If there isn't one, it will create an empty one. */
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
	
	/* saveCoordinates function saves the last position of the window. */
	public void saveCoordinates(int x, int y){
		FileWriter fw;
      try {
         clearFile(coordinateFile);
         fw = new FileWriter(coordinateFile, true);
         fw.write(x+"\n"+y);
         fw.close();
      }
      catch (IOException e) {
         e.printStackTrace();
      }
	}
	
	/* clearFile function clear the content of a text file. */
	public void clearFile(String path){
	   PrintWriter writer;
      try {
         writer = new PrintWriter(path);
         writer.print("");
         writer.close();
      }
      catch (FileNotFoundException e) {
         e.printStackTrace();
      }
	}
	
	/* getCoordinate function returns Point for window location. */
	public Point getCoordinate(){	   
	   Point loc = new Point(xPosition, yPosition);
	   return loc;
	}
	
	public void setSite(String site){
		this.site = site;
	}
	
	public String getSite(){
		return this.site;
	}
}