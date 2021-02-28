package Command;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParserException;
import Game.Game;

public class ShockwaveCommand extends Command {

	public ShockwaveCommand() {
		super("shockwave","w", "shockWave", "UCM-Ship releases a shock wave.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		
		if(game.shockWave())
			game.update();
		else {
			throw new CommandExecuteException("\n" + "Failed to shoot\r\n" + 
					"Cause of Exception:\r\n" + "Cannot release shockwave: no shockwave available");
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("w") || commandWords[0].equalsIgnoreCase("shockwave"))) {
			return new ShockwaveCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("w") || commandWords[0].equalsIgnoreCase("shockwave")))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
	
		
		return null;
	}

}
