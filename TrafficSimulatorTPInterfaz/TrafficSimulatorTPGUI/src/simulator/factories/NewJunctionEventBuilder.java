package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {

	
	private int time = -1;
	private String id; 
	private int xCoor = -1;
	private int yCoor = -1;
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}



	protected Event createTheInstance(JSONObject data) throws Exception {
		this.time = data.getInt("time");
		this.id = data.getString("id");
		this.xCoor = data.getJSONArray("coor").getInt(0);
		this.yCoor = data.getJSONArray("coor").getInt(1);
		this.lsStrategy = this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));
		this.dqStrategy = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		NewJunctionEvent njEvent;
		
		if(this.time != -1 && id != null && xCoor != -1 && yCoor != -1 && lsStrategy != null && dqStrategy != null) {
			njEvent = new NewJunctionEvent(this.time,this.id,this.lsStrategy,this.dqStrategy,this.xCoor,this.yCoor);
		}
		else
			throw new Exception("error en el builder NewJunctionEvent");
		
		return njEvent;
	}
	
	

}
