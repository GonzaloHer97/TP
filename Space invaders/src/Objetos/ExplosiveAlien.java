package Objetos;


import Game.Game;
import Game.GameObject;

public class ExplosiveAlien extends AlienShip{
	

	private int damage; 
	private boolean alreadyExplosion;
	public ExplosiveAlien(Game game, int x, int y) {
		super(game, x, y, 2, 5);
		this.symbol = "E";
		this.setAlreadyExplosion(false);
		this.damage = 1;
		this.updateTurn = 3;
	}
	public ExplosiveAlien() {
		super();
		this.symbol = "E";
		this.damage = 1;

	}
	public boolean update(int turn) {
		if(this.updateTurn == turn && !this.alreadyUpdate) {
			super.update(turn);
			if(!this.isAlive()) {
				this.setAlreadyUpdate(true);
			}
			this.alreadyUpdate = true;
			return true;
		}
		return false;
	}
	public boolean onDelete() {
		if(!this.alreadyExplosion && !this.isAlive()) {
			explosiveAlienDamage(this.x,this.y,this.damage);
			this.alreadyExplosion = true;
			return true;
		}
		return false;
	}
	
	public void explosiveAlienDamage(int x, int y, int damage) {
		GameObject aux = game.isNull(x+1, y);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x + 1, y-1);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x, y-1);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x-1, y-1);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x - 1, y);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x - 1, y +1);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x, y+1);
		if(aux !=null)
			aux.getDamage(damage);
		aux = game.isNull(x+1, y+1);
		if(aux !=null)
			aux.getDamage(damage);
		
	}
	
	public String toString() {
		return this.symbol + "[" + this.live + "]";
	}
	public boolean isAlreadyExplosion() {
		return alreadyExplosion;
	}
	public void setAlreadyExplosion(boolean alreadyExplosion) {
		this.alreadyExplosion = alreadyExplosion;
	}
}
