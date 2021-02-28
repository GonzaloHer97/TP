package simulator.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {
	
	
	
	
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel time;
	private JLabel event;
	private int actTime;
	private String newEvent;
	
	public StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	
	
	public void initGUI() {
		this.setBorder(BorderFactory.createBevelBorder(1));
		this.setLayout(new BorderLayout());
		
		time = new JLabel("  Time: "+ actTime + "        ");
		this.add(time, BorderLayout.WEST);
		
		event = new JLabel("Event added: " + newEvent);
		this.add(event);
		
		if(newEvent == null) {
			Container parent = event.getParent();
			parent.remove(event);
			parent.validate();
			parent.repaint();
		}
		
		setPreferredSize(new Dimension(this.getWidth(),30));
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this.actTime = time;
		this.time.setText(String.valueOf(this.actTime));
		newEvent = null;

		this.removeAll();
		this.initGUI();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this.actTime = time;
		this.time.setText(String.valueOf(this.actTime));
		this.newEvent = e.toString();
		
		this.removeAll();
		this.initGUI();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.actTime = time;
		this.time.setText(String.valueOf(this.actTime));
		newEvent = null;

		this.removeAll();
		this.initGUI();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.actTime = time;
		this.time.setText(String.valueOf(this.actTime));
		newEvent = null;

		this.removeAll();
		this.initGUI();
		
	}

	@Override
	public void onError(String err) {
		
	}

}
