package Objetos;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Game.Game;
import Game.GameObject;

public class Weapon extends GameObject{

	public Weapon(Game game, int x, int y, int live) {
		super(game, x, y, live);
		this.isWeapon = true;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void computerAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onDelete() {
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(int turn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String serializedString() {
		String serialized;
		serialized = this.symbol + ";" + this.x +","+this.y+"\n";
		return serialized;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException {
		Weapon loadWeapon;
		String[] words = stringFromFile.split(";");
		if(words[0].equals(this.getSymbol())) {
			if(verifier.verifyWeaponString(stringFromFile, game)) {
				String[] coords = words[1].split(",");
				if(words[0].equals("B"))
					loadWeapon = new Bomb(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),1);
				else if(words[0].equals("M")) {
					loadWeapon = new UCMMissile(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),1);
					game.getPlayer().setDisparo(false);
				}
				else {
					loadWeapon = new SuperMissile(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]),1);
					game.getPlayer().setDisparo(false);
				}
				return loadWeapon;
			}
		}
		return null;
	}

	@Override
	public boolean receiveShockWaveAttack(int damage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean receiveBombAttack(int damage) {
		// TODO Auto-generated method stub
		return false;
	}

}
