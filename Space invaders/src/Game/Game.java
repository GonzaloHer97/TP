package Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


import Enums.Level;
import Exceptions.CommandExecuteException;
import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Interfaces.IPlayerController;
import Objetos.*;

public class Game  implements IPlayerController{
	public final static int DIM_X = 9;
	public final static int DIM_Y = 8;

	private int currentCycle;
	private int auxCurrentCycle;
	private boolean loading;
	private Random rand;
	private Level level;
	private Level auxLevel;
	GameObjectBoard board;
	GameObjectBoard auxBoard;
	private UCMShip player;
	private UCMShip auxPlayer;
	private boolean doExit;
	private BoardInitializer initializer;
	
	public Game (Level level, Random random){
		this.rand = random;
		this.level = level;
		initializer = new BoardInitializer();
		initGame();
	}
	
	public void initGame () {
		currentCycle = 0;
		board = initializer.initialize(this, level);
		player = new UCMShip(this, DIM_X / 2, DIM_Y - 1);
		board.add(player);
	
	}

	public Random getRandom() {
		return rand;
	}
	
	public GameObjectBoard getBoard() {
		return board;
	}
	public Level getLevel() {
		return level;
	}
	public int getCurrentCycle() {
		return currentCycle;
	}
	
	public void reset() {
		initGame();
	}
	
	public void addObject(GameObject object) {
		board.add(object);
	}
	
	public String positionToString(int x, int y) {
		return board.toString(x, y);
	}

	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}
	
	public boolean aliensWin() {
		return !player.isAlive() || AlienShip.haveLanded();
	}
	
	private boolean playerWin () {
		return board.remainingAliens() == 0;
	}
	
	public void remove(GameObject object) {
		board.remove(object);
	}
	

	
	public void update() {
		board.computerAction();
		board.update();
		currentCycle += 1;
	}
	
	public boolean isOnBoard(int y, int x) {
		return x >= 0 && y >= 0 && x < DIM_X && y < DIM_Y;
	}
	
	public GameObject isNull(int x, int y) {
		return board.isNull(x, y);
	}
	
	public void exit() {
		doExit = true;
	}
	
	public String infoToString() {
		return "Cycles: " + currentCycle + "\n" +
			player.stateToString() +
			"Remaining aliens: " + board.remainingAliens() + "\n"; 
	}
	
	public String getWinnerMessage () {
		if (playerWin()) return "Player win!";
		else if (aliensWin()) return "Aliens win!";
		else if (doExit) return "Player exits the game";
		else return "This should not happen";
	}
	
	public boolean buySupermissile() throws CommandExecuteException {
		if(player.getPoints() >= 20) {
			this.player.setNumSupermissiles(player.getNumSupermissiles() + 1);
			player.setPoints(player.getPoints() - 20);
			return true;
		}
		
		return false;
	}

	
	public void save(String file) {
		String  aux = file + ".dat";
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(aux));
			bw.write("— Space Invaders v2.0 —" + "\n" + "\n");
			bw.write("G;" + getCurrentCycle() + "\n");
			bw.write("L;" + getLevel().name() + "\n");
			bw.write(board.serialized());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void load(BufferedReader inStream, String line) throws Exception {
		try {
			auxCurrentCycle = this.currentCycle;
			auxLevel = this.level;
			auxBoard = this.board;
			auxPlayer = this.player;
			FileContentsVerifier verifier = new FileContentsVerifier();
			loading = false;
			line = inStream.readLine().trim();
			if(verifier.verifyCycleString(line)) {
				String[] words = line.split(";");
				this.currentCycle = Integer.parseInt(words[1]);
			}
			else {
				throw new  FileContentsException("invalid file,wrong number of cycles");
			}
			line = inStream.readLine().trim();
			if(verifier.verifyLevelString(line)) {
				String[] words = line.split(";");
				this.level = Level.fromParam(words[1]);
			}
			else {
				throw new  FileContentsException("invalid file,wrong level");
			}
			board = new GameObjectBoard(Game.DIM_X, Game.DIM_Y);
			line = inStream.readLine().trim();
			while(line != null && !line.isEmpty() ) {
				GameObject gameObject = GameObjectGenerator.parse(line, this, verifier);
				if (gameObject == null) {
					throw new FileContentsException("invalid file, unrecognised line prefix");
				}
				board.add(gameObject);
				line = inStream.readLine().trim();
			}
			inStream.close();
		}catch(IOException e) {
			this.currentCycle = auxCurrentCycle;
			this.level = auxLevel;
			this.board = auxBoard;
			this.player = auxPlayer;
			throw new FileContentsException(e.getMessage());
		}
	}
	
	public String list() {
		String list = "";
		list += "[R]egular ship: Points: 5 - Harm: 0 - Shield: 2 " + "\n";
		list += "[E]xplosive ship: Points: 5 - Harm: 1 - Shield: 2 " + "\n";
		list += "[D]estroyer ship: Points: 10 - Harm: 1 - Shield: 1" + "\n";
		list += "[O]vni: Points: 25 - Harm: 0 - Shield: 1" + "\n";
		list += "^__^: Harm: 1 - Shield: 3"  + "\n";
		return list;
	}
	
	
	
	
	// TODO implementar los métodos del interfaz IPlayerController
	
	
	@Override
	public boolean move(int numCells, String dir) throws CommandExecuteException {
		this.player.move(dir, numCells);
		return true;
	}

	@Override
	public boolean shootLaser() {
		if(this.player.getDisparo() == true) {
			GameObject aux = this.isNull(this.player.getX()+1,this.player.getY());
			if( aux != null) {
				aux.receiveMissileAttack(1);
			}
			else {
				this.player.setDisparo(false);
				board.add(new UCMMissile(this,this.player.getX(),this.player.getY(),1));
			}
			return true;
		}
		return false;
	}

	public boolean shootSupermissile() throws CommandExecuteException {
		if(player.getNumSupermissiles() > 0) {
			if(this.player.getDisparo() == true) {
				GameObject aux = this.isNull(this.player.getX()+1,this.player.getY());
				if( aux != null) {
					aux.receiveMissileAttack(2);
				}
				else {
					this.player.setDisparo(false);
					board.add(new SuperMissile(this,this.player.getX(),this.player.getY(),1));
				}
				this.player.setNumSupermissiles(player.getNumSupermissiles() - 1);
				return true;
			}
		}
		else {
			throw new CommandExecuteException("\n" + "Failed to shoot\r\n" + 
					"Cause of Exception:\r\n" + " Cannot fire supermissile: 0 supermissiles at the moment");
		}
		return false;
	}
	@Override
	public boolean shockWave() {
		if(this.player.getShockwave() == true) {
			board.shockwave();
			this.player.setShockwave(false);
			return true;
		}
		return false;
	}

	@Override
	public void receivePoints(int points) {
		this.player.setPoints(this.player.getPoints() + points);
	}

	@Override
	public void enableShockWave() {
		this.player.setShockwave(true);
		
	}

	@Override
	public void enableMissile() {
		this.player.setDisparo(true);
	}

	public UCMShip getPlayer() {
		return player;
	}

	public void setPlayer(UCMShip player) {
		this.player = player;
	}
	
	
}
