package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;


public class SetContClassEventBuilder extends Builder<Event> {

	private int time = -1;
	private List<Pair<String,Integer>> ws;
	private Pair<String,Integer> pair;
	
	public SetContClassEventBuilder() {
		super("set_cont_class");
	}


	protected Event createTheInstance(JSONObject data) throws Exception {
		NewSetContClassEvent swe;
		ws = new ArrayList<Pair<String,Integer>>();
		this.time = data.getInt("time");
		for(int i = 0; i < data.getJSONArray("info").length();i++) {
			pair = new Pair<String, Integer>(data.getJSONArray("info").getJSONObject(i).getString("vehicle"), data.getJSONArray("info").getJSONObject(i).getInt("class"));
			ws.add(pair);
		}
		if(time != -1 && ws != null )
			swe = new NewSetContClassEvent(time,ws);
		else 
			throw new Exception("error en el builder de SetWeatherEvent");
		
		return swe;
	}
	
	

}
