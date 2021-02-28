package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	
	private int timeSlot;
	
	public MostCrowdedStrategy(int timeSlot){
		this.timeSlot = timeSlot;
	}
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,	int currTime) {
		
		if(roads.isEmpty())
			return -1;
		else if(currGreen == -1) 
			return longestRoad(qs);
		else if((currTime-lastSwitchingTime) < timeSlot)
			return currGreen;
		else {
			int pos = (currGreen + 1) % roads.size();
			return search(qs,pos);
		}
	}
	
	private int longestRoad(List<List<Vehicle>> qs) {
		int ind = 0;
		for(int i = 1; i < qs.size();i++) {
			if(qs.get(i - 1).size() < qs.get(i).size())
				ind = i;
		}
		return ind;
	}
	
	private int search(List<List<Vehicle>> qs, int pos) {
		int ind = pos;
		int i = 0;
		
		do {
			if(pos + 1 == qs.size()) {
				 if(qs.get(pos).size() < qs.get(0).size())
					ind = 0;
				 pos = 0;
			}	
			else if(qs.get(pos).size() < qs.get(pos + 1).size()) {
				ind = pos + 1;
				pos++;
			}
			i++;
		}while(i <= qs.size());
		
		return ind;
	}

}
