package Objetos;

import Game.Game;

public class EnemyShip extends Ship {
	private int points;
	public EnemyShip(Game game, int x, int y, int live, int points) {
		super(game, x, y, live);
		this.points = points;
	}
	
	
	public boolean receiveShockWaveAttack(int  damage) {
		this.live -= damage;
		return true;
	}
	
	public void points() {
		if(!this.isAlive())
			game.receivePoints(points);
	}
	
}
