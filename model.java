import java.util.*;
import org.json.*;
import extraction.java

public class model {
	/*Convert JSON data into HashMap.*/
	public void init_data(JSONArray source){
		JSONParser parser = parser.parse();
		Object jsonObject = parser.parse(source);
		JSONArray listOfStates = (JSONArray)jsonObject;
		
		HashMap states = new HashMap();
		
		for (int i=0; i < listOfStates.length(); i++) {
			JSONObject state = (JSONObject)listOfStates.get(i);
			String stateName = state.get("state");
			JSONArray listOfCities = (JSONArray)state.get("stations");
			
			HashMap stations = new HashMap();
			
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
		url = model.get(0).get(stateName).get(cityName);
		return url;
	}
	
	public void addFave(Map<String, String>faveList, String cityName){
		
	}
	
	public void removeFave(){
		
	}
	
	public void refreshData(){
		
	}
	
	
}