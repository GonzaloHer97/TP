package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {

	private List<Pair<String, Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) throws Exception {
		super(time);
		this.cs = cs;
		if(this.cs == null)
			throw new Exception("Error al cargar los datos de la nueva contaminación");
	}

	@Override
	void execute(RoadMap map) throws Exception {
		for(Pair<String, Integer> cv : cs) {
			try {
			map.getVehicle(cv.getFirst()).setContaminationClass(cv.getSecond());
			}catch (Exception e) {
				throw new Exception("Error al cambiar el nivel de contaminación de " + cv.getFirst());
			}
		}
		
	}
	
	public String toString() {
		String changes = "";
		for(int i = 0; i < cs.size();i++) {
			changes += "(";
			changes += cs.get(i).getFirst() + ",";
			changes += cs.get(i).getSecond() + ")";
			if(i < cs.size() - 1) {
				changes += ", ";
			}
		}
		return "Change C02 Class: ["+changes+"]";
	}

}
