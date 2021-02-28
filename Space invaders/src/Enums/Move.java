package Enums;

public enum Move {
	izq(),
	der(),
	abajo();
	
	
	private Move() {
		
	}
	
	public static Move parse(String param) {
		for (Move move : Move.values())
			if (move.name().equalsIgnoreCase(param)) return move;
		return null;
	   
	}
}
