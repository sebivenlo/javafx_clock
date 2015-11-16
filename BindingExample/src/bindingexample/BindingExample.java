/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bindingexample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author techlogic
 */
public class BindingExample extends Application {

    private TimeUnit unit;

    @Override
    public void start(Stage primaryStage) {
        unit = new TimeUnit(0, 10);
        
        //Create GUI components
        Label label = new Label();
        Button btn = new Button();
        btn.setText("Increment");
        
        //Add increment on click 
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                unit.increment();
            }
        });

        //Bind TimeUnit value to label
        label.textProperty().bind(unit.valueProperty().asString("%02d"));

        //Set up scene
        HBox box = new HBox();
        box.getChildren().add(btn);
        StackPane root = new StackPane();
        root.getChildren().add(label);
        root.getChildren().add(box);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
