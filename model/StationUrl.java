public class StationUrl{
	private String stateName;
	private String stationName;
	private String urlName;
	
	public void setState(String name){
		this.stateName = name;
	}
	
	public void setStation(String name){
		this.stationName = name;
	}
	
	public void setUrl(String name){
		this.urlName = name;
	}
	
	public void getState(String name){
		return this.stateName;
	}
	
	public String getStation(){
		return this.stationName;
	}
	
	public String getUrl(){
		return this.urlName;
	}
}