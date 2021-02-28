package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	private int time = -1;
	private List<Pair<String,Weather>> ws;
	private Pair<String,Weather> pair;
	
	public SetWeatherEventBuilder() {
		super("set_weather");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) throws Exception {
		SetWeatherEvent swe;
		ws = new ArrayList<Pair<String,Weather>>();
		this.time = data.getInt("time");
		for(int i = 0; i < data.getJSONArray("info").length();i++) {
			pair = new Pair<String, Weather>(data.getJSONArray("info").getJSONObject(i).getString("road"), Weather.valueOf(data.getJSONArray("info").getJSONObject(i).getString("weather")));
			ws.add(pair);
		}
		if(time != -1 && ws != null )
			swe = new SetWeatherEvent(time,ws);
		else 
			throw new Exception("error en el builder de SetWeatherEvent");
		
		return swe;
	}


}
