package Files;

import Enums.Level;
import Enums.Move;
import Exceptions.FileContentsException;
import Game.Game;

public class FileContentsVerifier {
	public static final String separator1 = ";";
	public static final String separator2 = ",";
	public static final String labelRefSeparator = " - ";

	private String foundInFileString = "";
	
	private void appendToFoundInFileString(String linePrefix) {
		foundInFileString += linePrefix;
	}

	// Don't catch NumberFormatException.
	public boolean verifyCycleString(String cycleString) throws FileContentsException {
		String[] words = cycleString.split(separator1);
		appendToFoundInFileString(words[0]);
		if (words.length != 2 || !verifyCurrentCycle(Integer.parseInt(words[1])))
			return false;
		if(!verifyCurrentCycle(Integer.parseInt(words[1])))
				throw new FileContentsException("Wrong number of cycles");
		return true;
	}
	
	public boolean verifyLevelString(String levelString) throws FileContentsException {
		String[] words = levelString.split(separator1);
		appendToFoundInFileString(words[0]);
		if (words.length != 2 || !verifyLevel(Level.fromParam(words[1])))
			return false;
		if(!verifyLevel(Level.fromParam(words[1])))
			throw new FileContentsException("Wrong level");
		return true;
	}
	
	// Don't catch NumberFormatException.
	public boolean verifyOvniString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		appendToFoundInFileString(words[0]);
		if (words.length != 3) return false;
		String[] coords = words[1].split(separator2);
		if(Integer.parseInt(coords[0]) == 0 && Integer.parseInt(coords[1]) == 9)
			return true;
		if ( !verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game) || !verifyLives(Integer.parseInt(words[2]), armour) ) 
			return false;
		if(!verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)) {
			throw new FileContentsException("Wrong coordinates of ovni");
		}
		if(!verifyLives(Integer.parseInt(words[2]), armour) ) {
			throw new FileContentsException("Wrong live of ovni");
		}
		return true;
	}

	// Don't catch NumberFormatException.
	public boolean verifyPlayerString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		appendToFoundInFileString(words[0]);
		if (words.length != 6) return false;

		String[] coords = words[1].split(separator2);
		
		if ( !verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)
				|| !verifyLives(Integer.parseInt(words[2]), armour)
				|| !verifyPoints(Integer.parseInt(words[3]))
				|| !verifyBool(words[4]) )
			return false;
		if(!verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)) {
			throw new FileContentsException("Wrong coordinates of player");
		}
		if(!verifyLives(Integer.parseInt(words[2]), armour) ) {
			throw new FileContentsException("Wrong live of player");
		}
		if(!verifyPoints(Integer.parseInt(words[3]))) {
			throw new FileContentsException("Wrong points of player");
		}
		if(!verifyBool(words[4])) {
			throw new FileContentsException("Wrong superpower of player");
		}
		return true;
	}
	
	// Don't catch NumberFormatException.
	public boolean verifyAlienShipString(String lineFromFile, Game game, int armour) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		appendToFoundInFileString(words[0]);
		if (words.length != 5) return false;

		String[] coords = words[1].split(separator2);
		
		if ( !verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)
				|| !verifyLives(Integer.parseInt(words[2]), armour)
				|| !verifyCycleToNextAlienMove(Integer.parseInt(words[3]), game.getLevel())
				|| !verifyDir(Move.parse(words[4])) ) {
			return false;
		}
		if(!verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)) {
			throw new FileContentsException("Wrong coordinates of alienShip");
		}
		if(!verifyLives(Integer.parseInt(words[2]), armour) ) {
			throw new FileContentsException("Wrong live of alienShip");
		}
		if(!verifyCycleToNextAlienMove(Integer.parseInt(words[3]), game.getLevel())) {
			throw new FileContentsException("Wrong remaining cycles to move of alienShip");
		}
		if(!verifyDir(Move.parse(words[4]))) {
			throw new FileContentsException("Wrong direction of alienShip");
		}
		return true;
	}
	
	// Don't catch NumberFormatException.
	public boolean verifyWeaponString(String lineFromFile, Game game) throws FileContentsException {
		String[] words = lineFromFile.split(separator1);
		if (words.length != 2) return false;
		
		appendToFoundInFileString(words[0]);
		String[] coords = words[1].split(separator2);

		if ( !verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game) )
			return false;
		if(!verifyCoords(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), game)) {
			throw new FileContentsException("Wrong coordinates of weapon");
		}
		return true;
	}
	
	public boolean verifyRefString(String lineFromFile) {
		String[] words = lineFromFile.split(labelRefSeparator);
		if (words.length != 2 || !verifyLabel(words[1])) return false;
		return true;
	}

	public static boolean verifyLabel(String label) {
		return Integer.parseInt(label) > 0;
	}
	
	public static boolean verifyCoords(int x, int y, Game game) {
		return game.isOnBoard(x, y);
	}
	
	public static boolean verifyCurrentCycle(int currentCycle) {
		return currentCycle >= 0;
	}
	
	public static boolean verifyLevel(Level level) {
		return level != null;
	}
	
	public static boolean verifyDir(Move dir) {
		return dir != null;
	}
	
	public static boolean verifyLives(int live, int armour) {
		return 0 < live && live <= armour;
	}
	
	public static boolean verifyPoints(int points) {
		return points >= 0;
	}
	
	public static boolean verifyCycleToNextAlienMove(int cycle, Level level) {
		return 0 <= cycle && cycle <= level.getNumCyclesToMoveOneCell();
	}
	
	// parseBoolean converts any string different from "true" to false.
	public static boolean verifyBool(String boolString) {
		return boolString.equals("true") || boolString.equals("false");
	}

	public boolean isMissileOnLoadedBoard() {
		return foundInFileString.toUpperCase().contains("M");
	}
	
	// Use a regular expression to verify the string of concatenated prefixes found
	public boolean verifyLines() {
		// TO DO: compare foundInFileString with a regular expression
		return true; 
	}

	// text explaining allowed concatenated prefixes
	public String toString() {
		// TO DO
		return "";
	}
}
