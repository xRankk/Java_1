package ticTacToe;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

public class TicTacToeApplication extends Application{
    
    String currentPlayer = "X";
    ArrayList<Button> buttons = new ArrayList<>();
    
    
    @Override
    public void start(Stage window){
        //layout
        BorderPane mainLayout=new BorderPane();
        
        //component
        Label status=new Label("Turn: "+currentPlayer);
        status.setFont(Font.font("Monospaced", 25));

        
        //center layout
        GridPane center=new GridPane();
        
        
        //styling
        center.setAlignment(Pos.CENTER);
        center.setVgap(10);
        center.setHgap(10);
        center.setPadding(new Insets(10, 10, 10, 10));
        center.setPrefSize(300,250);
        
        //Buttons
        for (int i = 0; i < 9; i++) {
            Button button = new Button();
            button.setFont(Font.font("Monospaced", 25));
            button.setMinSize(70, 70);
            button.setMaxSize(70, 70);
            button.setOnMouseClicked((event) -> {
                if (status.getText().startsWith("Winner:") || status.getText().equals("It's a draw!")) {
                    button.disarm();
                }else if (button.getText().isEmpty()) {
                    button.setText(currentPlayer);
                    if (checkIfWinner()) {
                        status.setText("Winner: "+currentPlayer);
                    } else if(allBoxesAreFilled()) {
                        status.setText("It's a draw!");
                    }else {
                        takeTurn();
                        status.setText("Turn: "+currentPlayer);
                    }
                }
            });
            buttons.add(button);
        }
        
        
        //adding buttons
        center.add(buttons.get(0), 0, 0);
	center.add(buttons.get(1), 0, 1);
	center.add(buttons.get(2), 0, 2);
	center.add(buttons.get(3), 1, 0);
	center.add(buttons.get(4), 1, 1);
	center.add(buttons.get(5), 1, 2);
	center.add(buttons.get(6), 2, 0);
	center.add(buttons.get(7), 2, 1);
        center.add(buttons.get(8), 2, 2);
        //adding the gridpane
        mainLayout.setTop(status);
        mainLayout.setCenter(center);
        
        //starter
        Scene view=new Scene(mainLayout,400,300);
        
        window.setScene(view);
        window.show();
    }
    
    public void takeTurn() {
	if (currentPlayer.equals("X")) {
            currentPlayer = "O";
	} else if (currentPlayer.equals("O")) {
            currentPlayer = "X";
	}
    }

    private boolean allBoxesAreFilled() {
	for (Button b : buttons) {
            if (b.getText().isEmpty()) {
		return false;
            }
        }
	return true;
    }
    
    public boolean checkIfWinner() {
        return checkVerticalColumns() || checkHorizontalRows() || checkDiagonals();
    }
    
    public boolean checkVerticalColumns() {
        for(Button b:buttons){
            if (!buttons.get(0).getText().isEmpty() && ((buttons.get(0).getText().equals(buttons.get(1).getText()))
				&& (buttons.get(0).getText().equals(buttons.get(2).getText())))) {
		return true;
            }

		//checks the second vertical column
            if (!buttons.get(3).getText().isEmpty() && ((buttons.get(3).getText().equals(buttons.get(4).getText()))
				&& (buttons.get(3).getText().equals(buttons.get(5).getText())))) {
		return true;
            }
		//checks the third vertical column
            if (!buttons.get(6).getText().isEmpty() && ((buttons.get(6).getText().equals(buttons.get(7).getText()))
				&& (buttons.get(6).getText().equals(buttons.get(8).getText())))) {
		return true;
            }
        }
        return false;
}
    
    public boolean checkHorizontalRows() {
		//checks first horizontal row for a winner
	if (!buttons.get(0).getText().isEmpty() && ((buttons.get(0).getText().equals(buttons.get(3).getText()))
				&& (buttons.get(0).getText().equals(buttons.get(6).getText())))) {
            return true;
	}

		//checks second horizontal row for a winner
	if (!buttons.get(1).getText().isEmpty() && ((buttons.get(1).getText().equals(buttons.get(4).getText()))
				&& (buttons.get(1).getText().equals(buttons.get(7).getText())))) {
            return true;
	}
		//checks third horizontal row for a winner
	if (!buttons.get(2).getText().isEmpty() && ((buttons.get(2).getText().equals(buttons.get(5).getText()))
				&& (buttons.get(2).getText().equals(buttons.get(8).getText())))) {
            return true;
	}
	return false;
    }
    
    public boolean checkDiagonals() {
		//checks for first horizontal for a winner
	if (!buttons.get(0).getText().isEmpty() && ((buttons.get(0).getText().equals(buttons.get(4).getText()))
				&& (buttons.get(0).getText().equals(buttons.get(8).getText())))) {
            return true;
	}
		//checks for second horizontal for a winner
	if (!buttons.get(2).getText().isEmpty() && ((buttons.get(2).getText().equals(buttons.get(4).getText()))
				&& (buttons.get(2).getText().equals(buttons.get(6).getText())))) {
            return true;
	}
	return false;
}
    

    public static void main(String[] args) {
        launch(TicTacToeApplication.class);
    }

}
