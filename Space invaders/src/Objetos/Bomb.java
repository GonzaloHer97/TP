package Objetos;


import Game.Game;
import Game.GameObject;

public class Bomb extends Weapon {

	private int damage;
	public Bomb(Game game, int x, int y, int live) {
		super(game, x, y, live);
		this.damage = 1;
		this.symbol="B";
		this.updateTurn = 1;
	}
	public Bomb() {
		super(null, 0, 0, 0);
		this.damage = 1;
		this.symbol="B";
	}
	public void move() {
		GameObject aux = game.getBoard().getObjectInPosition(x + 1, y);
		if(aux != null && aux.receiveBombAttack(this.damage) ) {	
			if(!aux.isAlive())
				game.enableMissile();
				this.live = 0;
		}
		else {
			this.x += 1;
			if(this.x > 7)
				this.live = 0;
		}
	}

	public String toString() {
		return ".";
	}
	public boolean update(int turn) {
		if(this.updateTurn == turn && !this.alreadyUpdate) {
		
			if(this.isAlive())
			this.move();
			this.alreadyUpdate = true;
			return true;
		}
		return false;
	}
}
