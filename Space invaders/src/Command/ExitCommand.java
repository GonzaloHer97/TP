package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class ExitCommand extends Command{

	public ExitCommand() {
		super("exit", "e", "exit", "Terminates the program.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.exit();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("e") || commandWords[0].equalsIgnoreCase("exit"))) {
			return new ExitCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("e") || commandWords[0].equalsIgnoreCase("exit")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}
	

}
