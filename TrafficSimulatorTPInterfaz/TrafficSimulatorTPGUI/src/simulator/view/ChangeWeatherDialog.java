package simulator.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class ChangeWeatherDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> roads;
	private JComboBox<String> weathers;
	private String[] weatherList = {"SUNNY","CLOUDY","RAINY","WINDY","STORM"};
	private DefaultComboBoxModel<String> _roadsModel;
	private JSpinner ticksSpinner;
	private int status;
	private String idR;
	private String w;
	private int ticksU;
	
	public ChangeWeatherDialog(Frame frame) {
		super(frame, true);
		initGUI();
	}
	
	public void initGUI() {
		
		setTitle("Change Road Weather");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel("<html><body>Schedule an event to change the weather class of a road after a given<P>" + "number of simulator ticks from now");
		helpMsg.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(helpMsg);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(viewsPanel);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(buttonsPanel);
		
		JLabel road = new JLabel("Road: ");
		viewsPanel.add(road);
		
		_roadsModel = new DefaultComboBoxModel<>();
		roads = new JComboBox<>(_roadsModel);
		roads.setSize(50, 10);
		viewsPanel.add(roads);
		
		JLabel weatherLabel = new JLabel("Weather: ");
		viewsPanel.add(weatherLabel);
		
		weathers = new JComboBox<>(weatherList);
		viewsPanel.add(weathers);
		
		JLabel ticks = new JLabel("Ticks: ");
		viewsPanel.add(ticks);
		
		SpinnerModel spinnermodel = new SpinnerNumberModel(1,0,null,1);
		ticksSpinner = new JSpinner(spinnermodel);
		viewsPanel.add(ticksSpinner);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_roadsModel.getSelectedItem() != null && weathers.getSelectedItem() != null) {
					status = 1;
					idR = (String) roads.getSelectedItem();
					w = (String) weathers.getSelectedItem();
					ticksU = (int) ticksSpinner.getValue();
					ChangeWeatherDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);
		
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
		
	}
	
	
	public int open(List<String> roads) {
		_roadsModel.removeAllElements();
		for(String r : roads)
			_roadsModel.addElement(r);
		
		setLocation(this.getWidth(), this.getHeight());
		
		setVisible(true);
		return status;
	}
	
	public String getIdR() {
		return idR;
	}
	
	public void setIdR(String idR) {
		this.idR = idR;
	}
	
	public String getW() {
		return w;
	}
	
	public void setW(String w) {
		this.w = w;
	}
	
	public int getTicksU() {
		return ticksU;
	}
	
	public void setTicksU(int ticksU) {
		this.ticksU = ticksU;
	}

}
