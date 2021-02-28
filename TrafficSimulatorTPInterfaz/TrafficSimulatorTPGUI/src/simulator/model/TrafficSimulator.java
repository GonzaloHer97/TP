package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;



public class TrafficSimulator implements Observable<TrafficSimObserver> {
	private RoadMap roadM;
	private List<Event> lEvent;
	private List<TrafficSimObserver> observadores;
	private int time;
	
	public TrafficSimulator(RoadMap roadM, List<Event> lEvent, int time){
		this.roadM = roadM;
		this.lEvent = lEvent;
		this.time = time;
		this.observadores = new ArrayList<TrafficSimObserver>();
	}

	public void addEvent(Event e) {
		
		this.lEvent.add(e);
		for(TrafficSimObserver tfo : observadores)tfo.onEventAdded(roadM, lEvent, e, time);
	}
	
	
	public void advance() throws Exception {
		this.time++;
		
		for(TrafficSimObserver tfo : observadores)tfo.onAdvanceStart(roadM, lEvent, time);
		
		Iterator<Event> it = this.lEvent.iterator();
		
		while(it.hasNext()) {
				Event e = it.next();
				if(e.getTime() == time) {
					try {
						e.execute(roadM);
						it.remove();
					}catch(Exception ex) {
						for(TrafficSimObserver tfo : observadores)tfo.onError(ex.getMessage());
						throw new Exception(ex.getMessage());
					}
				}
		}
		
		try {
			for(Junction j : this.roadM.getJunctions()) {
				j.advance(time);
			}
			
			for(Road r : this.roadM.getRoads()) {
				r.advance(time);
			}
		}catch(Exception e) {
			for(TrafficSimObserver tfo : observadores)tfo.onError(e.getMessage());
			throw new Exception(e.getMessage());
		}
		for(TrafficSimObserver tfo : observadores)tfo.onAdvanceEnd(roadM, lEvent, time);
	}
	
	public void reset() {
		this.roadM.reset();
		this.lEvent.clear();
		this.time = 0;
		for(TrafficSimObserver tfo : observadores)tfo.onReset(roadM, lEvent, time);
	}
	
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		this.observadores.add(o);
		o.onRegister(roadM, lEvent, time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observadores.remove(o);
	}
	
	public JSONObject report() {
		JSONObject tfsJSON = new JSONObject();
		tfsJSON.put("time", this.time);
		tfsJSON.put("state", this.roadM.report());
		return tfsJSON;
		
	}
	
	public RoadMap getRoadM() {
		return roadM;
	}

	public void setRoadM(RoadMap roadM) {
		this.roadM = roadM;
	}

	public List<Event> getlEvent() {
		return lEvent;
	}

	public void setlEvent(List<Event> lEvent) {
		this.lEvent = lEvent;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}


	
	
	
	
	
}
