/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.gui;

import java.util.Calendar;
import java.util.Date;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import nl.fontys.jfxclock.clock.ClockTime;

/**
 *
 * @author max
 *
 */
public class ClockStarter extends Application {

    //main timeline
    private Timeline timeline;
    private AnimationTimer timer;
    private final ClockTime clock = new ClockTime();
    private Button addHhrs;
    private Button addMin;
    private Button subHrs;
    private Button subMin;
    //variable for storing actual frame
    private Integer i = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Clock");

        // example from http://docs.oracle.com/javase/8/javafx/visual-effects-tutorial/basics.htm#BEIIDFJC 
//        BorderPane border = new BorderPane();
//        HBox hbox = new HBox();
//        border.setTop(hbox);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 300, 275);
        
        primaryStage.setScene(scene);

        //create a circle with effect
        final Circle circle = new Circle(100, Color.rgb(156, 216, 255));
        circle.setEffect(new Lighting());

        //create a text inside a circle
        final Text scenetitle = new Text(i.toString());
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setStroke(Color.BLACK);

        //create a layout for circle with text inside
        final StackPane stack = new StackPane();
        addHhrs = new Button("+ hrs");
        addMin = new Button("+ min");
        subHrs = new Button("- hrs");
        subMin = new Button("- min");

        stack.getChildren().addAll(scenetitle);
        stack.setLayoutX(30);
        stack.setLayoutY(30);
        grid.add(stack,0,0);
        grid.add(circle,0,1);
        grid.add(addHhrs,0,2);
        grid.add(addMin,1,2);
        grid.add(subHrs,2,2);
        grid.add(subMin,3,2);

        primaryStage.show();

        //create a timeline for moving the circle
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        //You can add a specific action when each frame is started.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                clock.tick();
                scenetitle.setText(clock.toString());
            }

        };

        //create a keyValue with factory: scaling the circle 2times
        //  KeyValue keyValueX = new KeyValue(stack.scaleXProperty(), 2);
        //  KeyValue keyValueY = new KeyValue(stack.scaleYProperty(), 2);
        //create a keyFrame, the keyValue is reached at time 2s
        Duration duration = Duration.millis(1000);
        //one can add a specific action when the keyframe is reached
        EventHandler onFinished = (EventHandler<ActionEvent>) (ActionEvent t) -> {
            // stack.setTranslateX(java.lang.Math.random()*200-100);
            //reset counter
            i = 0;
        };

        timeline.play();
        timer.start();
    }

    private long getTimeAndDate() {
        return new Date().getTime();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
