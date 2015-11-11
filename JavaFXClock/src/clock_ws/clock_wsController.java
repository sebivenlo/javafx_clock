/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clock_ws;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author max
 */
public class clock_wsController implements Initializable {

    @FXML
    private Label label;
    private Timeline tl = new Timeline();
    private Time time = new Time(3, 59, 56);

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button btn = (Button) event.getSource();
        if (btn.getId().equals("hrIncr")) {
            time.increment(time.getHour());
        } else if (btn.getId().equals("minIncr")) {
            time.increment(time.getMinute());
        } else if (btn.getId().equals("secIncr")) {
            time.increment(time.getSecond());
        } else if (btn.getId().equals("hrDecr")) {
            time.decrement(time.getHour());
        } else if (btn.getId().equals("minDecr")) {
            time.decrement(time.getMinute());
        } else if (btn.getId().equals("secDecr")) {
            time.decrement(time.getSecond());
        } else if (btn.getId().equals("sync")) {
            time.sync();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tl.setCycleCount(Timeline.INDEFINITE);
        //add actions to timeLine
        label.textProperty().bind(time.total);
        tl.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            //we define what should happen
            public void handle(ActionEvent event) {
                time.tick();
            }
        }));
        tl.play();
    }

}
