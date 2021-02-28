package simulator.factories;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	
	private int time = -1;
	private String id;
	private int maxspeed = -1;
	private int clas = -1;
	private String itinerary;
	private List<String> lItinerary;

	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws Exception {
		NewVehicleEvent nve;
		this.lItinerary = new ArrayList<String>();
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.maxspeed = data.getInt("maxspeed");
		this.clas = data.getInt("class");
		for(int i = 0; i < data.getJSONArray("itinerary").length();i++) {
			this.itinerary = data.getJSONArray("itinerary").getString(i);
			this.lItinerary.add(itinerary);
		}
		if(time != -1 && id != null && maxspeed != -1 && clas != -1 && lItinerary != null)
			nve = new NewVehicleEvent(time,id,maxspeed,clas,lItinerary);
		else
			throw new Exception("error en el builder de NewVehicleEvent");
		return nve;
	}
	

}
