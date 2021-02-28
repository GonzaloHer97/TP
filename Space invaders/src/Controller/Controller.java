package Controller;

import Command.Command;
import Command.CommandGenerator;
import Game.Game;
import Scanner.Scan;
import gamePrinter.FormattedPrinter;
import gamePrinter.GamePrinter;

public class Controller {
	private Game game;
	private Scan scanner;
	private GamePrinter gamePrinter;
	
	public Controller(Game game, Scan scanner) {
		this.game = game;
		this.scanner = scanner;
	}
	
	public void run() throws Exception {
		while (!game.isFinished()){
			printBoard();
			System.out.print("Command >> ");
			scanner.read();
			String[] words = scanner.parsher(scanner.getCadena().toLowerCase());
			try {
				Command command = CommandGenerator.parseCommand(words);
				if (command != null) {
					command.execute(game);
				}
				else {
					System.out.format("Comando equivocado");
				}
			}catch(Exception e){
				System.out.format(e.getMessage() + " %n %n");
			}
		}
		System.out.println("\n" +game.getWinnerMessage());
		printBoard();
	}

	public void printBoard() {
		this.gamePrinter = new FormattedPrinter(game, 8, 9);
		System.out.println("");
		System.out.println(game.infoToString());
		System.out.println("");
		System.out.print(gamePrinter);
	}
	
}
