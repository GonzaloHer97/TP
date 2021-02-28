package Command;

import Exceptions.CommandParserException;
import Game.Game;

public class UpdateCommand extends Command{

	public UpdateCommand() {
		super("", "", "[none]", "Skips one cycle.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.update();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && commandWords[0].equals("")) {
			return new UpdateCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equals("")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}
	

}
