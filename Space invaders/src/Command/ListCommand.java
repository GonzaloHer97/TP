package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class ListCommand extends Command{

	public ListCommand() {
		super("list", "l", "list", "Prints the list of available ships.");
		
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(game.list());
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("l") || commandWords[0].equalsIgnoreCase("list"))) {
			return new ListCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("l") || commandWords[0].equalsIgnoreCase("list")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}

}
