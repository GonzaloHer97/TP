package Game;

import java.util.ArrayList;

public class GameObjectBoard {
	private ArrayList<GameObject> objects;
	private int width;
	private int height;
	private int actual_size;
	private boolean ovniAlready;

	
	
	public GameObjectBoard (int width, int height) {
		this.objects = new ArrayList<GameObject>();
		this.width = width;
		this.height = height;
		this.ovniAlready = false;
	}
	
	private int getCurrentObjects () {
		return this.objects.size();
	}
	
	public int remainingAliens() {
		int aliens = 0;
		for(GameObject gc :objects) {
			if(gc.isAlien)
				aliens++;
		}
		return aliens;
	}
	
	public void add (GameObject object) {
		objects.add(object);
	}
	
	public GameObject getObjectInPosition (int x, int y) {
		for(GameObject aux: objects) {
			if(aux.isOnPosition(x, y) && aux.isEnable())
				return aux;
		}
		return null;
	}
	
	private int getIndex(int x, int y) {
		for(int i = 0 ;i < objects.size();i++) {
			if(objects.get(i).getX() == x && objects.get(i).getY() == y)
				return i;
		}
		return -1;
	}

	public void remove(GameObject object) {
		objects.remove(object);
	}
	

	private void checkAttacks(GameObject object) {
		// TODO implement
	}
	
	public void computerAction() {
		actual_size = getCurrentObjects();
		for(int i = 0; i < actual_size;i++) {
			objects.get(i).computerAction();
		}
		
	}
	

	public void update() {
		int turn = 0;
		int numElements = 0;
		boolean ok = false;
		while(numElements < objects.size() - 1) {
			ok = false;
			for(int j = 0; j < objects.size();j++) {
				if(objects.get(j).update(turn)) {
					numElements++;
					ok = true;
				}
			}
			if(turn == 0)
				this.explosiveCheck();
			if(!ok)
				turn++;
		}
		for(int j = 0; j < objects.size();j++) {
			objects.get(j).setAlreadyUpdate(false);
		}
		removeDead();
		
	}
	
	
	private void removeDead() {
		ArrayList<GameObject> aux = new ArrayList<GameObject>();
		this.explosiveCheck();
		for(int i = 0;i < objects.size();i++) {
			if(!objects.get(i).isAlive()) {
				aux.add(objects.get(i));
			}
		}
		for(int i = 0; i < aux.size();i++) {
			if(!aux.get(i).isUCM) {
				this.remove(aux.get(i));
			}
		}
		
	}
	
	
	public void explosiveCheck() {
		boolean ok = false;
		while(!ok) {
			ok = true;
			for(GameObject aux: objects) {
				if(aux.onDelete()) {
					ok  = false;
				}
			}
		}
	}
	
	
	public void shockwave() {
		for(GameObject aux:objects) {
			if (aux.isEnable())
				aux.receiveShockWaveAttack(1);
		}
	}
	public GameObject isNull(int x, int y) {
		for(GameObject aux: objects) {
			if(aux.getX() == x && aux.getY() == y)
				return aux;
		}
		return null;
	}
	public String toString(int x, int y) {
		for(GameObject aux: objects) {
			if(aux.getX() == x && aux.getY() == y)
				return aux.toString();
		}
		return null;
	}
	
	public String serialized() {
		String ser = "";
		for(GameObject aux:objects) {
			ser += aux.serializedString();
		}
		ser += "\n";
		return ser;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isOvniAlready() {
		return ovniAlready;
	}

	public void setOvniAlready(boolean ovniAlready) {
		this.ovniAlready = ovniAlready;
	}

	
}
