package simulator.model;

public class NewInterCityRoadEvent extends Event {
	
	private String id;
	private String srcJunc;
	private String destJunc;
	private int length;
	private int co2Limit;
	private int maxSpeed;
	private Weather weather;

	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
		super(time);
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
	}

	@Override
	void execute(RoadMap map) throws Exception {
		Junction src = map.getJunction(this.srcJunc);
		Junction dest = map.getJunction(this.destJunc);
		InterCityRoad r = new InterCityRoad(this.id,src,dest,this.maxSpeed,this.co2Limit,this.length,this.weather);
		map.addRoad(r);
	}

	
	public String toString() {
		return "New Inter City Road '"+id+"'";
	}

}
