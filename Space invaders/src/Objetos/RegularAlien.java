package Objetos;

import Game.Game;


public class RegularAlien extends AlienShip {

	private String icono;
	public RegularAlien(Game game, int x, int y) {
		super(game, x, y, 2, 5);
		this.symbol ="R";
		this.icono = "C";
		this.updateTurn = 3;
	}
	
	public RegularAlien() {
		super();
		this.symbol ="R";
		this.icono = "C";

	}

	public void computerAction() {
		if(game.getRandom().nextInt(20) == 0) {
			ExplosiveAlien aux = new ExplosiveAlien(game,this.getX(),this.getY());
			aux.setRemainigCycles(this.getRemainigCycles());
			aux.setLive(this.live);
			AlienShip.setDirection(direction);
			game.addObject(aux);
			game.remove(this);
		}
	}
	public String toString() {
		return this.icono + "[" + this.live + "]";
	}	

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

}
