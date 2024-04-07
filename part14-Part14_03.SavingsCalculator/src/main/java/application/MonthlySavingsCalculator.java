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
public class MonthlySavingsCalculator {
    public XYChart.Series savingsData;
    
    public MonthlySavingsCalculator(){
        savingsData=new XYChart.Series();
        savingsData.setName("Savings");
    }
    
    public void calculateSavings(double monthlyAmount){
        for(int i=0;i<=30;i++){
             double savingsAmount = i * monthlyAmount * 12;
             XYChart.Data<Integer,Double> data=new XYChart.Data(i,savingsAmount);
             data.setNode(new HoveredThresholdNode(i, savingsAmount));
             savingsData.getData().add(data);
        }
    }
    
    public XYChart.Series getChartData(){
        return this.savingsData;
    }
    
    public void clear(){
        this.savingsData.getData().clear();
    }
}
