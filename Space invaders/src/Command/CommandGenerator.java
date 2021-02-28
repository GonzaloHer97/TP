package Command;

import Exceptions.CommandParserException;

public class CommandGenerator {
	
	private static Command[] availableCommands = {
			new ListCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new ListCommand(),
			new UpdateCommand(),
			new ShootCommand(),
			new MoveCommand(),
			new StringifyCommand(),
			new ShockwaveCommand(),
			new ListPrintersCommand(),
			new SuperMissileCommand(),
			new LoadCommand(),
			new SaveCommand()
			};

	public static Command parseCommand(String[ ] commandWords) throws CommandParserException {
		for(Command aux: availableCommands) {
			if(aux.parse(commandWords) != null) {
				return aux;
			}
		}
		throw new CommandParserException("\n" + "Unkwon command");
	}
	
	public static String commandHelp() {
		String aux = "";
		for(Command c :availableCommands ) {
			aux += c.helpText();
		}
		return aux;
	}
	
	public static Command[] getAvailableCommands() {
		return availableCommands;
	}


	public static void setAvailableCommands(Command[] availableCommands) {
		CommandGenerator.availableCommands = availableCommands;
	}
}
