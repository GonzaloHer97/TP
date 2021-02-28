package Command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Exceptions.CommandParserException;
import Exceptions.FileContentsException;
import Game.Game;

public class LoadCommand extends Command{

	private String file;
	private String line = "";
	public LoadCommand() {
		super("load fileName", "load fileName", "load fileName", "Load the game save in fileName");
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("resource")
	@Override
	public boolean execute(Game game) throws Exception {
		File aux = new File(file);
		if(!aux.exists())
			throw new FileNotFoundException("file " + file + " does not found");
		BufferedReader inStream = new BufferedReader(new FileReader(file));
		line = inStream.readLine().trim();
		if(line.equalsIgnoreCase("— Space Invaders v2.0 —")) {
			line = inStream.readLine().trim();
			if(line.equals("")) {
				game.load(inStream,line);
				System.out.println("\n" + "Game successfully loaded from file " + file );
			}
			else
				throw new FileContentsException("wrong file");
		}
		else {
			throw new FileContentsException("wrong file");
		}
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParserException {
		if(commandWords[1] != null && commandWords[2] == null && commandWords[0].equalsIgnoreCase("load")) {
			file = commandWords[1];
			return new HelpCommand();
		}
		if((commandWords[1] == null || commandWords[2] != null) && commandWords[0].equalsIgnoreCase("load"))
			throw new CommandParserException("\n" + incorrectNumArgsMsg);		
		return null;
	}

}
