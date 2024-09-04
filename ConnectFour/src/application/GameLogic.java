package application;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class GameLogic {
	private final int NUMCOLS = 7;
	private final int NUMROWS = 6;
	private ArrayList<ArrayList<Character>> _board = new ArrayList<>();
	private boolean _playerTurn; //false = yellow, true = red
	private boolean _numPlayers;
	private boolean _difficulty;
	
	private boolean allEqual(char c0, char c1, char c2, char c3) {
		return (c0 != '\0' && c0 == c1 && c1 == c2 && c2 == c3);
	}
	
	public GameLogic() {
		for (int i = 0; i < NUMCOLS; i++) {
			ArrayList<Character> col = new ArrayList<>();
			for (int j = 0; j < NUMROWS; j++) {
				col.add('\0');
			}
			_board.add(col);
		}
		_playerTurn = false;
	}
	
	public boolean noSpotsLeft() {
		int sum = 0;
		for (int i = 0; i < NUMCOLS; i++) {
			sum += colHeight(i);
		}
		return sum == 42;
	}
	
	public void changePlayerTurn() {_playerTurn = !_playerTurn;}
	public boolean getPlayerTurn() {return _playerTurn;}
	
	public void setNumPlayers(boolean b) {_numPlayers = b;}
	public boolean getNumPlayers() {return _numPlayers;}
	
	public void setDifficulty(boolean b) {_difficulty = b;}
	public boolean getDifficulty() {return _difficulty;}
	
	public boolean insertIntoColumn(int col) {
	    if (col < 0 || col >= _board.size()) {
	        return false; // Invalid column
	    }
	    
	    char piece;
	    if (_playerTurn) {
	    	piece = 'Y';
	    }else {
	    	piece = 'R';
	    }

	    ArrayList<Character> c = _board.get(col);
	    for (int i = 0; i < c.size(); i++) {
	        if (c.get(i) == '\0') {
	            c.set(i, piece);
	            return true; // Disc added successfully
	        }
	    }
	    return false; // Column is full
	}
	
	public boolean removeFromColumn(int col) {
	    if (col < 0 || col >= _board.size()) {
	        return false; // Invalid column
	    }

	    ArrayList<Character> c = _board.get(col);
	    for (int i = c.size() - 1; i >= 0; i--) {
	        if (c.get(i) != '\0') {
	            c.set(i, '\0'); // Remove the disc
	            return true; // Disc removed successfully
	        }
	    }
	    return false; // Column is empty, no disc to remove
	}
	
	public void resetGame() {
		_board = new ArrayList<>();
		for (int i = 0; i < NUMCOLS; i++) {
			ArrayList<Character> col = new ArrayList<>();
			for (int j = 0; j < NUMROWS; j++) {
				col.add('\0');
			}
			_board.add(col);
		}
		_playerTurn = false;
	}
	
	public int colHeight (int col) {
		if (col < 0 || col > 6) {return -1;}
		int count = 0;
		while (count < NUMROWS && _board.get(col).get(count) != '\0') {
			count += 1;
		}
		return count;
	}

	public Color playerColor() {
		if (_playerTurn) {return Color.RED;}
		return Color.YELLOW;
	}
	
	
	public boolean checkWin() {
	        // Check vertical, horizontal, and both diagonal wins
	        return checkVerticalWin() || checkHorizontalWin() || checkDiagonalWin();
	    }

	private boolean checkVerticalWin() {
		for (int col = 0; col < NUMCOLS; col++) {
	        ArrayList<Character> column = _board.get(col);
	        if (column.size() >= 4) {
	        	for (int row = 0; row < column.size() - 3; row++) {
	        		if (allEqual(column.get(row), column.get(row + 1), column.get(row + 2), column.get(row + 3))) {
	        			return true;
	                }
	            }
	        }
		}
	        return false;
	    }

	    private boolean checkHorizontalWin() {
	        for (int row = 0; row < NUMROWS; row++) {
	            for (int col = 0; col < NUMCOLS - 3; col++) {
	                if (row < _board.get(col).size() && row < _board.get(col + 1).size() &&
	                    row < _board.get(col + 2).size() && row < _board.get(col + 3).size()) {
	                    if (allEqual(_board.get(col).get(row), _board.get(col + 1).get(row), 
	                                      _board.get(col + 2).get(row), _board.get(col + 3).get(row))) {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }

	    private boolean checkDiagonalWin() {
	        // Check descending diagonals
	        for (int col = 0; col < NUMCOLS - 3; col++) {
	            for (int row = 0; row < NUMROWS - 3; row++) {
	                if (row < _board.get(col).size() && row + 1 < _board.get(col + 1).size() &&
	                    row + 2 < _board.get(col + 2).size() && row + 3 < _board.get(col + 3).size()) {
	                    if (allEqual(_board.get(col).get(row), _board.get(col + 1).get(row + 1), 
	                                      _board.get(col + 2).get(row + 2), _board.get(col + 3).get(row + 3))) {
	                        return true;
	                    }
	                }
	            }
	        }
	        // Check ascending diagonals
	        for (int col = 0; col < NUMCOLS - 3; col++) {
	            for (int row = 3; row < NUMROWS; row++) {
	                if (row < _board.get(col).size() && row - 1 < _board.get(col + 1).size() &&
	                    row - 2 < _board.get(col + 2).size() && row - 3 < _board.get(col + 3).size()) {
	                    if (allEqual(_board.get(col).get(row), _board.get(col + 1).get(row - 1), 
	                                      _board.get(col + 2).get(row - 2), _board.get(col + 3).get(row - 3))) {
	                        return true;
	                    }
	                }
	            }
	        }
	        return false;
	    }
	
}
