package simulator.model;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {

	protected Junction srcJunc;
	protected Junction destJunc;
	protected int length;
	protected int maxSpeed;
	protected int limitActualSpeed;
	protected int contLimit;
	protected Weather weather;
	protected int totalCont;
	protected List<Vehicle> vehicles;
	
	Road(String id,  Junction srcJunc, Junction destJunc, int maxSpeed,	int contLimit, int length, Weather weather) throws Exception {
		super(id);
		vehicles = new ArrayList<Vehicle>();
		if(maxSpeed >= 0 && contLimit >= 0 && length >= 0 && srcJunc != null && destJunc != null && weather != null) {
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
			this.destJunc.addIncommingRoad(this);;
			this.srcJunc.addOutGoingRoad(this);
		}
		else
			throw new Exception("Error al instanciar la carretera " + id);
		
	}

	

	void advance(int time) throws Exception{
		reduceTotalContamination();
		updateSpeedLimit(); 
		
		for(int i = 0; i < this.vehicles.size();i++) {
			int v = this.calculateVehicleSpeed(this.vehicles.get(i));
			this.vehicles.get(i).setSpeed(v);
			this.vehicles.get(i).advance(time);
		}
		
		Collections.sort(vehicles, new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle v2) {
				return new Integer(v2.getLocate()).compareTo(new Integer(v1.getLocate()));
			}
		});
	}

	
	public JSONObject report() {
		JSONObject road = new JSONObject();
		JSONArray idVehicle = new JSONArray();
		
		road.put("id", this._id);
		road.put("speedlimit", this.limitActualSpeed);
		road.put("weather", this.weather);
		road.put("co2", this.totalCont);
		
		//lista de solo lectura
		for(int i = 0; i < this.vehicles.size();i++) {
			idVehicle.put(this.vehicles.get(i).getId());
		}
		
		road.put("vehicles", idVehicle);
		return road;
	}
	
	protected void enter(Vehicle v) throws Exception {
		if(v.getLocate() == 0 && v.getActualSpeed() == 0) {
			this.vehicles.add(v);
		}
		else 
			throw new Exception("Error en entrada de vehiculo a la carretera " + this._id);
	}
	
	protected void exit(Vehicle v) {
		 this.vehicles.remove(v);
	}
	
	protected void setWeather(Weather w) throws Exception {
		if(w != null) {
			this.weather = w;
		}
		else
			throw new Exception("Error al cambiar el tiempo atmosférico en " + this._id);
	}
	
	protected void addContamination(int c) throws Exception {
		if(c >= 0) {
			this.totalCont += c;
		}
		else 
			throw new Exception("Error al añadir contaminacion a la carretera " + this._id);
	}
	
	
	
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit(); 
	abstract int calculateVehicleSpeed(Vehicle v);



	public String getIdSrcJunction() {
		return this.getSrcJunc().getId();
	}
	
	public String getIdDestJunction() {
		return this.getDestJunc().getId();
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}




	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}




	public int getLimitActualSpeed() {
		return limitActualSpeed;
	}




	public void setLimitActualSpeed(int limitActualSpeed) {
		this.limitActualSpeed = limitActualSpeed;
	}




	public int getContLimit() {
		return contLimit;
	}




	public void setContLimit(int contLimit) {
		this.contLimit = contLimit;
	}




	public int getTotalCont() {
		return totalCont;
	}




	public void setTotalCont(int totalCont) {
		this.totalCont = totalCont;
	}




	public List<Vehicle> getVehicles() {
		return vehicles;
	}




	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}




	public Weather getWeather() {
		return weather;
	}
	
	
	
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}



	public Junction getSrcJunc() {
		return srcJunc;
	}



	public void setSrcJunc(Junction srcJunc) {
		this.srcJunc = srcJunc;
	}



	public Junction getDestJunc() {
		return destJunc;
	}



	public void setDestJunc(Junction destJunc) {
		this.destJunc = destJunc;
	}
	
	
}
