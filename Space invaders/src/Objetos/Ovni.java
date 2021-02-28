package Objetos;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Game.Game;
import Game.GameObject;

public class Ovni extends EnemyShip{
	
	private String symbol;
	

	public Ovni(Game game, int x, int y) {
		super(game, x, y, 1, 25);
		this.symbol = "O";
		this.updateTurn = 5;
	}
	
	
	public Ovni() {
		super(null, 0, 0, 1, 25);
	}
	
	public void computerAction() {
		if(game.getRandom().nextDouble() < game.getLevel().getOvniFrequency() && !game.getBoard().isOvniAlready()) {
			game.addObject(new Ovni(game,0,9));
			game.getBoard().setOvniAlready(true);
		}
	}

	public void move() {
		if(this.enable)
			this.y = this.y - 1;
	}
	
	public boolean update(int turn) {
		if(this.updateTurn == turn && this.isEnable() && !this.alreadyUpdate) {
				if(!this.isAlive() && this.y >= 0) {
					game.enableShockWave();
					game.receivePoints(25);
					game.getBoard().setOvniAlready(false);
				}
				else {
					if(this.y == 0) {
						this.getDamage(this.live);
						game.getBoard().setOvniAlready(false);
					}
					else {
						this.checkMissile(this.x, this.y-1);
						if(!this.isAlive()) {
							game.enableShockWave();
							game.receivePoints(25);
							game.getBoard().setOvniAlready(false);
						}else
							move();
					}
			}
				this.alreadyUpdate = true;
				return true;
		}
		return false;
	}
	public GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException {
		String[] words = stringFromFile.split(";");
		if(words[0].equals("O")) {
			if(verifier.verifyOvniString(stringFromFile, game, 1)) {
				String[] coords = words[1].split(",");
				Ovni loadOvni = new Ovni(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
				loadOvni.setLive(Integer.parseInt(words[2]));
				if(!game.isOnBoard(loadOvni.getX(), loadOvni.getY()))
					loadOvni.setEnable(false);
				else
					game.getBoard().setOvniAlready(true);
				return loadOvni;
			}
		}
		return null;
		
	}
	
	private void checkMissile(int row, int col) {
		GameObject aux = game.isNull(row, col);
		if(aux != null) {
			if(aux.isWeapon() && aux.isAlive()){
				this.receiveMissileAttack(aux.getDamage());
				aux.receiveMissileAttack(1);
				game.enableMissile();
			}
		}
	}
	
	public String toString() {
		return "O[" + this.live + "]";
	}
	
	public String serializedString() {
		String serialized;
		serialized = this.symbol + ";" +this.x + "," + this.y + ";" + this.live +"\n";
		return serialized;
 	}

	
}
