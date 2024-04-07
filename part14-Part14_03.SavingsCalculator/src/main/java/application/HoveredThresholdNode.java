/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author calin
 */

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class HoveredThresholdNode extends StackPane{
    
    public HoveredThresholdNode(double priorValue, double value){
        final Label label = createDataThresholdLabel(priorValue, value);
        
        setOnMouseEntered((MouseEvent mouseEvent) -> {
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
        });
        setOnMouseExited((MouseEvent mouseEvent) -> {
            getChildren().clear();
            setCursor(Cursor.CROSSHAIR); 
        });
    }
     
    private Label createDataThresholdLabel(double priorValue, double value){
        final Label label = new Label("Total savings: £" + String.format("%.2f", value) + "");
        label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
        label.setStyle("-fx-font-size: 12; -fx-font-weight: bold");
        
        if(priorValue == 0.0){
            label.setTextFill(Color.DARKGREY);
        } else if(value > priorValue) {
            label.setTextFill(Color.FORESTGREEN);
        } else {
            label.setTextFill(Color.FIREBRICK);
        }
        
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
