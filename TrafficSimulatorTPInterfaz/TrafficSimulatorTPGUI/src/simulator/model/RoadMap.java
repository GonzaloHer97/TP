package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> junList;
	private List<Road> roadList;
	private List<Vehicle> vehiList;
	private Map<String,Junction> junMap;
	private Map<String,Road> roadMap;
	private Map<String,Vehicle> vehiMap;
	
	
	
	 public RoadMap( List<Junction> junList, List<Road> roadList, List<Vehicle> vehiList, Map<String,Junction> junMap, Map<String,Road> roadMap, Map<String,Vehicle> vehiMap){
		this.junList = junList;
		this.roadList = roadList;
		this.vehiList = vehiList;
		this.junMap = junMap;
		this.roadMap = roadMap;
		this.vehiMap = vehiMap;
	}
	
	 void addJunction(Junction j) {
		 if(!this.junMap.containsKey(j.getId())) {
			 this.junList.add(j);
			 this.junMap.put(j.getId(), j);
		 }
	 }
	 
	 void addRoad(Road r) throws Exception {
		 if(!this.roadMap.containsKey(r.getId()) && this.junMap.containsKey(r.getIdDestJunction()) && this.junMap.containsKey(r.getIdSrcJunction())) {
			 this.roadList.add(r);
			 this.roadMap.put(r.getId(), r);
		 }
		 else 
			 throw new Exception("error al añadir la carretera " + r.getId() + " al mapa");
			 
	 }
	 
	 void addVehicle(Vehicle v) throws Exception {
		 
		 if(!this.vehiMap.containsKey(v.getId()) && this.itinerariVehicle(v)) {
			 this.vehiList.add(v);
			 this.vehiMap.put(v.getId(), v);
		 }
		 else
			 throw new Exception("error al añadir el vehiculo " + v.getId() + " al mapa");

		 
	 }
	 
	 private boolean itinerariVehicle(Vehicle v) {
		 for(int i = 0; i < v.getItinerary().size();i++) {
			 if(!this.junMap.containsValue(v.getItinerary().get(i)))
				 return false;
		 }
		return true;
		 
	 }
	 
	 
	 public Junction getJunction(String id) {
		 return this.junMap.get(id);
	 }
	 
	 public Road getRoad(String id) {
		 return this.roadMap.get(id);
	 }
	 
	 public Vehicle getVehicle(String id) {
		 return this.vehiMap.get(id);
	 }
	 
	 public List<Junction>getJunctions(){
		 return Collections.unmodifiableList(new ArrayList<>(this.junList));
	 }
	 public List<Road>getRoads(){
		 return Collections.unmodifiableList(new ArrayList<>(this.roadList));
	 }
	 
	 public List<Vehicle>getVehicles(){
		 return Collections.unmodifiableList(new ArrayList<>(this.vehiList));
	 }
	 
	 void reset() {
		 this.junList.clear();
		 this.junMap.clear();
		 this.roadList.clear();
		 this.roadMap.clear();
		 this.vehiList.clear();
		 this.vehiMap.clear();
	 }
	 
	 public JSONObject report() {
		JSONObject roadMapJSON =  new JSONObject();
		JSONArray vehiclesJSON = new JSONArray();
		JSONArray roadJSON = new JSONArray();
		JSONArray junctionJSON = new JSONArray();
		for(int i = 0; i < this.junList.size();i++) {
			if(this.junList.size() > i)
				junctionJSON.put(this.junList.get(i).report());
			if(this.roadList.size() > i)
				roadJSON.put(this.roadList.get(i).report());
			if(this.vehiList.size() > i)
				vehiclesJSON.put(this.vehiList.get(i).report());
			
		}
		roadMapJSON.put("junctions", junctionJSON);
		roadMapJSON.put("roads", roadJSON);
		roadMapJSON.put("vehicles", vehiclesJSON);
		return roadMapJSON;
	 }
	 
}
