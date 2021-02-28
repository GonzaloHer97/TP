package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

	private int time = -1;
	private String id;
	private String src;
	private String dest;
	private int length = -1;
	private int co2limit = -1;
	private int maxspeed = -1;
	private String weather;
	
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws Exception {
		
		NewInterCityRoadEvent ncre;
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.src = data.getString("src");
		this.dest = data.getString("dest");
		this.length = data.getInt("length");
		this.co2limit = data.getInt("co2limit");
		this.maxspeed = data.getInt("maxspeed");
		this.weather = data.getString("weather");
		
		if(time != -1 && id != null && src != null && dest != null && length != -1 && co2limit != -1 && maxspeed != -1 && weather != null) {
			ncre = new NewInterCityRoadEvent(time,id,src,dest,length,co2limit,maxspeed,Weather.valueOf(weather));
		}
		else
			throw new Exception("Error en el builder NewInterCityRoadEvent");
		
		return ncre;
	}
	
	
}
