package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private JButton fileButton;
	private JFileChooser fileChooser;
	private JButton co2Button;
	private JButton weatherButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	private JSpinner ticksSpinner;
	private JToolBar toolBar;
	private Boolean _stopped = true;
	
	public ControlPanel(Controller ctrl) {
		this.ctrl = ctrl;
		this.ctrl.addObserver(this);
		initGUI();
	}

	
	public void initGUI() {
		this.setLayout(new BorderLayout());
		toolBar = new JToolBar();
		this.add(toolBar);
		
		
		fileButton = new JButton();
		fileButton.setToolTipText("Load file");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filesButtonListener();
			}
		});
		fileButton.setIcon(new ImageIcon("resources/Icons/open.png"));
		toolBar.add(this.fileButton);
		
		toolBar.addSeparator();
		co2Button = new JButton();
		co2Button.setToolTipText("New event CO2 Class");
		co2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					contClassButtonListener();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		co2Button.setIcon(new ImageIcon("resources/Icons/co2class.png"));
		toolBar.add(this.co2Button);
		
		
		weatherButton = new JButton();
		weatherButton.setToolTipText("New event Weather");
		weatherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					weatherButtonListener();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		weatherButton.setIcon(new ImageIcon("resources/Icons/weather.png"));
		toolBar.add(weatherButton);
		
		toolBar.addSeparator();
		runButton = new JButton();
		runButton.setToolTipText("Run aplication");
		runButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				enableToolBar(false);
				_stopped = false;
				run_sim((int) ticksSpinner.getValue());
				if(_stopped)
					enableToolBar(true);
			}
			
		});
		runButton.setIcon(new ImageIcon("resources/Icons/run.png"));
		toolBar.add(runButton);
		
		stopButton = new JButton();
		stopButton.setToolTipText("Stop aplication");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
			
		});
		stopButton.setIcon(new ImageIcon("resources/Icons/stop.png"));
		toolBar.add(stopButton);
		
		JLabel ticks = new JLabel("Ticks: ");
		toolBar.add(ticks);
		
		SpinnerModel spinnermodel = new SpinnerNumberModel(1,0,null,1);
		this.ticksSpinner = new JSpinner(spinnermodel);
		ticksSpinner.setMaximumSize(new Dimension(100,40));
		ticksSpinner.setMinimumSize(new Dimension(100,40));
		ticksSpinner.setPreferredSize(new Dimension(100,40));
		toolBar.add(ticksSpinner);
		
		
		toolBar.add(Box.createGlue());
		exitButton = new JButton();
		exitButton.setToolTipText("Exit aplication");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exitButtonListener();
			}
		});
		exitButton.setIcon(new ImageIcon("resources/Icons/exit.png"));
		toolBar.add(exitButton);

		
		this.setSize(800, 800);
		this.setVisible(true);
	}
	

	public void filesButtonListener() {
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("C:\\hlocal\\TrafficSimulatorTPInterfaz\\TrafficSimulatorTPGUI\\resources\\examples"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setVisible(true);
		int v = fileChooser.showOpenDialog(null);
		if (v == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream in = new FileInputStream(fileChooser.getSelectedFile());
				ctrl.reset();
				ctrl.loadEvents(in);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(toolBar),"Wrong file", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
	public void contClassButtonListener() throws Exception {
		ChangeCO2ClassDialog conClassDialog = new ChangeCO2ClassDialog((Frame)SwingUtilities.getWindowAncestor(this));
		List<String> vehicles = new ArrayList<String>(ctrl.getSim().getRoadM().getVehicles().size());
	
		for(Vehicle v : ctrl.getSim().getRoadM().getVehicles()) {
			vehicles.add(v.getId());
		}
		int status = conClassDialog.open(vehicles);
		if (status != 0) {
			String id = conClassDialog.getIdV();
			int co2 = conClassDialog.getCo2class();
			int ticksU = conClassDialog.getTicksU();
			List<Pair<String,Integer>> ws =  new ArrayList<Pair<String,Integer>>();
			Pair<String, Integer> cs = new Pair<String, Integer>(id,co2);
			ws.add(cs);
			NewSetContClassEvent co2Event = new NewSetContClassEvent((ctrl.getSim().getTime() + ticksU), ws);
			ctrl.addEvent(co2Event);		
		} 
	}
	
	public void weatherButtonListener() throws Exception {
		ChangeWeatherDialog weatherDialog = new ChangeWeatherDialog((Frame)SwingUtilities.getWindowAncestor(this));
		List<String> roads = new ArrayList<String>(ctrl.getSim().getRoadM().getRoads().size());
		
		for(Road r : ctrl.getSim().getRoadM().getRoads()) {
			roads.add(r.getId());
		}
		int status = weatherDialog.open(roads);
		if(status != 0) {
			String id = weatherDialog.getIdR();
			String weather = weatherDialog.getW();
			int ticksU = weatherDialog.getTicksU();
			List<Pair<String, Weather>> ws = new ArrayList<Pair<String, Weather>>();
			Pair <String, Weather> cs = new Pair <String, Weather>(id, Weather.valueOf(weather));
			ws.add(cs);
			SetWeatherEvent weatherEvent = new SetWeatherEvent(ctrl.getSim().getTime() + ticksU, ws);
			ctrl.addEvent(weatherEvent);
		}
	}
	
	

	public void exitButtonListener() {
		int opt=JOptionPane.YES_NO_OPTION;
		opt=JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(toolBar), "Are you sure you want to exit?", "EXIT", opt);
		if(opt==JOptionPane.YES_OPTION)
			System.exit(0);
	}
		
	
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(toolBar),e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				ctrl.reset();
				_stopped = true;
				
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
			} else {
				enableToolBar(true);
				_stopped = true;
			}
	}
	
	private void enableToolBar(boolean b) {
		this.fileButton.setEnabled(b);;
		this.co2Button.setEnabled(b);
		this.weatherButton.setEnabled(b);
		this.runButton.setEnabled(b);
		this.ticksSpinner.setEnabled(b);
		
	}


	private void stop() {
		_stopped = true;
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
