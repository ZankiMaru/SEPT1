public class State {
	private String name;
	public HashMap<String, Station> stations = new HashMap<String, Station>();
	
	public State(String name){
		this.name = name;
	}
	
	private Station getStation(String stationName){
		Station station = stations.get(stationName);
		return station;
	}
}