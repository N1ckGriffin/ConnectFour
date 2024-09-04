package application;


public class AI {
	public int getNextMove(GameLogic g) {
		if (!g.getDifficulty()) {
			return (int) (Math.random() * 7);
		}
		int col = -1;
		boolean inserted;
		for (int i = 0; i < 7; i++) {
			inserted = g.insertIntoColumn(i);
			if (inserted && g.checkWin()) {
				col = i;
			}
			if (inserted) {
				g.removeFromColumn(i);
			}
		}
		if (col != -1) {
			return col;
		}
		g.changePlayerTurn();
		for (int i = 0; i < 7; i++) {
			inserted = g.insertIntoColumn(i);
			if (inserted && g.checkWin()) {
				col = i;
			}
			if (inserted) {
				g.removeFromColumn(i);
			}	
		}
		g.changePlayerTurn();
		if (col != -1) {
			return col;
		}
		return (int) (Math.random() * 7);
	}
}
