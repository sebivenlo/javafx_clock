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

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockviewController implements Initializable {

    private WeekDay weekday = new WeekDay( 7 );
    @FXML
    private Label weekdayLabel;
    @FXML
    private HBox settingsHBox;
    TimeUnit hour = new TimeUnit( 24 ).named("h").setNext( weekday );
    @FXML
    private Label hourLabel;
    TimeUnit minute = new TimeUnit( 60 ).named("m").setNext( hour );
    @FXML
    private Label minLabel;
    TimeUnit second = new TimeUnit( 60 ).named("s").setNext( minute );
    @FXML
    private Label secLabel;

    @FXML
    private ToggleButton alarmToggleButton;

    @FXML
    private Label dateLabel;
    private final Timeline timeline = new Timeline();
    private final Time time = new Time();
    private Time alarmTime = new Time();
    private boolean ticking = false;
    private boolean isAlarmSet = false;

    @FXML
    private void handleSettingsButtonAction( ActionEvent event ) {
        settingsHBox.setVisible( !settingsHBox.isVisible() );
    }

    @FXML
    private void handleSyncButtonAction( ActionEvent event ) {
        time.sync();
    }

    @FXML
    private void incrementSeconds( ActionEvent e ) {
        second.increment();
    }

    @FXML
    private void decrementSeconds( ActionEvent e ) {
        second.decrement();
    }

    @FXML
    private void incrementMinutes( ActionEvent e ) {
        minute.increment();
    }

    @FXML
    private void decrementMinutes( ActionEvent e ) {
        minute.decrement();
    }

    @FXML
    private void incrementHours( ActionEvent e ) {
        hour.increment();
    }

    @FXML
    private void decrementHours( ActionEvent e ) {
        hour.decrement();
    }

    @FXML
    private void handleAlarmToggleButtonAction( ActionEvent event ) {
        isAlarmSet = !isAlarmSet;
        toggleAlarm();
    }

    private void toggleAlarm() {
        if ( !isAlarmSet ) {
            alarmToggleButton.setText( "Set Alarm!" );
            if ( alarmToggleButton.isSelected() ) {
                alarmToggleButton.setSelected( isAlarmSet );
            }
        } else {
            // alarmTime = new Time(time.getHour().getValue(), time.getMinute().getValue(), 0, time.getWeekday().getValue(), time.getDate());
            alarmToggleButton.setText( "Set to "
                    + alarmTime.getAlarmTimeString() );
        }
    }

    @FXML
    private void handleStartStopToggleButtonAction( ActionEvent event ) {
        startStopAction();
    }

    /**
     * starts and stops the timeline animation
     */
    private void startStopAction() {
        if ( ticking ) {
            timeline.pause();
            ticking = false;
        } else {
            timeline.play();
            ticking = true;
        }
    }

    private void checkAlarm() {
        if ( isAlarmSet && ( time.equals( alarmTime ) ) ) {
            AlarmPlayer.playAlarmSound();

            Alert alarmAlert = new Alert( Alert.AlertType.CONFIRMATION );
            alarmAlert.setTitle( "ALARM" );
            alarmAlert.setHeaderText( "!!!!!!ALARM ALARM ALARM!!!!!!" );
            alarmAlert.setContentText( "Do you want to end or pause the alarm?" );

            ButtonType pauseButtonType = new ButtonType( "Pause",
                    ButtonData.CANCEL_CLOSE );
            ButtonType stopButtonType = new ButtonType( "Stop",
                    ButtonData.OK_DONE );

            alarmAlert.getButtonTypes().setAll( pauseButtonType, stopButtonType );

            alarmAlert.show();
            alarmAlert.resultProperty().addListener(
                    ( ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue ) -> {
                if ( observable.getValue().equals( stopButtonType ) ) {
                    isAlarmSet = false;
                    toggleAlarm();
                } else if ( observable.getValue().equals( pauseButtonType ) ) {
                    alarmTime.getMinute().setValue(
                            alarmTime.getMinute().getValue() + 9 );
                    alarmToggleButton.setText( "Set to "
                            + alarmTime.getAlarmTimeString() );
                }
            } );
        }
    }

    @Override
    public void initialize( URL url, ResourceBundle rb ) {
        timeline.setCycleCount( Timeline.INDEFINITE );
        //bind label with time
        hourLabel.textProperty().bind( hour.asStringBinding() );
        minLabel.textProperty().bind( minute.asStringBinding() );
        secLabel.textProperty().bind( second.asStringBinding() );
        //bind label with day
        weekdayLabel.textProperty().bind( weekday.dayStringProperty());
        //bind date
        dateLabel.textProperty().bind( time.dateStringProperty() );
        //add actions to timeLine
        timeline.getKeyFrames().add( new KeyFrame(
                javafx.util.Duration.seconds( 1 ), ( ActionEvent event ) -> {
            //update second
            this.tick();
            //check alarmtime
            checkAlarm();
            System.out.println( time.getWeekday() + ", " + time.toString() ); //toString is nicer readable
        } //we define what should happen every second
        ) );
        //start cllock first time
        startStopAction();
    }

    void tick() {
        second.increment();
    }
}
