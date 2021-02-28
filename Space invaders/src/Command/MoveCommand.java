package Command;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParserException;
import Game.Game;

public class MoveCommand extends Command {
	private String dir;
	private int num;

	public MoveCommand() {
		super("move", "m", "move <left|right> <1|2>", "Moves UCM-Ship to the indicated direction.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		game.move(num, dir);
		game.update();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] != null && commandWords[2] != null && (commandWords[0].equalsIgnoreCase("m") || commandWords[0].equalsIgnoreCase("move")) 
			&& (commandWords[1].equalsIgnoreCase("l") || commandWords[1].equalsIgnoreCase("left")
			|| commandWords[1].equalsIgnoreCase("r") || commandWords[1].equalsIgnoreCase("right"))
			&& (commandWords[2].equalsIgnoreCase("1") || commandWords[2].equalsIgnoreCase("2"))){

			this.dir = commandWords[1];
			this.num = Integer.parseInt(commandWords[2]);
			return new ListCommand();
		}
		if((commandWords[1] == null || commandWords[2] == null) && (commandWords[0].equalsIgnoreCase("m") || commandWords[0].equalsIgnoreCase("move")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		if((commandWords[1] != null || commandWords[2] != null) && (!commandWords[1].equalsIgnoreCase("l") && !commandWords[1].equalsIgnoreCase("left") && !commandWords[1].equalsIgnoreCase("r") && !commandWords[1].equalsIgnoreCase("right")) && (commandWords[0].equalsIgnoreCase("m") || commandWords[0].equalsIgnoreCase("move")))
			throw new CommandParserException("\n" + "Wrong direction: [r]ight or [l]eft");
		if((commandWords[1] != null || commandWords[2] != null) && (!isNumeric(commandWords[2]) || (!commandWords[2].equalsIgnoreCase("1") && !commandWords[2].equalsIgnoreCase("2"))) && (commandWords[0].equalsIgnoreCase("m") || commandWords[0].equalsIgnoreCase("move")))
			throw new CommandParserException("\n" + "Last parameter must be a number: 1 or 2");
		
		
		return null;
	}
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


}
