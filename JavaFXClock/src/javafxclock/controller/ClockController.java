package javafxclock.controller;

import clock_ws.Time;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockController implements Initializable {

    @FXML
    private Label timeLabel;
    private Timeline tl = new Timeline();
    private Time time = new Time();

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // TODO
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tl.setCycleCount(Timeline.INDEFINITE);
        //bind label with time
        timeLabel.textProperty().bind(time.total);
        //add actions to timeLine
        tl.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            //we define what should happen every second
            public void handle(ActionEvent event) {
                time.tick();
                System.out.println(time.toString()); //toString is nicer readable
            }
        }));
        tl.play();
    }

}
