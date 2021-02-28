package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;

	
	private RoadMap _map;
	private Image _car;
	
	public MapByRoadComponent(Controller ctrl) {
		setPreferredSize (new Dimension (300, 200));
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
	}

	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		drawRoadsId(g);
		drawVehicles(g);
		drawJunctions(g);
		drawWeather(g);
		drawCO2(g);
	}
	
	
	private void drawRoads(Graphics g) {
		int i = 0;
		for (int j = 0; j < _map.getRoads().size();j++) {

			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int y1 = (i+1)*50;
			int x2 = getWidth()-100;
			int y2 = (i+1)*50;

			// draw line from (x1,y1) to (x2,y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length and 5 px width

			g.setColor( Color.BLACK);
			g.drawLine(x1, y1, x2, y2);
			i++;

		}

	}
	private void drawJunctions(Graphics g) {
		int i = 0;
		for(Road r : _map.getRoads()) {
			Junction src = r.getSrcJunc();
			Junction dest = r.getDestJunc();
			// (x,y) are the coordinates of the junction
			int x1 =  50;
			int y1 = (i+1)*50;
			int x2 =  getWidth()-100;
			int y2 = (i+1)*50;
			
			//color junction destine
			Color junctionColor = _RED_LIGHT_COLOR;
			int idx = dest.getCurrGreen();
			if (idx != -1 && r.equals(dest.getRoads().get(idx))) {
				junctionColor = _GREEN_LIGHT_COLOR;
			}
	
			//draw a circle with center at (x1,y1) with radius _JRADIUS junction origin
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS / 2, y1 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
			
			//draw a circle with center at (x2,y2) with radius _JRADIUS junctions destine
			g.setColor(junctionColor);
			g.fillOval(x2 - _JRADIUS / 2, y2 - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			
				
			// draw the junction's identifier at (x,y)
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(src.getId(), x1 - 2, y1 - 7);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(dest.getId(), x2 - 2, y2 - 7);
			i++;
		}
	}
	
	private void drawVehicles(Graphics g) {
		for (Vehicle v : _map.getVehicles()) {
			int i = 0;
			int y = 0;
			if (v.getState() != VehicleStatus.ARRIVED) {

				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relativly to the length of the road, and
				// the location on the vehicle.
				Road r = v.getRoad();
				int x1 = 50;
				int x2 = getWidth() - 120;

				
				int vX = (x1 + (int) ((x2 - x1) * ((double) v.getLocate() / (double) r.getLength())));
				
				for(Road roadAux : _map.getRoads()) {
					if(roadAux.getId() != r.getId())
						i++;
					else {
						y = i;
					}
				}
				int vY = (y+1)*50;

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
				g.setColor(new Color(0, vLabelColor, 0));

				// draw an image of a car and it identifier
				g.drawImage(_car, vX, vY - 10, 16, 16, this);
				g.drawString(v.getId(), vX, vY - 10);
			}
		}
	}
	
	
	private void drawRoadsId(Graphics g) {
		int i = 0;
		for(Road r:_map.getRoads()) {
			g.drawString(r.getId(), 25, ((i+1)*50) + 4);
			++i;
		}
	}
	
	private void drawWeather(Graphics g) {
		int i = 0;
		for(Road r: _map.getRoads()) {
			if(r.getWeather() == Weather.CLOUDY) {
				g.drawImage(loadImage("cloud.png"), getWidth()- 85,((i+1)*50) - 16, 32, 32, this);
			}
			else if(r.getWeather() == Weather.RAINY) {
				g.drawImage(loadImage("rain.png"), getWidth()- 85,((i+1)*50) - 16, 32, 32, this);
			}
			else if(r.getWeather() == Weather.STORM) {
				g.drawImage(loadImage("storm.png"), getWidth()- 85,((i+1)*50) -16, 32, 32, this);
			}
			else if(r.getWeather() == Weather.SUNNY) {
				g.drawImage(loadImage("sun.png"), getWidth()- 85,((i+1)*50) - 16, 32, 32, this);
			}
			else if(r.getWeather() == Weather.WINDY) {
				g.drawImage(loadImage("wind.png"), getWidth()- 85,((i+1)*50) -16, 32, 32, this);
			}
			++i;
		}
	}
	
	private void drawCO2(Graphics g) {
		int i = 0;
		for(Road r: _map.getRoads()) {
			int C = (int) Math.floor(Math.min((double) r.getTotalCont()/(1.0 + (double) r.getContLimit()),1.0) / 0.19);
			if(C == 0) {
				g.drawImage(loadImage("cont_0.png"), getWidth()- 48,((i+1)*50) - 16, 32, 32, this);
			}
			else if(C == 1) {
				g.drawImage(loadImage("cont_1.png"), getWidth()- 48,((i+1)*50) - 16, 32, 32, this);
			}
			else if(C == 2) {
				g.drawImage(loadImage("cont_2.png"), getWidth()- 48,((i+1)*50) -16, 32, 32, this);
			}
			else if(C == 3) {
				g.drawImage(loadImage("cont_3.png"), getWidth()- 48,((i+1)*50) - 16, 32, 32, this);
			}
			else if(C == 4) {
				g.drawImage(loadImage("cont_4.png"), getWidth()- 48,((i+1)*50) -16, 32, 32, this);
			}
			else if(C >= 5) {
				g.drawImage(loadImage("cont_5.png"), getWidth()- 48,((i+1)*50) -16, 32, 32, this);
			}
			++i;
		}
	}
	

	private void updatePrefferedSize() {
		int maxW = 455;
		int maxH = 350;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getxCoor());
			maxH = Math.max(maxH, j.getyCoor());
		}
		maxW += 20;
		maxH += 20;
		setPreferredSize(new Dimension(maxW, maxH));
		setSize(new Dimension(maxW, maxH));
	}
	
	
	
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	
	
	public void update(RoadMap map) {
		_map = map;
		repaint();
	}

	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
