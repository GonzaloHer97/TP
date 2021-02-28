package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
	
	private String id;
	private int contClass;
	private int maxSpeed;
	private List<String> itinerary;
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		this.id = id;
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.itinerary = itinerary;
	}

	void execute(RoadMap map) throws Exception {
		
		List<Junction> itine = new ArrayList<Junction>();
		for(int i = 0; i < this.itinerary.size();i++) {
			itine.add(map.getJunction(this.itinerary.get(i)));
		}
		
		Vehicle v = new Vehicle(this.id,this.maxSpeed,this.contClass,itine);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
	
	public String toString() {
		return "New Vehicle '"+id+"'";
	}

}
