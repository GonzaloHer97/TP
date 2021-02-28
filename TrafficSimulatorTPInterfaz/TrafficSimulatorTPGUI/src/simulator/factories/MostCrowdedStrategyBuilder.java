package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;


public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss");
		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int defecttime = 1;
		int timeslot = -1;
		MostCrowdedStrategy mcStrategy = new MostCrowdedStrategy(defecttime);
		timeslot = data.getInt("timeslot");
		if(timeslot != -1) {
			mcStrategy = new MostCrowdedStrategy(data.getInt("timeslot"));
		}
		return mcStrategy;
	}

}
