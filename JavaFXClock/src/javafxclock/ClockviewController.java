package javafxclock;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import nl.fontys.sebivenlo.tickerhelper.TickerHelper;

/**
 * @author Ron Gebauer &lt;mail@ron.gebauers.org&gt;
 * @version 1
 */
public class ClockviewController implements Initializable {

    private WeekDay weekday = new WeekDay( 7 );
    
    @FXML
    private Label weekdayLabel;
    @FXML
    private HBox settingsHBox;
    TimeUnit hour = new TimeUnit( 24 ).named( "h" ).setNext( weekday );
    @FXML
    private Label hourLabel;
    TimeUnit minute = new TimeUnit( 60 ).named( "m" ).setNext( hour );
    @FXML
    private Label minLabel;
    TimeUnit second = new TimeUnit( 60 ).named( "s" ).setNext( minute );
    @FXML
    private Label secLabel;

    @FXML
    private ToggleButton alarmToggleButton;

    @FXML
    private Label dateLabel;
//    private final Timeline timeline = new Timeline();
    
    private TimeUnit alarmHour = new TimeUnit(24).named( "ah");
    @FXML
    private Label alarmHourLabel;
    
    private TimeUnit alarmMinute = new TimeUnit(60).named( "am");;
    @FXML
    private Label alarmMinLabel;
//    private final Time time = new Time();
//    private Time alarmTime = new Time();
    private boolean ticking = false;
    private boolean isAlarmSet = false;

    @FXML
    private void handleSettingsButtonAction( ActionEvent event ) {
        settingsHBox.setVisible( !settingsHBox.isVisible() );
    }

    @FXML
    private void handleSyncButtonAction( ActionEvent event ) {
        this.sync();
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
                    + alarmHourLabel.getText()+ ':'+ alarmMinLabel.getText());
        }
    }

    private void checkAlarm() {
        if (! minute.equals( alarmMinute)) return ;
        if (! hour.equals( alarmHour)) return;
            AlarmPlayer.playAlarmSound();

//            Alert alarmAlert = new Alert( Alert.AlertType.CONFIRMATION );
//            alarmAlert.setTitle( "ALARM" );
//            alarmAlert.setHeaderText( "!!!!!!ALARM ALARM ALARM!!!!!!" );
//            alarmAlert.setContentText( "Do you want to end or pause the alarm?" );
//
//            ButtonType pauseButtonType = new ButtonType( "Pause",
//                    ButtonData.CANCEL_CLOSE );
//            ButtonType stopButtonType = new ButtonType( "Stop",
//                    ButtonData.OK_DONE );
//
//            alarmAlert.getButtonTypes().setAll( pauseButtonType, stopButtonType );
//
//            alarmAlert.show();
//            alarmAlert.resultProperty().addListener(
//                    ( ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue ) -> {
//                if ( observable.getValue().equals( stopButtonType ) ) {
//                    isAlarmSet = false;
//                    toggleAlarm();
//                } else if ( observable.getValue().equals( pauseButtonType ) ) {
//                    alarmTime.getMinute().setValue(
//                            alarmTime.getMinute().getValue() + 9 );
//                    alarmToggleButton.setText( "Set to "
//                            + alarmTime.getAlarmTimeString() );
//                }
//            } );
    }
    private TickerHelper tickerHelper;

    @Override
    public void initialize( URL url, ResourceBundle rb ) {
        //bind label with time
        hourLabel.textProperty().bind( hour.asStringBinding() );
        minLabel.textProperty().bind( minute.asStringBinding() );
        secLabel.textProperty().bind( second.asStringBinding() );
        //bind label with day
        weekdayLabel.textProperty().bind( weekday.dayStringProperty() );
        //bind date
        //dateLabel.textProperty().bind( time.dateStringProperty() );
        alarmHourLabel.textProperty().bind( alarmHour.asStringBinding());
        alarmMinLabel.textProperty().bind( alarmMinute.asStringBinding());
        tickerHelper = new TickerHelper( this::tick );
        tickerHelper.start();
        Platform.runLater( this::sync );

    }

    /**
     *
     */
    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        hour.setValue( syncTime.getHour() );
        minute.setValue( syncTime.getMinute() );
        second.setValue( syncTime.getSecond() );
        weekday.setValue( syncTime.getDayOfWeek().getValue() );
        
    }

    void tick() {
        Platform.runLater( second::increment );
    }

    public void stopApp() {
        tickerHelper.stop();
    }

    static ClockviewController INSTANCE;

    public ClockviewController() {
        INSTANCE = this;
    }

}
