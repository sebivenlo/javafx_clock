package javafxclock;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import static javafx.util.Duration.seconds;

/**
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class ClockviewController implements Initializable
{

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
    public void initialize(URL url,
                           ResourceBundle rb)
    {
        initializeTimeline();
        initializeLabels();
        initializeButtons();
    }

    private void initializeTimeline()
    {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(seconds(1),
                                                 (ActionEvent event)
                                                 -> 
                                                 {
                                                     time.tick();
                                                     if (Integer.compare(
                                                             time.second().getValue(),
                                                             0) == 0)
                                                     {
                                                         checkAlarm();
                                                     }
                                         }));

        startApp(startStopToggleButton.selectedProperty().get());
    }

    /**
     * This method start or stops the clock.
     *
     * @param start if true clock starts, otherwise clock will be stopped
     */
    public void startApp(boolean start)
    {
        if (start)
        {
            timeline.play();
        }
        else
        {
            timeline.stop();
        }
    }

    /**
     * This method checks if the alarm should be start.
     */
    private void checkAlarm()
    {
        if (alarmAlert == null)
        {
            if (alarm.get() && time.equals(alarmTime))
            {
                AlarmPlayer.playAlarmSound();
            }
        }
    }

    private void initializeLabels()
    {
        //bind label with day
        weekdayLabel.textProperty().bind(time.getDayOfWeek());
        //bind label with time
        hourLabel.textProperty().bind(time.getHour());
        minuteLabel.textProperty().bind(time.getMinute());
        secondLabel.textProperty().bind(time.getSecond());
    }

    private void initializeButtons()
    {
        startStopToggleButton.setOnAction((ActionEvent event)
                -> 
                {
                    startApp(startStopToggleButton.selectedProperty().get());
        });

        alarmToggleButton.setOnAction(this::setAlarmTime);
        alarm.bind(alarmToggleButton.selectedProperty());

        settingsHBox.visibleProperty().bind(
                settingsToggleButton.selectedProperty());
        syncButton.setOnAction(time::sync);
        addHourButton.setOnAction(time::hourIncrement);
        minusHourButton.setOnAction(time::hourDecrement);
        addMinuteButton.setOnAction(time::minuteIncrement);
        minusMinuteButton.setOnAction(time::minuteDecrement);
        addSecondButton.setOnAction(time::secondIncrement);
        minusSecondButton.setOnAction(time::secondDecrement);
    }

    private void setAlarmTime(ActionEvent event)
    {
        if (alarmToggleButton.selectedProperty().get())
        {
            alarmTime = new Time(
                    time.hour().getValue(),
                    time.minute().getValue(),
                    0,
                    0);

            alarmToggleButton.setText("Alarm set to " + alarmTime.toHhMmString());
        }
        else
        {
            alarmToggleButton.setText("Set Alarm!");
            alarmAlert = null;
        }
    }
}
