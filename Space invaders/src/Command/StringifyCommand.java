package Command;

import Exceptions.CommandParserException;
import Game.Game;
import gamePrinter.GamePrinter;
import gamePrinter.Stringify;

public class StringifyCommand extends Command{

	public StringifyCommand() {
		super("serialize", "r", "serialize", "serialize the game");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		GamePrinter printer = new Stringify(game);
		System.out.println(printer);
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("d") || commandWords[0].equalsIgnoreCase("serialize"))) {
			return new StringifyCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("d") || commandWords[0].equalsIgnoreCase("serialize")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}

}
