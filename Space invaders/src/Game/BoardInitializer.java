package Game;

import Enums.Level;
import Objetos.*;

public class BoardInitializer {

	private Level level;
	private GameObjectBoard board;
	private Game game;
	
	
	public  GameObjectBoard initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new GameObjectBoard(Game.DIM_X, Game.DIM_Y);
		initializeOvni();
		initializeRegularAliens();
		initializeDestroyerAliens();
		return board;
	}
	
	private void initializeOvni () {
		Ovni ovni = new Ovni(game,0, 9);
		ovni.setEnable(false);
		board.add(ovni);
	}

	private void initializeRegularAliens () {
		int col = 3; 
		int row = 1;
		for(int i=0;i< level.getNumRowsOfRegularAliens();i++) {
			for(int j = 0; j < level.getNumRegularAliensPerRow();j++) {
				board.add(new RegularAlien(game,row,col));
				col++;
			}
			row++;
			col = 3;
			
		}
		
	}
	
	private void initializeDestroyerAliens() {
		int col = 0;
		int row = 0; 
		if(level.getNumRegularAliens() == 4) {
			col = 4;
			row = 2;
		}
		else if(level.getNumRegularAliens() == 8) {
			col = 3;
			row = 3;
		}
		else if(level.getNumRegularAliens() == 12) {
			col = 3;
			row = 4;
		}
		
		
		for(int i = 0; i < level.getNumDestroyerAliens();i++) {
			board.add(new DestroyerAlien(game,row,col));
			col++;
		}
	}
}
