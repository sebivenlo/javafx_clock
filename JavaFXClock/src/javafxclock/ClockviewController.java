package javafxclock;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import nl.fontys.sebivenlo.tickerhelper.TickerHelper;

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

    private final SimpleBooleanProperty alarm = new SimpleBooleanProperty(false);

    private TickerHelper tickerHelper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeTimeline();
        initializeLabels();
        initializeButtons();
    }

    private void initializeTimeline() {
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.getKeyFrames().add(new KeyFrame(seconds(1), (ActionEvent event) -> {
//            time.tick();
//
//            checkAlarm();
//        }));
//        timeline.play();

        tickerHelper = new TickerHelper(this::tick);
        tickerHelper.start();
        Platform.runLater(time::sync);
    }

    void tick() {
        Platform.runLater(time::second);
    }

    public void stopApp() {
        tickerHelper.stop();
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

    private void initializeLabels() {
        //bind label with day
        weekdayLabel.textProperty().bind(time.getWeekday());
        //bind label with time
        hourLabel.textProperty().bind(time.getHour());
        minuteLabel.textProperty().bind(time.getMinute());
        secondLabel.textProperty().bind(time.getSecond());
    }

    private void initializeButtons() {
        startStopToggleButton.setOnAction((event) -> {
            System.out.println(startStopToggleButton.selectedProperty());
            stopApp();
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
        alarmTime = new Time();
    }
}
