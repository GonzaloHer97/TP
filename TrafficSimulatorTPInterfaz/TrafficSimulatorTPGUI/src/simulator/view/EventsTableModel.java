package simulator.view;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String[] columNames = {"Time","Description"};
	private List<Event> events;
	
	public EventsTableModel(Controller ctrl) {
		ctrl.addObserver(this);
	}
	
	@Override
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
		return events == null ? 0 : events.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
			case 0:
				return this.events.get(row).get_time();
			default:
				return this.events.get(row).toString();
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableDataChanged();		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.events = events;
		fireTableDataChanged();		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableDataChanged();		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.events = events;
		fireTableDataChanged();		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
