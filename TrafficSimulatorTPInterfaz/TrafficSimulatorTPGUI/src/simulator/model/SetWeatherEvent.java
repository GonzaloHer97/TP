package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	
	private List<Pair<String,Weather>> ws;

	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) throws Exception {
		super(time);
		this.ws = ws;
		if(this.ws == null)
			throw new Exception("Error al cargar los datos para cambiar el tiempo");
			
	}

	@Override
	void execute(RoadMap map) throws Exception {
		
			for(Pair<String,Weather> cv :  ws) {
				try {
				map.getRoad(cv.getFirst()).setWeather(cv.getSecond());
				}catch(Exception e) {
					throw new Exception("Error al cambiar el tiempo de " + cv.getFirst());
				}
			}
		
		
	}

	public String toString() {
		String changes = "";
		for(int i = 0; i < ws.size();i++) {
			changes += "(";
			changes += ws.get(i).getFirst() + ",";
			changes += ws.get(i).getSecond() + ")";
			if(i < ws.size() - 1) {
				changes += ", ";
			}
		}
		return "ChangeWeather: ["+changes+"]";
	}
}
