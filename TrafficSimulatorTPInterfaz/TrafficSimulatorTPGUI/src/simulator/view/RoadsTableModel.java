package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columNames= {"id", "Length", "Weather", "Max. Speed", "Sped Limit", "Total CO2", "CO2 Limit"};
	private List<Road> roads;
	
	
	public RoadsTableModel(Controller ctrl) {
		this.roads = new ArrayList<Road>();
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
		return this.roads.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0:
				return this.roads.get(rowIndex).getId();
			case 1:
				return this.roads.get(rowIndex).getLength();
			case 2:
				return this.roads.get(rowIndex).getWeather().toString();
			case 3:
				return this.roads.get(rowIndex).getMaxSpeed();
			case 4:
				return this.roads.get(rowIndex).getLimitActualSpeed();
			case 5:
				return this.roads.get(rowIndex).getTotalCont();
			default:
				return this.roads.get(rowIndex).getContLimit();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableDataChanged();	
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
	
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableDataChanged();	
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.roads = map.getRoads();
		fireTableDataChanged();	
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
