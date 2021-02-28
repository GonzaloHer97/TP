package Command;

import Enums.PrinterTypes;
import Exceptions.CommandParserException;
import Game.Game;

public class ListPrintersCommand extends Command {

	public ListPrintersCommand() {
		super("listPrinters", "listPrinters", "listPrinters", "list the types of printers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(PrinterTypes.printerHelp(game));
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] == null && commandWords[2] == null && commandWords[0].equalsIgnoreCase("listprinters")) {
			return new ListPrintersCommand();
		}
		if((commandWords[1] != null || commandWords[2] != null) && (commandWords[0].equalsIgnoreCase("listprinters") ))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);
		return null;
	}

}
