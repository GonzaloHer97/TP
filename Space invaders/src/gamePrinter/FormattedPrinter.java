package gamePrinter;
import Game.Game;
import Utils.MyStringUtils;

public class FormattedPrinter extends GamePrinter{
  
  Game game;
  int numRows; 
  int numCols;
  String[][] board;
  final String space = " ";
  
  public FormattedPrinter (Game game2, int rows, int cols) {
    this.game = game2;
    this.numRows = rows;
    this.numCols = cols;  
  }
  
  public FormattedPrinter() {
	// TODO Auto-generated constructor stub
}

private void encodeGame() {
    board = new String[numRows][numCols];
    for(int i = 0; i < numRows;i++ ) {
    	for(int j = 0; j < numCols;j++ ) {
    		board[i][j] = space;
    		if(game.positionToString(i, j) != null)
    			board[i][j] = game.positionToString(i, j);
    	}
    }
    
  }
  
  public String toString() {
    encodeGame();

    int cellSize = 7;
    int marginSize = 2;
    String vDelimiter = "|";
    String hDelimiter = "-";
    
    String rowDelimiter = MyStringUtils.repeat(hDelimiter, (numCols * (cellSize + 1)) - 1);
    String margin = MyStringUtils.repeat(space, marginSize);
    String lineDelimiter = String.format("%n%s%s%n", margin + space, rowDelimiter);
    
    StringBuilder str = new StringBuilder();
    
    str.append(lineDelimiter);
    
    for(int i=0; i<numRows; i++) {
        str.append(margin).append(vDelimiter);
        for (int j=0; j<numCols; j++) {
          str.append( MyStringUtils.centre(board[i][j], cellSize)).append(vDelimiter);
        }
        str.append(lineDelimiter);
    }
    return str.toString();
  }

}
