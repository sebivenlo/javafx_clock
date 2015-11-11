package javafxclock.controller;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafxclock.model.Time;
import javafxclock.util.AlarmPlayer;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockController implements Initializable {

    @FXML
    private HBox settingsHBox;
    @FXML
    private Label timeLabel;
    @FXML
    private Label weekdayLabel;
    private Timeline tl = new Timeline();
    private Time time = new Time(23,59,55,2);
    private Time alarmTime=new Time();
    private boolean ticking = false;
    private boolean isAlarmSet = false;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // TODO
    }

    @FXML
    private void handleSettingsButtonAction(ActionEvent event) {
        if(settingsHBox.isVisible()) {
            settingsHBox.setVisible(false);
        }
        else {
            settingsHBox.setVisible(true);
        }
    }
    @FXML
    private void handleSyncButtonAction(ActionEvent event) {
        time.sync();
    }

    @FXML
    private void handleIncrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        if (sourceBtn.getId().equals("addHourButton")) {
            time.increment(time.getHour());
        } else if (sourceBtn.getId().equals("addMinuteButton")) {
            time.increment(time.getMinute());
        }
    }

    @FXML
    private void handleAlarmToggleButtonAction(ActionEvent event) {
        if (isAlarmSet) {
            isAlarmSet = false;
        } else {
            alarmTime = new Time(time.getHour().getValue(), time.getMinute().getValue(), time.getSecond().getValue(), time.getDay().getValue());
            isAlarmSet = true;
        }
        if(isAlarmSet) {
            System.out.println("alarm is set");
        }
        else {
            System.out.println("alarm not set");
        }
    }

    @FXML
    private void handleStartStopToggleButtonAction(ActionEvent event) {
        startStopAction();
    }

    @FXML
    private void handleDecrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        if (sourceBtn.getId().equals("minusHourButton")) {
            time.decrement(time.getHour());
        } else if (sourceBtn.getId().equals("minusMinuteButton")) {
            time.decrement(time.getMinute());
        }
    }

    /**
     * starts and stops the timeline animation
     */
    private void startStopAction() {
        if (ticking) {
            tl.pause();
            ticking = false;
        } else {
            tl.play();
            ticking = true;
        }
    }
    private void checkAlarm() {
        if(isAlarmSet && time==alarmTime) {
            AlarmPlayer.playAlarmSound();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tl.setCycleCount(Timeline.INDEFINITE);
        //bind label with time
        timeLabel.textProperty().bind(time.total);
        //bind label with day
        weekdayLabel.textProperty().bind(time.dStr);

        //add actions to timeLine
        tl.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            //we define what should happen every second
            public void handle(ActionEvent event) {
                time.tick();
                checkAlarm();
                System.out.println(time.getDay().toString()+", "+time.toString()); //toString is nicer readable
            }
        }));
        //start cllock first time
        startStopAction();
    }

}
