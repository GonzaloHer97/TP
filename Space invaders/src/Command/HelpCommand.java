package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class HelpCommand extends Command{

	public HelpCommand() {
		super("help", "h", "help", " Prints this help message.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("h") || commandWords[0].equalsIgnoreCase("help"))) {
			return new HelpCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("h") || commandWords[0].equalsIgnoreCase("help")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);		return null;
		
	}

}
