//DONE 1 - Round tip and total to 2 decimal places
//DONE 2 - Improve formatting and appearance
//DONE 3 - Add checkbox to round up to nearest dollar
//TODO 4 - Add splitting by number of people

package com.tipcalculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TipCalculator extends Application {
    @Override
    public void start(Stage stage) {
        UI ui = new UI();
        ui.buildLayout();

        Scene scene = new Scene(ui.getGridPane());
        stage.setTitle("Tip Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}