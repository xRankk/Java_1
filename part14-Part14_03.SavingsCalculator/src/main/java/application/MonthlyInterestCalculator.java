/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.scene.chart.XYChart;
/**
 *
 * @author calin
 */
public class MonthlyInterestCalculator {
    public XYChart.Series interestData;
    
    public MonthlyInterestCalculator(){
        interestData=new XYChart.Series<>();
        interestData.setName("Interest earned");
    }
    
    public void calculateInterest(double monthlyAmount,double interestRate){
        double amountSavedPerYear=monthlyAmount*12;
        double currentTotalSavings = amountSavedPerYear + (amountSavedPerYear * interestRate) / 100;
        this.interestData.getData().add(new XYChart.Data(0, 0.0));
        this.interestData.getData().add(new XYChart.Data(1, currentTotalSavings));
        for(int i=2;i<=30;i++){
            currentTotalSavings+=amountSavedPerYear;
            currentTotalSavings=currentTotalSavings+(currentTotalSavings*interestRate)/100;
            XYChart.Data<Integer,Double> data=new XYChart.Data(i,currentTotalSavings);
            data.setNode(new HoveredThresholdNode(i, currentTotalSavings));
            interestData.getData().add(data);
        }
    }
    
    
    public XYChart.Series getChartData(){
        return this.interestData;
    }
    
    public void clear(){
        this.interestData.getData().clear();
    }
}
