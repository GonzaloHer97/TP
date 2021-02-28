package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		 int resultado=0;
	        
		 if (this._time <= o._time) {   resultado =  -1;      }
		 
		 else if (this._time > o._time) {    resultado = 1;      }

	     
		 return resultado;
	}

	abstract void execute(RoadMap map) throws Exception;

	public int get_time() {
		return _time;
	}

	public void set_time(int _time) {
		this._time = _time;
	}
	
	
}
