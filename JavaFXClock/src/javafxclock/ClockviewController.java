package javafxclock;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
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
    private ToggleButton alarmToggleButton;
    @FXML
    private ToggleButton settingsToggleButton;

    @FXML
    private Label weekdayLabel;
    @FXML
    private Label hourLabel;
    @FXML
    private Label minuteLabel;
    @FXML
    private Label secondLabel;

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
    private Time alarmTime = new Time();

    private Alert alarmAlert = null;
    private final SimpleBooleanProperty alarm = new SimpleBooleanProperty(false);

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

        startApp(startStopToggleButton.selectedProperty().get());
    }

    public void startApp(boolean start) {
        if (start) {
            timeline.play();
        } else {
            timeline.stop();
        }
    }

    private void checkAlarm() {
        if (alarmAlert == null) {
            if (alarm.get() && time.equals(alarmTime)) {
                AlarmPlayer.playAlarmSound();

                alarmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                alarmAlert.setTitle("ALARM");
                alarmAlert.setHeaderText("!!!!!!ALARM ALARM ALARM!!!!!!");
                alarmAlert.setContentText("Do you want to end or pause the alarm?");

                ButtonType pauseButtonType = new ButtonType("Pause", ButtonData.CANCEL_CLOSE);
                ButtonType stopButtonType = new ButtonType("Stop", ButtonData.OK_DONE);

                alarmAlert.getButtonTypes().setAll(pauseButtonType, stopButtonType);

                alarmAlert.show();
                alarmAlert.resultProperty().addListener((ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) -> {
                    if (observable.getValue().equals(stopButtonType)) {
                        alarmToggleButton.selectedProperty().set(false);
                        setAlarmTime(null);
                    } else if (observable.getValue().equals(pauseButtonType)) {
                        alarmTime.minute().setValue(alarmTime.minute().getValue() + 9);
                        alarmToggleButton.setText("Alarm set to " + alarmTime.toString());
                        alarmAlert = null;
                    }
                });
            }
        }
    }

    private void initializeLabels() {
        //bind label with day
        weekdayLabel.textProperty().bind(time.getDayOfWeek());
        //bind label with time
        hourLabel.textProperty().bind(time.getHour());
        minuteLabel.textProperty().bind(time.getMinute());
        secondLabel.textProperty().bind(time.getSecond());
    }

    private void initializeButtons() {
        startStopToggleButton.setOnAction((event) -> {
            startApp(startStopToggleButton.selectedProperty().get());
        });

        alarmToggleButton.setOnAction(this::setAlarmTime);
        alarm.bind(alarmToggleButton.selectedProperty());

        settingsHBox.visibleProperty().bind(settingsToggleButton.selectedProperty());
        syncButton.setOnAction(time::sync);
        addHourButton.setOnAction(time::hourIncrement);
        minusHourButton.setOnAction(time::hourDecrement);
        addMinuteButton.setOnAction(time::minuteIncrement);
        minusMinuteButton.setOnAction(time::minuteDecrement);
        addSecondButton.setOnAction(time::secondIncrement);
        minusSecondButton.setOnAction(time::secondDecrement);
    }

    private void setAlarmTime(ActionEvent event) {
        if (alarmToggleButton.selectedProperty().get()) {
            alarmTime = new Time(
                    time.hour().getValue(),
                    time.minute().getValue(),
                    0,
                    0);

            alarmToggleButton.setText("Alarm set to " + alarmTime.toString());
        } else {
            alarmToggleButton.setText("Set Alarm!");
            alarmAlert = null;
        }
    }
}
