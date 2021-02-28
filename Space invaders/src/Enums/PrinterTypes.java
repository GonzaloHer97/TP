package Enums;

import Game.Game;
import gamePrinter.*;

public enum PrinterTypes {
	
	BOARDPRINTER("boardprinter", "prints the game formatted as a board of dimension: ", new FormattedPrinter()),
	SERIALIZER("serializer", "prints the game as plain text", new Stringify());
	private String printerName;
	private String helpText;
	private GamePrinter printerObject;
	private PrinterTypes(String name, String text, GamePrinter printer) {
		printerName = name;
		helpText = text;
		printerObject = printer;
		}
	public static String printerHelp(Game game) {
		String helpString = "";
		for (PrinterTypes printer : PrinterTypes.values())
			helpString += String.format(" %s : %s %s %n", printer.printerName, printer.helpText,(printer == BOARDPRINTER ? Game.DIM_X + " x " + Game.DIM_Y : ""	) );
		return helpString;
	}
	public GamePrinter getObject() {
		return printerObject;
	}

}
