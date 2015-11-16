package javafxclock;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.util.converter.LocalDateTimeStringConverter;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockviewController implements Initializable {

    @FXML
    private HBox settingsHBox;

    @FXML
    private Label timeLabel;

    @FXML
    private Label weekdayLabel;

    @FXML
    private ToggleButton alarmToggleButton;

    @FXML
    private Label dateLabel;
    
    private final Timeline timeline = new Timeline();
    private boolean ticking = false;
    private boolean isAlarmSet = false;

    private LocalDateTime time = LocalDateTime.now();
    private LocalDateTime alarmTime = LocalDateTime.MIN;
    
    @FXML
    private void handleSettingsButtonAction(ActionEvent event) {
        settingsHBox.setVisible(!settingsHBox.isVisible());
    }

    @FXML
    private void handleSyncButtonAction(ActionEvent event) {
        time = LocalDateTime.now();
    }

    @FXML
    private void handleIncrementAction(ActionEvent event) {
        Button sourceBtn = (Button) event.getSource();
        switch (sourceBtn.getId()) {
            case "addHourButton":
                time = time.plusHours(1);
                break;
            case "addMinuteButton":
                time = time.plusMinutes(1);
                break;
            case "addSecondButton":
                time = time.plusSeconds(1);
                break;
            default:
                System.out.println("unknown source " + sourceBtn.getId());
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
            alarmTime = LocalDateTime.of(time.toLocalDate(), time.toLocalTime());
            alarmToggleButton.setText("Set to " + alarmTime.format(DateTimeFormatter.ISO_DATE_TIME));
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
                time = time.minusHours(1);
                break;
            case "minusMinuteButton":
                time = time.minusMinutes(1);
                break;
            case "minusSecondButton":
                time = time.minusSeconds(1);
                break;
            default:
                System.out.println("unknown source " + sourceBtn.getId());
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
                    alarmTime = alarmTime.plusMinutes(9);
                    alarmToggleButton.setText("Set to " + alarmTime.format(DateTimeFormatter.ISO_DATE_TIME));
                }
            });
        }
    }
    
    private StringProperty stringProperty(LocalDateTime ldt) {
        return new SimpleStringProperty(ldt.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        timeline.setCycleCount(Timeline.INDEFINITE);
        //bind label with time
        timeLabel.textProperty().bind(new SimpleStringProperty(time.toLocalTime().format(DateTimeFormatter.ISO_TIME)));
        //bind label with day
        weekdayLabel.textProperty().bind(new SimpleStringProperty(time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK)));
        //bind date
        dateLabel.textProperty().bind(new SimpleStringProperty(time.toLocalDate().format(DateTimeFormatter.ISO_DATE)));
        //add actions to timeLine
        timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.seconds(1), (ActionEvent event) -> {
            //update second
            time = time.plusSeconds(1);
            //check alarmtime
            checkAlarm();
            System.out.println(time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK) + ", " + time.toString()); //toString is nicer readable
        } //we define what should happen every second
        ));
        //start cllock first time
        startStopAction();
    }

}
