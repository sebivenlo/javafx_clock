package javafxclock.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
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
    @FXML
    private ToggleButton alarmToggleButton;

    private final Timeline timeline = new Timeline();
    private final Time time = new Time();
    private Time alarmTime = new Time();
    private boolean ticking = false;
    private boolean isAlarmSet = false;

    @FXML
    private void handleSettingsButtonAction(ActionEvent event) {
        settingsHBox.setVisible(!settingsHBox.isVisible());
    }

    @FXML
    private void handleSyncButtonAction(ActionEvent event) {
        time.sync();
    }

    @FXML
    private void handleIncrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        switch (sourceBtn.getId()) {
            case "addHourButton":
                time.increment(time.getHour());
                break;
            case "addMinuteButton":
                time.increment(time.getMinute());
                break;
            case "addSecondButton":
                time.increment(time.getSecond());
                break;
            default:
                break;
        }
    }

    @FXML
    private void handleAlarmToggleButtonAction(ActionEvent event) {
        isAlarmSet = !isAlarmSet;
        toggleAlarm();
    }

    private void toggleAlarm() {
        if (!isAlarmSet) {
            alarmToggleButton.setText("Set Alarm!");
            if (alarmToggleButton.isSelected()) {
                alarmToggleButton.setSelected(isAlarmSet);
            }
        } else {
            alarmTime = new Time(time.getHour().getValue(), time.getMinute().getValue(), 0, time.getWeekday().getValue(), time.getDate());
            alarmToggleButton.setText("Set to " + alarmTime.getAlarmTimeString());
        }
    }

    @FXML
    private void handleStartStopToggleButtonAction(ActionEvent event) {
        startStopAction();
    }

    @FXML
    private void handleDecrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        switch (sourceBtn.getId()) {
            case "minusHourButton":
                time.decrement(time.getHour());
                break;
            case "minusMinuteButton":
                time.decrement(time.getMinute());
                break;
            case "minusSecondButton":
                time.decrement(time.getSecond());
                break;
            default:
                break;
        }
    }

    /**
     * starts and stops the timeline animation
     */
    private void startStopAction() {
        if (ticking) {
            timeline.pause();
            ticking = false;
        } else {
            timeline.play();
            ticking = true;
        }
    }

    private void checkAlarm() {
        if (isAlarmSet && (time.compareTo(alarmTime) == 0)) {
            AlarmPlayer.playAlarmSound();

            Alert alarmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            alarmAlert.setTitle("ALARM");
            alarmAlert.setHeaderText("!!!!!!ALARM ALARM ALARM!!!!!!");
            alarmAlert.setContentText("Do you want to end or pause the alarm?");

            ButtonType pauseButtonType = new ButtonType("Pause", ButtonData.CANCEL_CLOSE);
            ButtonType stopButtonType = new ButtonType("Stop", ButtonData.OK_DONE);

            alarmAlert.getButtonTypes().setAll(pauseButtonType, stopButtonType);

            alarmAlert.show();
            alarmAlert.resultProperty().addListener((ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) -> {
                if (observable.getValue().equals(stopButtonType)) {
                    isAlarmSet = false;
                    toggleAlarm();
                } else if (observable.getValue().equals(pauseButtonType)) {
                    alarmTime.getMinute().setValue(alarmTime.getMinute().getValue() + 9);
                    alarmToggleButton.setText("Set to " + alarmTime.getAlarmTimeString());
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline.setCycleCount(Timeline.INDEFINITE);
        //bind label with time
        timeLabel.textProperty().bind(time.totalTimeStringProperty());
        //bind label with day
        weekdayLabel.textProperty().bind(time.weekdayStringProperty());

        //add actions to timeLine
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), (ActionEvent event) -> {
            time.tick();
            checkAlarm();
//                System.out.println(time.getDay().toString() + ", " + time.toString()); //toString is nicer readable
        } //we define what should happen every second
        ));
        //start cllock first time
        startStopAction();
    }

}
