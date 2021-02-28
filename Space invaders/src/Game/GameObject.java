package Game;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Interfaces.IAttack;

public abstract class GameObject implements IAttack {
	protected int x;
	protected int y;
	protected int live;
	protected boolean enable;
	protected boolean isAlien = false;
	protected boolean isUCM = false;
	protected boolean isWeapon = false;
	protected int updateTurn;
	protected boolean alreadyUpdate = false;
	protected int damage;
	protected Game game;
	protected String symbol;
	
	public GameObject( Game game, int x, int y, int live) {	
		this.x = x;
		this.y = y;
		this.enable = true;
		this.game = game;
		this.live = live;
	}
	

	public int getX() {
	
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isAlive() {
		return this.live > 0;
	}

	public int getLive() {
		return this.live;
	}
	
	
	public void setLive(int live) {
		this.live = live;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public boolean isOnPosition(int x, int y) {
		return this.x == x && this.y == y;
	}

	public void getDamage (int damage) {
		this.live = damage >= this.live ? 0 : this.live - damage;
	}
	
	public boolean isOut() {
		return !game.isOnBoard(x, y);
	}
	
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean receiveMissileAttack(int  damage) {
		this.live -= damage;
		return false;
	};
	public abstract boolean receiveBombAttack(int  damage);
	public abstract boolean receiveShockWaveAttack(int  damage);;
	
	


	public abstract GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException;
	public abstract String serializedString();
	public abstract boolean update(int turn);
	public abstract void computerAction();
	public abstract boolean onDelete();
	public abstract void move();
	public abstract String toString();
	
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	
	public boolean isAlien() {
		return isAlien;
	}

	public void setAlien(boolean isAlien) {
		this.isAlien = isAlien;
	}

	public boolean isUCM() {
		return isUCM;
	}

	public void setUCM(boolean isUCM) {
		this.isUCM = isUCM;
	}

	public boolean isWeapon() {
		return isWeapon;
	}

	public void setWeapon(boolean isWeapon) {
		this.isWeapon = isWeapon;
	}

	public boolean isAlreadyUpdate() {
		return alreadyUpdate;
	}

	public void setAlreadyUpdate(boolean alreadyUpdate) {
		this.alreadyUpdate = alreadyUpdate;
	}
	
}
