package gamePrinter;

import Game.Game;

public class Stringify extends GamePrinter{

	private Game game;
	public Stringify(Game game) {
		this.game =game;
	}
	
	
	
	
	
	public Stringify() {
		// TODO Auto-generated constructor stub
	}





	public String toString() {
		String printer;
		printer = "— Space Invaders v2.0 —" + "\n" + "\n";
		printer += "G;" + game.getCurrentCycle() + "\n";
		printer += "L;" + game.getLevel().name() + "\n";
		printer += serialized();
		
		
		return printer;
	}
	
	
	
	public String serialized() {
		String aux = "";
		for(int i = 0;i < game.getBoard().getObjects().size();i++) {
			if (game.getBoard().getObjects().get(i).isEnable())
			 aux += game.getBoard().getObjects().get(i).serializedString();
		}
		return aux;
	}
	
}
