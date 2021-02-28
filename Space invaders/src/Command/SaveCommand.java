package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class SaveCommand extends Command {

	private String file;
	public SaveCommand() {
		super("save fileName", "save fileName", "save fileName", "save the game in fileName");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws Exception {
		game.save(file);
		System.out.println("\n" + "Game successfully saved in file " + file + ".dat. Use the load command to reload it");
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] != null && commandWords[2] == null && commandWords[0].equalsIgnoreCase("save")) {
			file = commandWords[1];
			return new HelpCommand();
		}
		if((commandWords[1] == null || commandWords[2] != null) && commandWords[0].equalsIgnoreCase("save"))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);		
		return null;
	}

}
