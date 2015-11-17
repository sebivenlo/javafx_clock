package javafxclock;

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
import static javafx.util.Duration.seconds;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockviewController implements Initializable {

    @FXML
    private ToggleButton startStopToggleButton;

    @FXML
    private Label weekdayLabel;

    @FXML
    private Label hourLabel;
    @FXML
    private Label minuteLabel;
    @FXML
    private Label secondLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private ToggleButton alarmToggleButton;

    @FXML
    private ToggleButton settingsToggleButton;
    @FXML
    private HBox settingsHBox;
    @FXML
    private Button syncButton;
    @FXML
    private Button addHourButton;
    @FXML
    private Button minusHourButton;
    @FXML
    private Button addMinuteButton;
    @FXML
    private Button minusMinuteButton;
    @FXML
    private Button addSecondButton;
    @FXML
    private Button minusSecondButton;

    private final Timeline timeline = new Timeline();

    private final Time time = new Time();
    private final Time alarmTime = new Time();

    private boolean ticking = true;
    private boolean isAlarmSet = false;

    private void toggleAlarm() {
//        if (!isAlarmSet) {
//            alarmToggleButton.setText("Set Alarm!");
//            if (alarmToggleButton.isSelected()) {
//                alarmToggleButton.setSelected(isAlarmSet);
//            }
//        } else {
////            alarmTime = new Time(time.getHourTimeUnit().getValue(), time.getMinute().getValue(), 0, time.getWeekday().getValue(), time.getDate());
////            alarmToggleButton.setText("Set to " + alarmTime.getAlarmTimeString());
//        }
    }

    private void checkAlarm() {
//        if (isAlarmSet && (time.compareTo(alarmTime) == 0)) {
//            AlarmPlayer.playAlarmSound();
//
//            Alert alarmAlert = new Alert(Alert.AlertType.CONFIRMATION);
//            alarmAlert.setTitle("ALARM");
//            alarmAlert.setHeaderText("!!!!!!ALARM ALARM ALARM!!!!!!");
//            alarmAlert.setContentText("Do you want to end or pause the alarm?");
//
//            ButtonType pauseButtonType = new ButtonType("Pause", ButtonData.CANCEL_CLOSE);
//            ButtonType stopButtonType = new ButtonType("Stop", ButtonData.OK_DONE);
//
//            alarmAlert.getButtonTypes().setAll(pauseButtonType, stopButtonType);
//
//            alarmAlert.show();
//            alarmAlert.resultProperty().addListener((ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) -> {
//                if (observable.getValue().equals(stopButtonType)) {
//                    isAlarmSet = false;
//                    toggleAlarm();
//                } else if (observable.getValue().equals(pauseButtonType)) {
////                    alarmTime.getMinute().setValue(alarmTime.getMinute().getValue() + 9);
////                    alarmToggleButton.setText("Set to " + alarmTime.getAlarmTimeString());
//                }
//            });
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTimeline();
        initializeLabels();
        initializeButtons();
    }

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(seconds(1), (ActionEvent event) -> {
            time.tick();

            checkAlarm();
        }));
        timeline.play();
    }

    private void initializeLabels() {
        //bind label with day
        weekdayLabel.textProperty().bind(time.getWeekdayTimeUnit().weekdayProperty());
        //bind label with time
        hourLabel.textProperty().bind(time.getHourTimeUnit().valueProperty().asString("%02d"));
        minuteLabel.textProperty().bind(time.getMinuteTimeUnit().valueProperty().asString("%02d"));
        secondLabel.textProperty().bind(time.getSecondTimeUnit().valueProperty().asString("%02d"));
        //bind date
        dateLabel.textProperty().bind(time.getCustomDate().dateStringProperty());
    }

    private void initializeButtons() {
        startStopToggleButton.setOnAction((event) -> {
            ticking = !ticking;
            if (!ticking) {
                timeline.pause();
            } else {
                timeline.play();
            }
        });

        alarmToggleButton.setOnAction((event) -> {
            isAlarmSet = !isAlarmSet;
            toggleAlarm();
        });

        settingsToggleButton.setOnAction((event) -> {
            settingsHBox.setVisible(!settingsHBox.isVisible());
        });
        syncButton.setOnAction((event) -> {
            // syncronize time with local time
            time.sync();
        });
        addHourButton.setOnAction((event) -> {
            time.getHourTimeUnit().increment();
        });
        minusHourButton.setOnAction((event) -> {
            time.getHourTimeUnit().decrement();
        });
        addMinuteButton.setOnAction((event) -> {
            time.getMinuteTimeUnit().increment();
        });
        minusMinuteButton.setOnAction((event) -> {
            time.getMinuteTimeUnit().decrement();
        });
        addSecondButton.setOnAction((event) -> {
            time.getSecondTimeUnit().increment();
        });
        minusSecondButton.setOnAction((event) -> {
            time.getSecondTimeUnit().decrement();
        });
    }
}
