package Objetos;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Game.Game;
import Game.GameObject;

public  class Ship extends GameObject {

	public Ship(Game game, int x, int y, int live) {
		super(game, x, y, live);
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
		return false;
		// TODO Auto-generated method stub
		
	}

	@Override
	public String serializedString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException {
		// TODO Auto-generated method stub
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
