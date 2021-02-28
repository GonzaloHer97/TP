package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class ResetCommand extends Command{

	public ResetCommand() {
		super("reset", "r", "reset", "Starts a new game.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("r") || commandWords[0].equalsIgnoreCase("reset"))) {
			return new ResetCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("r") || commandWords[0].equalsIgnoreCase("reset")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		return null;
	}

}
