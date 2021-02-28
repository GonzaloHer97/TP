package Main;

import java.util.Random;

import Controller.Controller;
import Enums.Level;
import Exceptions.CommandExecuteException;
import Game.Game;
import Scanner.Scan;

public class Main {
	

    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

	public static void main(String[] args) throws Exception {
		Random seed = new Random();
		if(args.length< 1 || args.length > 2)
			throw new CommandExecuteException("Usage: Main <EASY|HARD|INSANE> [seed]");
		Level level = Level.fromParam(args[0]);
		if(level == null)
			 throw new Exception("Usage: Main <EASY|HARD|INSANE> [seed]: level must be one of: EASY, HARD, INSANE");
		if(args.length > 1 && isNumeric(args[1])) {
			seed.setSeed(Integer.parseInt(args[1]));
		}
		else {
			throw new NumberFormatException("Usage: Main <EASY|HARD|INSANE> [seed]: the seed must be a number");
		}
		Game game = new Game(level, seed);
		Scan scanner = new Scan();
		Controller controller =  new Controller(game,scanner);
		controller.run();
		
	}

}
