package Objetos;


import Game.Game;
import Game.GameObject;

public class DestroyerAlien extends AlienShip {

	public DestroyerAlien(Game game, int x, int y) {
		super(game, x, y, 1, 10);
		this.symbol = "D";
		this.updateTurn = 4;
	}
	
	
	public DestroyerAlien() {
		super();
		this.symbol = "D";
	}


	public void computerAction() {
		if(this.isAlive()) {
			if(game.getRandom().nextDouble() < game.getLevel().getShootFrequency()) {
				GameObject aux = game.isNull(this.getX()+ 1,this.getY());
				if(aux !=null && aux.receiveBombAttack(1)) {
					return ;
				}
				else
					game.addObject(new Bomb(game,this.getX(),this.getY(),1));
			}
		}
	}
	

	public String toString() {
		return this.symbol + "[" + this.getLive() + "]";
	}

}
