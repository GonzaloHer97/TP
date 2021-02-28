package Objetos;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Game.Game;
import Game.GameObject;

public class AlienShip extends EnemyShip {
	private static boolean haveLanded= false;
	private static boolean allDead = false;
	private static int remainingAliens = 0;
	protected static String direction ="izq";
	private static String oldDirection;
	private int remainigCycles;
	private static boolean group_move = false;
	protected static int group_move_insane = 0;
	private int points;
	
	public AlienShip(Game game, int x, int y, int live, int points) {
		super(game, x, y, live, points);
		this.points = points;
		this.remainigCycles = game.getLevel().getNumCyclesToMoveOneCell();
		this.isAlien = true;
	}
	
	
	public AlienShip() {
		super(null, 0, 0, 0, 0);
		
	}
	public void move() {
		
		if(direction.equals("izq")) {
			this.checkMissile(x, y - 1);
			this.y--;
		}
		if(direction.equals("abajo")) {
			this.checkMissile(x + 1, y);
			this.x++;
		}
		if(direction.equals("der")) {
			this.checkMissile(x, y+1);
			this.y++;
		}
		if(!this.isAlive())
			game.receivePoints(points);
		if(this.x == 7)
			haveLanded = true;
	}
	
	public void setDirection() {
		if(!group_move) {
			if((this.checkPosition(8) && !direction.equals("abajo")) || (this.checkPosition(0) && !direction.equals("abajo"))) {
				if(direction.equals("der"))
					oldDirection = "izq";
				else
					oldDirection = "der";
				direction = "abajo";
			}
			else if(this.checkPosition(0) && direction.equals("abajo")) 
				direction = "der";
			else if(this.checkPosition(8) && direction.equals("abajo")) 
				direction ="izq";
			else if(!this.checkPosition(8) && !this.checkPosition(0) && direction.equals("abajo") )
				direction = oldDirection;
		}
	}
	
	public void setDirectionInsane() {
		if(group_move_insane == game.getCurrentCycle()) {
			group_move_insane++;
			if((this.checkPosition(8) && !direction.equals("abajo")) || (this.checkPosition(0) && !direction.equals("abajo"))) {
				if(direction.equals("der"))
					oldDirection = "izq";
				else
					oldDirection = "der";
				direction = "abajo";
			}
			else if(this.checkPosition(0) && direction.equals("abajo"))
				direction = "der";
			else if(this.checkPosition(8) && direction.equals("abajo"))
				direction ="izq";
			else if(!this.checkPosition(8) && !this.checkPosition(0) && direction.equals("abajo") )
				direction = oldDirection;
		}
	}
	
	public boolean update(int turn) {
		if(this.updateTurn == turn && !this.alreadyUpdate) {
			if(!this.isAlive()) {
				game.receivePoints(points);
			}
			else {
				
				if(game.getLevel().getNumCyclesToMoveOneCell() == 1) {
					setDirectionInsane();
					move();
				}
				
				else {
					if(this.remainigCycles == 1) {
						setDirection();
						move();
						group_move=true;
						this.remainigCycles = game.getLevel().getNumCyclesToMoveOneCell();
					}
					else {
						group_move=false;
						this.remainigCycles--;
					}
				}
			}
			this.alreadyUpdate = true;
			return true;
		}
		return false;
	}
	
	public String serializedString() {
		String serialized;
		serialized = this.symbol + ";" + this.x + "," +this.y + ";" + this.live + ";" + this.remainigCycles + ";" + direction+ "\n";
		return serialized;
		
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
	
	private boolean checkPosition(int col) {
		for(int i = 1; i < 6;i++) {
			GameObject aux = game.isNull(i, col);
			if(aux != null) {
				if(!aux.isWeapon() || aux.isAlien())
					return true;
			}
		}
		return false;
	}
	
	
	public GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws NumberFormatException, FileContentsException {
		AlienShip loadShip;
		String[] words = stringFromFile.split(";");
		if( words[0].equalsIgnoreCase(this.getSymbol()) ) {
			if(verifier.verifyAlienShipString(stringFromFile, game, 2)) {
				String[] coords = words[1].split(",");
				if(words[0].equals("D"))
					loadShip = new  DestroyerAlien(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
				else if(words[0].equals("R"))
					loadShip = new  RegularAlien(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
				else
					loadShip = new  ExplosiveAlien(game,Integer.parseInt(coords[0]),Integer.parseInt(coords[1]));
				loadShip.setLive(Integer.parseInt(words[2]));;
				loadShip.setRemainigCycles(Integer.parseInt(words[3]));
				AlienShip.setDirection(words[4]);
				AlienShip.setGroup_move_insane(game.getCurrentCycle());
				return loadShip;
			}
		}
			return null;
		
	}
	
	public static  boolean haveLanded() {
		return haveLanded;
	}
	
	public static boolean allDead() {
		return allDead;
	}
	
	public static int getRemainingAliens() {
		return remainingAliens;
	}



	public static boolean isHaveLanded() {
		return haveLanded;
	}



	public static void setHaveLanded(boolean haveLanded) {
		AlienShip.haveLanded = haveLanded;
	}



	public static boolean isAllDead() {
		return allDead;
	}



	public static void setAllDead(boolean allDead) {
		AlienShip.allDead = allDead;
	}



	public static String getDirection() {
		return direction;
	}



	public static void setDirection(String direction) {
		AlienShip.direction = direction;
	}



	public int getRemainigCycles() {
		return remainigCycles;
	}



	public void setRemainigCycles(int remainigCycles) {
		this.remainigCycles = remainigCycles;
	}



	public static boolean isGroup_move() {
		return group_move;
	}



	public static void setGroup_move(boolean group_move) {
		AlienShip.group_move = group_move;
	}



	public static int getGroup_move_insane() {
		return group_move_insane;
	}



	public static void setGroup_move_insane(int group_move_insane) {
		AlienShip.group_move_insane = group_move_insane;
	}



	public int getPoints() {
		return points;
	}



	public void setPoints(int points) {
		this.points = points;
	}



	public static void setRemainingAliens(int remainingAliens) {
		AlienShip.remainingAliens = remainingAliens;
	}
	
	

}
