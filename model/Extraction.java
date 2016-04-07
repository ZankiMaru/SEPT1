package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

/* Extraction class is used to extract information from the website's JSON file.
 * Extraction class will be able to return values from the website's JSON into
 * Model class for the application to use. */
public class Extraction {

	/* The function used to debug the Extraction class easier. */
//   public static void main(String[] args){
      
//      String site;
//      printAllStations();
//        JSONArray x = getAllStateCities("victoria");
//      site = findSiteUrl("Melbourne Olympic Park");
//      getStationData(site);
//      System.out.println(site);
//      printSiteWeather(site);
//      getAllStates();
//   }
   
	/* printSiteWeather function is the example of how to extract the weather
	 * of a site using an url as a string. */
	private static void printSiteWeather(String siteUrl)
	{
		JSONParser parser = new JSONParser();
		String text = "";
		/* First, connect to the website using an url and pass the whole page
		 * as a string. Using StringBuilder to remove all the extra characters
		 * and new line character to compile the JSON.*/
		try
		{
			URL oracle = new URL(siteUrl);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String x = "";
			StringBuilder inputLine = new StringBuilder();
			
			while ((x = in.readLine()) != null) {       
				inputLine.append(x);
			}	
			text = inputLine.toString();
		}
		catch (MalformedURLException e1){
			e1.printStackTrace();
		} 
		catch (IOException e){
			e.printStackTrace();
		}	
      
		/* Next is to parse the whole page string as a JSONObject and extract
		 * the needed data. In this example, it prints the name of the site 
		 * and the weather at every half an hour. */
		try{
			Object obj = parser.parse(text);
			JSONObject result = (JSONObject)obj;
			JSONObject obs = (JSONObject) result.get("observations");
			
			JSONArray datas = (JSONArray) obs.get("data");
			JSONArray header = (JSONArray) obs.get("header");
			JSONObject name = (JSONObject) header.get(0);
			System.out.println(name.get("name"));

			for(int j = 0; j<datas.size(); j++){
				JSONObject cities = (JSONObject) datas.get(j);
				System.out.printf("%s - %s\n",cities.get("local_date_time"), cities.get("air_temp"));
			}
		}catch(ParseException pe){
			System.out.println(pe);
		}
	}
	
	/* findSiteUrl is a function used to search through the stations.json or 
	 * inputs.json and return the url for the site using the site's name. 
	 * The function will return null if there's no site found*/
	private static String findSiteUrl(String site) {
		JSONParser parser = new JSONParser();
		String path = "input.json";
		try{
			FileReader file = new FileReader(path);
			Object obj = parser.parse(file);
			JSONArray result = (JSONArray)obj;
			for(int i = 0; i<result.size();i++){
				JSONObject states = (JSONObject) result.get(i);
				JSONArray sites = (JSONArray) states.get("stations");

				for(int j = 0; j<sites.size(); j++){
					JSONObject cities = (JSONObject) sites.get(j);
					if(site.equalsIgnoreCase((String) cities.get("city"))){
						System.out.println(cities.get("url"));
						return (String) cities.get("url");
					}
				}
			}
		}catch(ParseException pe){
			System.out.println(pe);
		}
		catch (FileNotFoundException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	/* printAllStations function is used to debug and print all sites and url
	 * within stations.json or inputs.json. */
	private static void printAllStations()
	{
		JSONParser parser = new JSONParser();
		String path = "input.json";
		
		try{
			FileReader file = new FileReader(path);
			Object obj = parser.parse(file);
			JSONArray result = (JSONArray)obj;
			for(int i = 0; i<result.size();i++){
				JSONObject states = (JSONObject) result.get(i);
				System.out.println(states.get("state"));
				JSONArray sites = (JSONArray) states.get("stations");

				for(int j = 0; j<sites.size(); j++){
					JSONObject cities = (JSONObject) sites.get(j);
					System.out.println(cities.get("city"));
					System.out.println(cities.get("url"));
				}
			}
		}catch(ParseException pe){
			System.out.println(pe);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	/* getStationData function is used to return a JSONArray containing the 
	 * station datas from an url. */
	public static JSONArray getStationData (String siteUrl)
	{
	    JSONParser parser = new JSONParser();
        String text = "";
        /* First, connect to the website using an url and pass the whole page
         * as a string. Using StringBuilder to remove all the extra characters
         * and new line character to compile the JSON.*/
        try
        {
            URL oracle = new URL(siteUrl);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String x = "";
            StringBuilder inputLine = new StringBuilder();
            
            while ((x = in.readLine()) != null) {       
                inputLine.append(x);
            }   
            text = inputLine.toString();
        }
        catch (MalformedURLException e1){
            e1.printStackTrace();
        } 
        catch (IOException e){
            e.printStackTrace();
        }   
      
        /* Next is to parse the whole page string as a JSONObject and extract
         * the needed data. In this example, it prints the name of the site 
         * and the weather at every half hour. */
        try{
            Object obj = parser.parse(text);
            JSONObject result = (JSONObject)obj;
            JSONObject obs = (JSONObject) result.get("observations");
            
            JSONArray datas = (JSONArray) obs.get("data");
            return datas;
            
        }catch(ParseException pe){
            System.out.println(pe);
        }
        return null;
	}

	/* getAllStates function is used to return a JSONArray containing all of
	 *  the states data. */
	public static JSONArray getAllStates() {
      JSONParser parser = new JSONParser();
      String path = "input.json";
      JSONArray result = null;
      
      try{
         FileReader file = new FileReader(path);
         Object obj = parser.parse(file);
         result = (JSONArray)obj;
         
      }catch(ParseException pe){
         System.out.println(pe);
      }
      catch (FileNotFoundException e){
         e.printStackTrace();
      }
      catch (IOException e){
         e.printStackTrace();
      }
      
      return result;
	}
	
	/* getStateCities function is used to return a JSONArray containing the
	 * state's cities data. */
	public static JSONArray getStateCities(String stateName) {
	   JSONParser parser = new JSONParser();
	   String path = "input.json";
	   JSONArray sites = null;
	   
	   try{
	      FileReader file = new FileReader(path);
	      Object obj = parser.parse(file);
	      JSONArray result = (JSONArray)obj;
	      for(int i = 0; i<result.size();i++){
	         JSONObject states = (JSONObject) result.get(i);
	         
	         if(stateName.equalsIgnoreCase((String)states.get("state")))
	            sites = (JSONArray) states.get("stations");
	      }  
	   }catch(ParseException pe){
	      System.out.println(pe);
	   }
	   catch (FileNotFoundException e){
	      e.printStackTrace();
	   }
	   catch (IOException e){
	      e.printStackTrace();
	   }
	   return sites;
	}

	
}
