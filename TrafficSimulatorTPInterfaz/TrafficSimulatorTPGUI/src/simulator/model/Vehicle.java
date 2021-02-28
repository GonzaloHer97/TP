package simulator.model;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {

	private List<Junction> itinerary;
	private int lastItinerary;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus state;
	private Road road;
	private int locate;
	private int contClass;
	private int totalContamination;
	private int totalDistance;
	
	Vehicle(String id, int maxSpeed, int pollution, List<Junction> itinerary) throws Exception {
		super(id);
		this.state = VehicleStatus.PENDING;
		if(maxSpeed > 0 && 0 <= pollution && pollution <= 10 && itinerary.size() >= 2) {
			this.maxSpeed = maxSpeed;
			this.contClass = pollution;
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			this.lastItinerary = 0;
			this.totalContamination = 0;
			this.totalDistance = 0;
			this.actualSpeed = 0;
		}
		else
			throw new Exception("Error alinstanciar el vehículo " + id);
	}
	
	void advance(int time) throws Exception {
		
		if(this.state == VehicleStatus.TRAVELING) {
			int actualMove = this.locate + this.actualSpeed;
			int disMove = this.totalDistance + this.actualSpeed;
			if(actualMove < this.road.getLength()) {
				this.locate = actualMove;
				int c = this.contClass * this.actualSpeed;
				this.totalContamination += c;
				this.road.addContamination(c);
				this.totalDistance = disMove;
			}
			else {
			    int distance = this.road.getLength() - this.locate;
				this.locate = this.road.getLength();
				int c = this.contClass * distance;
				this.totalContamination += c;
				this.totalDistance += distance;
				this.road.addContamination(c);
				this.state = VehicleStatus.WAITING;
				this.itinerary.get(this.lastItinerary).enter(this);
			}
				
		}
		
	}
	
	protected void moveToNextRoad() throws Exception {
		this.locate = 0;
		this.actualSpeed = 0;
		if(this.state == VehicleStatus.PENDING || this.state == VehicleStatus.WAITING) {
			if(this.state == VehicleStatus.PENDING) {
				road = this.itinerary.get(0).roadTo(this.itinerary.get(1));
				road.enter(this);
				this.state = VehicleStatus.TRAVELING;
				this.lastItinerary++;
			}
			else {
				this.road.exit(this);
				if(this.lastItinerary == this.itinerary.size() - 1) {
					this.state = VehicleStatus.ARRIVED;
				}
				else if(this.lastItinerary < this.itinerary.size()) {
					road = this.itinerary.get(this.lastItinerary).roadTo(this.itinerary.get(this.lastItinerary + 1));
					road.enter(this);
					this.state = VehicleStatus.TRAVELING;
					this.lastItinerary++;
				}
			}
		}
		else
			throw new Exception("Error al mover vehiculo a la carretera " + road.getId());
	}

	public JSONObject report() {
		JSONObject vehicle = new JSONObject();
		
		vehicle.put("id", this._id);
		vehicle.put("speed", this.actualSpeed);
		vehicle.put("distance", this.totalDistance);
		vehicle.put("co2", this.totalContamination);
		vehicle.put("class", this.contClass);
		vehicle.put("status", this.state);
		
		if(this.state != VehicleStatus.ARRIVED && this.state != VehicleStatus.PENDING) {
			vehicle.put("road", this.road.getId());
			vehicle.put("location", this.locate);
		}
		return vehicle;
	}
	
	
	
	protected void setSpeed(int s) throws Exception {
		if(s >= 0) {
			if(s < this.maxSpeed)
				this.actualSpeed=s;
			else
				this.actualSpeed = this.maxSpeed;
		}
		else
			throw new Exception("Valor negativo al cambiar la velocidad de " + this.getId());
	}
	
	protected void setContaminationClass(int c) throws Exception {
		if(c >= 0 && c <= 10)
			this.contClass = c;
		else
			throw new Exception("Error al cambiar la contaminacion de" + this.getId());
			
	}
	

	public String idRoad() {
		return this.road.getId();
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public int getActualSpeed() {
		return actualSpeed;
	}

	public void setActualSpeed(int actualSpeed) {
		this.actualSpeed = actualSpeed;
	}

	public VehicleStatus getState() {
		return state;
	}

	public void setState(VehicleStatus state) {
		this.state = state;
	}

	public Road getRoad() {
		return road;
	}

	public void setRoad(Road road) {
		this.road = road;
	}

	public int getLocate() {
		return locate;
	}

	public void setLocate(int locate) {
		this.locate = locate;
	}

	public int getContClass() {
		return contClass;
	}

	public void setContClass(int contClass) {
		this.contClass = contClass;
	}

	public int getTotalContamination() {
		return totalContamination;
	}

	public void setTotalContamination(int totalContamination) {
		this.totalContamination = totalContamination;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public List<Junction> getItinerary() {
		return this.itinerary;
	}

	public void setItinerary(List<Junction> itinerary) {
		this.itinerary = itinerary;
	}

	public int getLastItinerary() {
		return lastItinerary;
	}

	public void setLastItinerary(int lastItinerary) {
		this.lastItinerary = lastItinerary;
	}
	
	

}
