package application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;



public class SampleController {
	@FXML
	private GridPane BoardGrid;
	@FXML
	private Button ResetButton;
	@FXML
	private Label label;
	@FXML
	private Button Easy;
	@FXML
	private Button Hard;
	@FXML
	private Button NumPlayers2;
	
	private ObservableList<String> stuffYouCanSee;
	@FXML
	private ListView<String> LogHolder;
	
	private GameLogic grid = new GameLogic();
	
	private AI robot = new AI();
    
    public void addToLog(boolean color, int column) {
		String result;
		if (color) {
			result = "Red: " + column;
		}else {
			result = "Yellow: " + column;
		}
		stuffYouCanSee.add(result);
	}
	
	
	private boolean placePiece(int col, GridPane gridPane) {
		
		boolean inserted = grid.insertIntoColumn(col);
		if (inserted){
	        for (Node node : gridPane.getChildren()) {
	            if (gridPane.getRowIndex(node) == grid.colHeight(col) - 1 
	            	&& gridPane.getColumnIndex(node) == col) {
	                if (node instanceof Circle) {
	                	((Circle) node).setFill(grid.playerColor());
	                }
	            }
	        }
	        addToLog(grid.getPlayerTurn(), col);
	        grid.changePlayerTurn();
	        label.setText("");
	        
		}else {
			label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
			label.setText("Column Full");
		}
		return inserted;
	}
	
	private void resetGame(GridPane gridPane) {
		grid.resetGame();
		for (Node node : gridPane.getChildren()) {
			if (node instanceof Circle) {
				node.setDisable(true);
				((Circle) node).setFill(Color.WHITE);
            }
        }
		Easy.setDisable(false);
		Easy.setVisible(true);
		Hard.setDisable(false);
		Hard.setVisible(true);
		NumPlayers2.setDisable(false);
		NumPlayers2.setVisible(true);
		ResetButton.setDisable(true);
		ResetButton.setVisible(false);
		label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		label.setText("Please Choose Game Mode");
		
		stuffYouCanSee = FXCollections.observableArrayList();
		LogHolder.setItems(stuffYouCanSee);
	}
	
	
	public void initialize() {
		stuffYouCanSee = FXCollections.observableArrayList();
	    LogHolder.setItems(stuffYouCanSee);
	    
		label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
		label.setText("Please Choose 1 or 2 Player");
		for (Node node : BoardGrid.getChildren()) {
			if (node instanceof Circle) {
				node.setDisable(true);
            }
        }
		ResetButton.setDisable(true);
		ResetButton.setVisible(false);
		
		Easy.setOnAction(event -> {
			grid.setNumPlayers(false);
			grid.setDifficulty(false);
			Easy.setDisable(true);
			Easy.setVisible(false);
			Hard.setDisable(true);
			Hard.setVisible(false);
			NumPlayers2.setDisable(true);
			NumPlayers2.setVisible(false);
			ResetButton.setDisable(false);
			ResetButton.setVisible(true);
			for (Node node : BoardGrid.getChildren()) {
				if (node instanceof Circle) {
					node.setDisable(false);
	            }
	        }
		});
		Hard.setOnAction(event -> {
			grid.setNumPlayers(false);
			grid.setDifficulty(true);
			Easy.setDisable(true);
			Easy.setVisible(false);
			Hard.setDisable(true);
			Hard.setVisible(false);
			NumPlayers2.setDisable(true);
			NumPlayers2.setVisible(false);
			ResetButton.setDisable(false);
			ResetButton.setVisible(true);
			for (Node node : BoardGrid.getChildren()) {
				if (node instanceof Circle) {
					node.setDisable(false);
	            }
	        }
		});
		
		NumPlayers2.setOnAction(event -> {
			grid.setNumPlayers(true);
			Easy.setDisable(true);
			Easy.setVisible(false);
			Hard.setDisable(true);
			Hard.setVisible(false);
			NumPlayers2.setDisable(true);
			NumPlayers2.setVisible(false);
			ResetButton.setDisable(false);
			ResetButton.setVisible(true);
			for (Node node : BoardGrid.getChildren()) {
				if (node instanceof Circle) {
					node.setDisable(false);
	            }
	        }
		});
	
		if (grid.noSpotsLeft()) {
    		label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
    		label.setText("Game Has No Winner, Please Reset");
    		for (Node n : BoardGrid.getChildren()) {
        		if (n instanceof Circle) {
        			n.setDisable(true);
                }
            }
    	}else {
    		for (Node node : BoardGrid.getChildren()) {
				if (node instanceof Circle) {
	                ((Circle) node).setOnMousePressed(event -> {
	                	boolean placed = placePiece(BoardGrid.getColumnIndex(node), BoardGrid);
	                	if (grid.checkWin()) {
	                    	for (Node n : BoardGrid.getChildren()) {
	                    		if (n instanceof Circle) {
	                    			n.setDisable(true);
	                            }
	                        }
	                        if (grid.getPlayerTurn()) {
	                            label.setStyle("-fx-background-color: black; -fx-text-fill: yellow;");
	                            label.setText("Yellow Wins!");
	                        }else {
	                            label.setStyle("-fx-background-color: black; -fx-text-fill: red;");
	                            label.setText("Red Wins!");
	                        }
	                        grid.setNumPlayers(true);
	                    }
	                	if (grid.noSpotsLeft()) {
	                		label.setStyle("-fx-background-color: black; -fx-text-fill: white;");
	                		label.setText("Game Has No Winner, Please Reset");
	                		for (Node n : BoardGrid.getChildren()) {
	                    		if (n instanceof Circle) {
	                    			n.setDisable(true);
	                            }
	                        }
	                	}
	                	else if (!grid.getNumPlayers() && placed) {
	                		while(!placePiece(robot.getNextMove(grid), BoardGrid)) {continue;}
	            			if (grid.checkWin()) {
	                        	for (Node n : BoardGrid.getChildren()) {
	                        		if (n instanceof Circle) {
	                        			n.setDisable(true);
	                                }
	                            }
	                            if (grid.getPlayerTurn()) {
	                                label.setStyle("-fx-background-color: black; -fx-text-fill: yellow;");
	                                label.setText("Yellow Wins!");
	                            }else {
	                                label.setStyle("-fx-background-color: black; -fx-text-fill: red;");
	                                label.setText("Red Wins!");
	                            }
	                        }
	                	}
	                });
				}
			}
    	}
		
		ResetButton.setOnAction(event -> {
			resetGame(BoardGrid);
		});
	}
}
