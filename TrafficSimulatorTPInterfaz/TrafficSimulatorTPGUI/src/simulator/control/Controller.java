package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;


import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;


public class Controller {
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) throws Exception	{
		if(sim == null && eventsFactory == null)
			throw new Exception("error en el constructor del controlador");
		this.sim =sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) throws Exception {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		for(int i = 0; i < jo.getJSONArray("events").length(); i++) {
			Event e = this.eventsFactory.createInstance(jo.getJSONArray("events").getJSONObject(i));
			sim.addEvent(e);
		}
	}
	
	public void run(int n, OutputStream out) throws Exception {
		String aux;
		String cabecera = "{\n" + "\"states\" : [\n" ;
		out.write(cabecera.getBytes());
		for(int i = 0; i < n;i++) {
			sim.advance();
			if(i == n-1) 
				aux = sim.report().toString() + "\n";
			else
				aux = sim.report().toString() + "," + "\n";
			
			out.write(aux.getBytes());
		}
		String fin = "] \n}";
		out.write(fin.getBytes());
		System.out.println();
	}
	
	public void run(int n) throws Exception {
		for(int i = 0; i < n;i++) {
			sim.advance();
		}
		System.out.println();
	}
	
	public void reset() {
		this.sim.reset();
	}

	public void addObserver(TrafficSimObserver o) {
		sim.addObserver(o);
		
	}
	public void removeObserver(TrafficSimObserver o) {
		sim.removeObserver(o);
	}
	public void  addEvent(Event e) {
		sim.addEvent(e);
	}

	public TrafficSimulator getSim() {
		return sim;
	}

	public void setSim(TrafficSimulator sim) {
		this.sim = sim;
	}

	public Factory<Event> getEventsFactory() {
		return eventsFactory;
	}

	public void setEventsFactory(Factory<Event> eventsFactory) {
		this.eventsFactory = eventsFactory;
	}
	
	
	

}
