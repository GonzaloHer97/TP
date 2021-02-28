package Command;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParserException;
import Game.Game;

public class ShootCommand extends Command
{

	private boolean superMissile;
	public ShootCommand() {
		super("shoot", "s", "shoot", "UCM-Ship launches a missile.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		if(!this.superMissile) {
			if(game.shootLaser())
				game.update();
			else {
				throw new CommandExecuteException("\n" + "Failed to shoot\r\n" + 
						"Cause of Exception:\r\n" + " Cannot fire missile: missile or supermissile already exists on board");
			}
		}
		else {
			if(game.shootSupermissile())
				game.update();
			else {
				throw new CommandExecuteException("\n" + "Failed to shoot\r\n" + 
						"Cause of Exception:\r\n" + " Cannot fire supermissile: missile or supermissile already exists on board");
			}
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && (commandWords[0].equalsIgnoreCase("s") || commandWords[0].equalsIgnoreCase("shoot"))) {
			this.superMissile = false;
			return new ShootCommand();
		}
		else if(commandWords[1] != null) {
			if(commandWords[2] == null && commandWords[1].equalsIgnoreCase("supermisil") && (commandWords[0].equalsIgnoreCase("s") || commandWords[0].equalsIgnoreCase("shoot"))) {
				this.superMissile = true;
				return new ShootCommand();
			}
		}
		if(commandWords[2] != null && (commandWords[0].equalsIgnoreCase("s") || commandWords[0].equalsIgnoreCase("shoot") ) && commandWords[0].equalsIgnoreCase("supermisil"))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		if(commandWords[2] != null && (commandWords[0].equalsIgnoreCase("s") || commandWords[0].equalsIgnoreCase("shoot") ) && !commandWords[0].equalsIgnoreCase("supermisil"))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}

}
