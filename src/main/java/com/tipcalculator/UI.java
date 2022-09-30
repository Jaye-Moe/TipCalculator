package com.tipcalculator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class UI {
    private final GridPane gridPane;
    private final Slider tipPercentInput;
    private final Text tipPercentOutput;
    private final Text billAmount;
    private final TextField billAmountInput;
    private final Text tipPercent;
    private final Text tipOutput;
    private final Text total;
    private int tipPercentInt;
    private float billAmountFloat;
    private CheckBox roundUpBox;
    private Text roundUpText;

    public UI() {
        this.gridPane = new GridPane();
        this.gridPane.setPadding(new Insets(10, 10, 10, 10));
        this.gridPane.setVgap(10);
        this.gridPane.setHgap(10);

        this.tipPercentInput = new Slider();
        this.tipPercentOutput = new Text();
        this.billAmount = new Text();
        this.billAmountInput = new TextField();
        this.tipPercent = new Text();
        this.tipOutput = new Text();
        this.total = new Text();
        this.roundUpBox = new CheckBox();
        this.roundUpText = new Text();
    }

    public void buildLayout() {
        this.setGridPane(300, 300);
        this.setSlider();
        this.setTipPercentOutput();
        this.setBillAmount();
        this.setBillAmountInput();
        this.setTipPercent();
        this.setTipOutput();
        this.setTotal();
        this.setRoundUpText();
        this.setRoundUpBox();

        this.gridPane.add(this.billAmount, 1, 1);
        this.gridPane.add(this.billAmountInput, 2, 1);
        this.gridPane.add(this.tipPercent, 1, 2);
        this.gridPane.add(this.tipPercentInput, 2, 2);
        this.gridPane.add(this.tipPercentOutput, 3, 2);
        this.gridPane.add(this.tipOutput, 1, 4);
        this.gridPane.add(this.total, 1, 5);
        this.gridPane.add(this.roundUpText,1,3,2,1);
        this.gridPane.add(this.roundUpBox,3,3);
    }

    public void setGridPane(double x, double y) {
        this.gridPane.setMinSize(x, y);
    }

    public void setSlider() {
        this.tipPercentInput.setMin(0);
        this.tipPercentInput.setMax(50);
        this.tipPercentInput.setValue(20);
        this.tipPercentInt = 20;
        this.tipPercentInput.setBlockIncrement(5);
        this.tipPercentInput.setShowTickMarks(true);
        this.tipPercentInput.setShowTickLabels(true);
        this.tipPercentInput.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    tipPercentOutput.setText(newValue.shortValue() + "%");
                    this.tipPercentInt = newValue.shortValue();
                    this.calculateTip();
                });
    }

    public void setTipPercentOutput() {
        this.tipPercentOutput.setText("20%");
        this.tipPercentOutput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setBillAmount() {
        this.billAmount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        this.billAmount.setText("Bill Amount: $");
        this.billAmount.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setBillAmountInput() {
        this.billAmountInput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        this.billAmountInput.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                billAmountInput.setText(oldValue);
            } else {
                System.out.println(newValue);
                this.billAmountFloat = Float.parseFloat(newValue);
                this.calculateTip();
            }
        }));
    }

    public void setTipPercent() {
        this.tipPercent.setText("Tip Percent: %");
        this.tipPercent.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setTipOutput() {
        this.tipOutput.setText("Tip:");
        this.tipOutput.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setTotal() {
        this.total.setText("Total:");
        this.total.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setRoundUpText(){
        this.roundUpText.setText("Round up total to the nearest dollar");
        this.roundUpText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
    }

    public void setRoundUpBox(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                calculateTip();
            }
        };
        this.roundUpBox.setOnAction(event);
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public void calculateTip(){
        final DecimalFormat df = new DecimalFormat("0.00");

        double tip = this.tipPercentInt * this.billAmountFloat / 100.00;
        double total = this.billAmountFloat + tip;
        double roundedTotal = Math.ceil(total);
        double roundedTip = roundedTotal - this.billAmountFloat;

        if (this.roundUpBox.isSelected()){
            this.tipOutput.setText("Tip: $" + df.format(roundedTip));
            this.total.setText("Total: $" + df.format(roundedTotal));

        }else {
            this.tipOutput.setText("Tip: $" + df.format(tip));
            this.total.setText("Total: $" + df.format(total));
        }
    }
}
