package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columNames= {"id", "Location", "Itinerary", "CO2 Class", "MAX Speed", "Speed", "Total CO2", "Distance"};
	private List<Vehicle> vehicles;
	
	public VehiclesTableModel(Controller ctrl) {
		this.vehicles = new ArrayList<Vehicle>();
		ctrl.addObserver(this);
	}
	
	
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public String getColumnName(int col) {
		return columNames[col];
	}

	@Override
	public int getColumnCount() {
		return this.columNames.length;
	}

	@Override
	public int getRowCount() {
		return this.vehicles.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0:
				return this.vehicles.get(rowIndex).getId();
			case 1:
				if(this.vehicles.get(rowIndex).getState() == VehicleStatus.ARRIVED)
					return "Arrived";
				return this.vehicles.get(rowIndex).getRoad().getId() + ":" + this.vehicles.get(rowIndex).getLocate();
			case 2:
				return this.vehicles.get(rowIndex).getItinerary();
			case 3:
				return this.vehicles.get(rowIndex).getContClass();
			case 4:
				return this.vehicles.get(rowIndex).getMaxSpeed();
			case 5:
				return this.vehicles.get(rowIndex).getActualSpeed();
			case 6:
				return this.vehicles.get(rowIndex).getTotalContamination();
			default:
				return this.vehicles.get(rowIndex).getTotalDistance();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {	
		this.vehicles = map.getVehicles();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableDataChanged();		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.vehicles = map.getVehicles();
		fireTableDataChanged();
	}

	@Override
	public void onError(String err) {
		
	}

}
