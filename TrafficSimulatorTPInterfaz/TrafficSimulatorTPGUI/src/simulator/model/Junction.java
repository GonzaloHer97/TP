package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	
	
	
	private List<Road> roads;
	private Map<Junction,Road> map;
	private Map<Road, List<Vehicle>> qMap;
	private List<List<Vehicle>> qs;
	private int currGreen;
	private int switchCurrGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	

	protected Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws Exception {
		super(id);
		this.roads = new ArrayList<Road>();
		this.qs = new ArrayList<List<Vehicle>>();
		this.map = new HashMap<Junction,Road>();
		this.qMap = new HashMap<Road, List<Vehicle>>();
		
		if(lsStrategy != null && dqStrategy != null && xCoor > 0 && yCoor > 0 ) {
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
			this.switchCurrGreen = 1;
		}
		else
			throw new Exception("Error al instanciar un juction " + id);
		
	}
	
	
	public void addIncommingRoad(Road r) throws Exception {
		if(r.getIdDestJunction().contentEquals(this.getId())) {
			this.roads.add(r);
			List<Vehicle> v = new ArrayList<Vehicle>();
			this.qs.add(v);
			this.qMap.put(r, v);
		}
		else
			throw new Exception("error al añadir carretera entrante " + r.getId());
	}
	
	public void  addOutGoingRoad(Road r) throws Exception {
	
		if(!this.map.containsKey(r.getDestJunc()) && r.getIdSrcJunction().contentEquals(this.getId()))
			this.map.put(r.getDestJunc(), r);
		else
			throw new Exception("error al añadir carretera saliente " + r.getId());
		
	}
	
	void enter(Vehicle v) {
		for(int i = 0; i < this.roads.size();i++) {
			if(v.idRoad().contentEquals(roads.get(i).getId()))
				qs.get(i).add(v);
		}
	}

	
	public Road roadTo(Junction j) {
		return this.map.get(j);
	}
	
	
	void advance(int time) throws Exception {
		if(this.currGreen!= -1 && !this.qs.isEmpty() && !this.qs.get(this.currGreen).isEmpty()) {
			List<Vehicle> auxList = this.dqStrategy.dequeue(this.qs.get(this.currGreen));
			for(int j = 0; j < auxList.size();j++) {
				auxList.get(j).moveToNextRoad();
				this.qs.get(this.currGreen).remove(j);
			}
		}
		int green = this.lsStrategy.chooseNextGreen(this.roads, this.qs, this.currGreen, this.switchCurrGreen, time);
		if(green != this.currGreen) {
			this.currGreen = green;
			this.switchCurrGreen = time;
		}
	}

	public JSONObject report() {
		JSONObject junctionJSON = new JSONObject();
		JSONArray queues =  new JSONArray();
		junctionJSON.put("id", this._id);
		if(this.currGreen != -1 && this.roads.size() != 0)
			junctionJSON.put("green", this.roads.get(this.currGreen).getId());
		else
			junctionJSON.put("green", "none");
		
		for(int i = 0; i < this.roads.size();i++) {
			queues.put(this.reportRoads(i));
		}
		
		junctionJSON.put("queues", queues);
		
		return junctionJSON;
	}
	
	private JSONObject reportRoads(int i) {
		JSONObject roads = new JSONObject();
		JSONArray vehiclesJSON = new JSONArray();
		roads.put("road", this.roads.get(i).getId());
		for (int j = 0; j < qs.get(i).size(); j++) {
			vehiclesJSON.put(this.qs.get(i).get(j).report().getString("id"));
		}
		roads.put("vehicles", vehiclesJSON);
		return roads;
	}


	public List<Road> getRoads() {
		return roads;
	}


	public void setRoads(List<Road> roads) {
		this.roads = roads;
	}


	public Map<Junction, Road> getMap() {
		return map;
	}


	public void setMap(Map<Junction, Road> map) {
		this.map = map;
	}


	public List<List<Vehicle>> getQs() {
		return qs;
	}


	public void setQs(List<List<Vehicle>> qs) {
		this.qs = qs;
	}


	public int getCurrGreen() {
		return currGreen;
	}


	public void setCurrGreen(int currGreen) {
		this.currGreen = currGreen;
	}


	public int getSwitchCurrGreen() {
		return switchCurrGreen;
	}


	public void setSwitchCurrGreen(int switchCurrGreen) {
		this.switchCurrGreen = switchCurrGreen;
	}


	public LightSwitchingStrategy getLsStrategy() {
		return lsStrategy;
	}


	public void setLsStrategy(LightSwitchingStrategy lsStrategy) {
		this.lsStrategy = lsStrategy;
	}


	public DequeuingStrategy getDqStrategy() {
		return dqStrategy;
	}


	public void setDqStrategy(DequeuingStrategy dqStrategy) {
		this.dqStrategy = dqStrategy;
	}


	public int getxCoor() {
		return xCoor;
	}


	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}


	public int getyCoor() {
		return yCoor;
	}


	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}


	public Map<Road, List<Vehicle>> getqMap() {
		return qMap;
	}


	public void setqMap(Map<Road, List<Vehicle>> qMap) {
		this.qMap = qMap;
	}

}
