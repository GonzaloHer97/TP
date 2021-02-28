package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id,  Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id,  srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	public void reduceTotalContamination() {
		int a = 100 - this.weatherToCon();
		double b = (double)a / 100;
		this.totalCont = (int) (b * this.totalCont);
		
	}

	
	public void updateSpeedLimit() {
		if(this.totalCont > this.contLimit)
			this.limitActualSpeed = (int) (this.maxSpeed * 0.5);
		else
			this.limitActualSpeed = this.maxSpeed;
		
	}

	public int calculateVehicleSpeed(Vehicle v)  {
		int speed = this.limitActualSpeed;
		if(this.weather == Weather.STORM)
			 speed = (int) (this.limitActualSpeed * 0.8);
		return speed;
	}
	
	private int weatherToCon() {
		int x = 0;
		if(this.weather == Weather.SUNNY)
			x = 2;
		if(this.weather == Weather.CLOUDY)
			x = 3;
		if(this.weather == Weather.RAINY)
			x = 10;
		if(this.weather == Weather.WINDY)
			x = 15;
		if(this.weather == Weather.STORM)
			x = 20;
		return x;
	}
}
