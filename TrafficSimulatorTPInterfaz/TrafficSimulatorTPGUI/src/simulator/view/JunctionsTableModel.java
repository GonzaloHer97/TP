package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columNames= {"id", "Green", "Queues"};
	private List<Junction> junctions;
	
	
	public JunctionsTableModel(Controller ctrl) {
		this.junctions = new ArrayList<Junction>();
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
		return this.junctions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex) {
			case 0:
				return this.junctions.get(rowIndex).getId();
			case 1:
				return this.greenRoad(this.junctions.get(rowIndex));
			default:
				return this.queues(this.junctions.get(rowIndex));
		}
	}
	
	
	private String greenRoad(Junction j) {
		if(j.getCurrGreen() == -1)
			return "NONE";
		else
			return j.getRoads().get(j.getCurrGreen()).getId();
	}
	
	private String queues(Junction j) {
		String r = null;
		if(j.getRoads().size() == 0 || j.getRoads() == null)
			return "";
		else {
			r="";
			for(int i = 0; i < j.getRoads().size();i++) {
				 r += j.getRoads().get(i).getId() + ":[";
				 if( j.getRoads().get(i).getVehicles().size() != 0 ||  j.getRoads().get(i).getVehicles() != null) {
					 for(int x = 0; x < j.getRoads().get(i).getVehicles().size();x++) {	
							r += j.getRoads().get(i).getVehicles().get(x).getId();
							if(x < j.getRoads().get(i).getVehicles().size() - 1)
								r += ",";
						}
				}
				r += "] ";
			}
			return r;
		}
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableDataChanged();	
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {	
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableDataChanged();	
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.junctions = map.getJunctions();
		fireTableDataChanged();	
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
