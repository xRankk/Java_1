package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SavingsCalculatorApplication extends Application{

    public static double monthlyAmount = 50;
    public static double interestRate = 5.0;
    public static MonthlyInterestCalculator interestCalculator = new MonthlyInterestCalculator();
    public static MonthlySavingsCalculator savingsCalculator = new MonthlySavingsCalculator();

    public static void main(String[] args) {
        launch(SavingsCalculatorApplication.class);
    }

    public void start(Stage stage)throws Exception {
        Label savingsLabel = new Label("Monthly savings");
        Label savingsAmountLabel = new Label(String.valueOf(monthlyAmount));
        
        Slider savingsSlider = new HSlider(25, 250, monthlyAmount);
        
        savingsSlider.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    monthlyAmount = (double) newValue;
                    savingsAmountLabel.setText(String.format("%.2f", monthlyAmount));
                    updateChart();
        });
        
        BorderPane savingsSliderContainer = new BorderPane();
        savingsSliderContainer.setLeft(savingsLabel);
        savingsSliderContainer.setCenter(savingsSlider);
        savingsSliderContainer.setRight(savingsAmountLabel);

        savingsCalculator.calculateSavings(monthlyAmount);

        Label interestLabel = new Label("Yearly interest rate");
        Label interestAmountLabel = new Label(String.valueOf(interestRate));
        
        Slider interestSlider = new HSlider(0.0, 10.0, interestRate);
        
        interestSlider.valueProperty().addListener(
                (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    interestRate = (double) newValue;
                    interestAmountLabel.setText(String.format("%.2f", interestRate));
                    updateChart();
        });
         
        BorderPane interestSliderContainer = new BorderPane();
        interestSliderContainer.setCenter(interestSlider);
        interestSliderContainer.setLeft(interestLabel);
        interestSliderContainer.setRight(interestAmountLabel);

        interestCalculator.calculateInterest(monthlyAmount, interestRate);
        
        VBox sliderBox = new VBox(savingsSliderContainer, interestSliderContainer);
        sliderBox.setSpacing(20);

        NumberAxis xAxis = new NumberAxis(0, 30, 1);
        NumberAxis yAxis = new NumberAxis(0, 125000, 25000);
        xAxis.setLabel("Years");
        
        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle("Monthly savings");
        lineChart.setCursor(Cursor.CROSSHAIR);
        lineChart.getData().addAll(savingsCalculator.getChartData(), interestCalculator.getChartData());
        lineChart.setAnimated(false);
        
        BorderPane layout = new BorderPane(lineChart);
        layout.setTop(sliderBox);
        layout.setPadding(new Insets(10, 10, 10, 10));
        
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setHeight(900);
        stage.setWidth(1600);
        stage.setTitle("Monthly Savings and Interest Calculator");
        stage.show();
    }
    
    
    public void updateChart() {

        savingsCalculator.clear();
        interestCalculator.clear();
        
        savingsCalculator.calculateSavings(monthlyAmount);
        interestCalculator.calculateInterest(monthlyAmount, interestRate);

    }
}
