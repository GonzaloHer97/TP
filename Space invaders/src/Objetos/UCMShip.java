package Objetos;

import Exceptions.CommandExecuteException;
import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Game.Game;
import Game.GameObject;

public class UCMShip extends Ship{
	private int fila;
	private int columna;
	private int resistencia;
	private int points;
	private Boolean shockwave;
	private Boolean disparo;
	private String icon;
	private int numSupermissiles;
	
	public UCMShip(Game game, int x, int y) {
		super(game, y, x ,3);
		this.fila=y;
		this.columna=x;
		this.resistencia=3;
		this.points = 0;
		this.numSupermissiles = 0;
		this.setShockwave(false);
		this.disparo = true;
		this.symbol = "P";
		this.icon = "^__^";
		this.isUCM = true;
		this.updateTurn = 2;
		
	}
	
	public UCMShip() {
		super(null, 0, 0 , 3);
	}
	
	public void move(String dir, int num) throws CommandExecuteException {
		if(dir.equalsIgnoreCase("l")|| dir.equalsIgnoreCase("left")) {
			if(this.y - num >= 0) {
				this.y -= num;
			}
			else {
				throw new CommandExecuteException("\n" + "Failed to move\r\n" + 
						"Cause of Exception:\r\n" + "Cannot perform move: ship too near border");
			}
		}
		else {
			if(this.y + num <= 8) {
				this.y += num;
			}
			else {
				throw new CommandExecuteException("\n" + "Failed to move\r\n" + 
						"Cause of Exception:\r\n" + "Cannot perform move: ship too near border");
			}
		}
	}
	
	
	public boolean update(int turn) {
		if(!this.alreadyUpdate && this.updateTurn == turn) {
			if(!this.isAlive())
				this.icon = "!xx!";
			this.alreadyUpdate = true;
			return true;
		}
		return false;
	}
	
	
	public String stateToString() {
		String info;
		info = "Life: " + this.live + "\n";
		info += "Points:" +this.points+ "\n";
		if(this.shockwave)
			info += "Shockwave: SI" + "\n";
		else
			info += "Shockwave: NO" + "\n";
		info += "Supermissiles: " + this.numSupermissiles + "\n";
		return info;	
	}
	
	public String serializedString() {
		String serialized;
		serialized = this.symbol + ";" + this.x + "," + this.y + ";" + this.live + ";" + this.points + ";";
		serialized += this.shockwave.booleanValue() + ";";
		serialized += this.numSupermissiles + "\n";
		return serialized;
	}
	
	public GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException {
		String[] words = stringFromFile.split(";");
		if(words[0].equals("P")) {
			if(verifier.verifyPlayerString(stringFromFile, game, 3)) {
				String[] coords = words[1].split(",");
				UCMShip loadShip =new  UCMShip(game,Integer.parseInt(coords[1]),Integer.parseInt(coords[0]));
				loadShip.setLive(Integer.parseInt(words[2]));;
				loadShip.setPoints(Integer.parseInt(words[3]));
				if(words[4].equals("true"))
					loadShip.setShockwave(true);
				else
					loadShip.setShockwave(false);
				loadShip.setNumSupermissiles(Integer.parseInt(words[5]));
				game.setPlayer(loadShip);
				return loadShip;
			}
		}
			return null;
	}
	
	public boolean receiveBombAttack(int damage) {
		this.live -= damage;
		return true;
	}
	
	
	public String toString() {
		return this.icon;
	}

	public Boolean getShockwave() {
		return shockwave;
	}


	public void setShockwave(Boolean shockwave) {
		this.shockwave = shockwave;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getNumSupermissiles() {
		return numSupermissiles;
	}

	public void setNumSupermissiles(int numSupermissiles) {
		this.numSupermissiles = numSupermissiles;
	}
	
	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public int getResistencia() {
		return resistencia;
	}

	public void setResistencia(int resistencia) {
		this.resistencia = resistencia;
	}


	public Boolean getDisparo() {
		return disparo;
	}



	public void setDisparo(Boolean disparo) {
		this.disparo = disparo;
	}
	

}
