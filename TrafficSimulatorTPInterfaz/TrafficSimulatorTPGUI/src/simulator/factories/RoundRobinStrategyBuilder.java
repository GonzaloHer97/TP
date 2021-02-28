package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

	public RoundRobinStrategyBuilder() {
		super("round_robin_lss");
		
	}

	@Override
	protected LightSwitchingStrategy createTheInstance(JSONObject data) {
		int defecttime = 1;
		int timeslot = -1;
		timeslot = data.getInt("timeslot");
		RoundRobinStrategy lsStrategy = new RoundRobinStrategy(defecttime);
		
		if( timeslot != -1) {
			lsStrategy = new RoundRobinStrategy(data.getInt("timeslot"));
		}
		return lsStrategy;
	}

}
