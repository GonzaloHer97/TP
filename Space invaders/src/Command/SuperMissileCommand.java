package Command;

import Exceptions.CommandExecuteException;
import Exceptions.CommandParserException;
import Game.Game;

public class SuperMissileCommand extends Command {

	public SuperMissileCommand() {
		super("supermissile","m", "supermisil", "buy a supermissile for 20 points");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		if(game.buySupermissile()) {
			game.update();
			return true;	
		}
		throw new CommandExecuteException("\n" + "Failed to buy supermissisle\r\n" + "Cause of Exception:\r\n" + " Cannot buy supermissile: not enough points");
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && commandWords[0].equalsIgnoreCase("supermisil")) {
			return new SuperMissileCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && commandWords[0].equalsIgnoreCase("supermisil"))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		
		return null;
	}

}
