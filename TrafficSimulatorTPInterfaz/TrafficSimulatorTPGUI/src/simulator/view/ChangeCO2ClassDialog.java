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



public class ChangeCO2ClassDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> vehicles;
	private JComboBox<Integer> conClass;
	private Integer[] co2list = {0,1,2,3,4,5,6,7,8,9,10};
	private DefaultComboBoxModel<String> _vehiclesModel;
	private JSpinner ticksSpinner;;
	private int status;
	private String idV;
	private int co2class;
	private int ticksU;
	
	public ChangeCO2ClassDialog(Frame frame) {
		super(frame, true);
		initGUI();
	}
	
	public void initGUI() {
		
		setTitle("Change CO2 Class");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JLabel helpMsg = new JLabel("<html><body>Schedule an event to change the CO2 class of a vehicle after a given number of simulation ticks from now");
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
		
		
		JLabel vehicle = new JLabel("Vehicle: ");
		viewsPanel.add(vehicle);
		
		_vehiclesModel = new DefaultComboBoxModel<>();
		vehicles = new JComboBox<>(_vehiclesModel);
		vehicles.setSize(50, 10);
		viewsPanel.add(vehicles);
		
		JLabel co2 = new JLabel("CO2 Class: ");
		viewsPanel.add(co2);
		
		conClass = new JComboBox<Integer>(co2list);
		viewsPanel.add(conClass);
		
		JLabel ticks = new JLabel("ticks: ");
		viewsPanel.add(ticks);
		
		SpinnerModel spinnermodel = new SpinnerNumberModel(1,0,null,1);
		ticksSpinner = new JSpinner(spinnermodel);
		viewsPanel.add(ticksSpinner);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_vehiclesModel.getSelectedItem() != null && conClass.getSelectedItem() != null) {
					status = 1;
					idV = (String) vehicles.getSelectedItem();
					co2class = (int) conClass.getSelectedItem();
					ticksU = (int) ticksSpinner.getValue();
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);
		
		
		setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	
	public int open(List<String> vehicles) {

		// update the comboxBox model -- if you always use the same no
		// need to update it, you can initialize it in the constructor.
		//
		_vehiclesModel.removeAllElements();
		for (String v : vehicles)
			_vehiclesModel.addElement(v);

		// You can chenge this to place the dialog in the middle of the parent window.
		// It can be done using uing getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		setLocation(this.getWidth() , this.getHeight());

		setVisible(true);
		return status;
	}

	public String getIdV() {
		return idV;
	}

	public void setIdV(String idV) {
		this.idV = idV;
	}

	public int getCo2class() {
		return co2class;
	}

	public void setCo2class(int co2class) {
		this.co2class = co2class;
	}

	public int getTicksU() {
		return ticksU;
	}

	public void setTicksU(int ticksU) {
		this.ticksU = ticksU;
	}
	
	
	
	

}
