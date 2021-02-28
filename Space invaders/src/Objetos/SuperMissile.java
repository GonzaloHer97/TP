package Objetos;


import Game.Game;
import Game.GameObject;

public class SuperMissile extends Weapon{

	
	private int damage;
	public SuperMissile(Game game, int x, int y, int live) {
		super(game, x, y, live);
		this.damage = 2;
		this.symbol = "X";
		this.updateTurn = 0;
	}
	
	public SuperMissile() {
		super(null, 0, 0, 1);
		this.damage = 2;
		this.symbol = "X";
	}
	
	
	public void move() {
		GameObject aux =game.getBoard().getObjectInPosition(x -1, y);
		if(aux == null || !aux.isEnable())
			this.x -= 1;
		else {
			aux.receiveMissileAttack(this.damage);
			game.enableMissile();
			this.live = 0;
		}
		if(this.x < 0) {
			this.live = 0;
			game.enableMissile();
		}
		
	}
	
	public boolean update(int turn) {
		if(this.updateTurn == turn && !this.alreadyUpdate) {
			if(this.isAlive())
				this.move();
			else
				game.enableMissile();
			this.alreadyUpdate = true;
			return true;
		}
		return false;
	}
	
	public boolean receiveBombAttack(int damage) {
		this.live -= damage;
		return true;
	}
	
	public String toString() {
		return "oo";
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	

}
