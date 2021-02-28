package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		
	}

	
	void reduceTotalContamination() {
		this.totalCont -= this.weatherToCon();
		if(this.totalCont < 0)
			this.totalCont = 0;
	}

	@Override
	void updateSpeedLimit() {
		this.limitActualSpeed = this.maxSpeed;
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int a = 11 - v.getContClass();
		double b = (double)a / 11;
		int speed = (int) (b * this.limitActualSpeed);
		return speed;
	}

	
	private int weatherToCon() {
		int x = 0;
		if(this.weather == Weather.WINDY || this.weather == Weather.STORM )
			x = 10;
		else
			x = 2;
		return x;
	}
}
