package javafxclock;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    private static final Logger LOG = Logger.getLogger(ClockviewController.class.getName());

    @FXML
    private ToggleButton startStopButton;
    @FXML
    private ToggleButton alarmButton;
    @FXML
    private ToggleButton settingsButton;

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

    private final TimeManager time = new TimeManager();
    private TimeManager alarmTime = new TimeManager();

    private final SimpleBooleanProperty alarm = new SimpleBooleanProperty(false);
    private boolean alarmRuns;

    private final DoubleProperty opacity = new SimpleDoubleProperty(1);

    public ClockviewController()
    {
    }

    @Override
    public void initialize(URL url,
                           ResourceBundle resourceBundle)
    {
        initializeTimeline();
        initializeLabels();
        initializeButtons();
    }

    private void initializeTimeline()
    {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(seconds(1), (ActionEvent event) ->
                                                 {
                                                     checkLabel();
                                                     time.tick();
                                                     if (Integer.compare(time.getSecond().getValue(),
                                                                         0) == 0)
                                                     {
                                                         checkAlarm();
                                                     }
                                                 }));

        startApp(startStopButton.selectedProperty().get());
    }

    private void checkLabel()
    {
        final double DEFAULT_OPACITY = 1.0;
        
        if (alarmRuns)
        {
            if (opacity.getValue() >= DEFAULT_OPACITY)
            {
                opacity.setValue(0.5);
            }
            else
            {
                opacity.setValue(opacity.getValue() + 0.5);
            }
        }
    }

    private void checkAlarm()
    {
        if (alarm.get() && time.equals(alarmTime))
        {
            AlarmPlayer.playAlarmSound();
            alarmRuns = true;
            alarmButton.setText("SNOOZE");
        }
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

    private void initializeLabels()
    {
        //bind label with day
        weekdayLabel.textProperty().bind(time.getDayOfWeekProperty());
        //bind label with time
        hourLabel.textProperty().bind(time.getHourBinding());
        hourLabel.opacityProperty().bind(opacity);
        minuteLabel.textProperty().bind(time.getMinuteBinding());
        minuteLabel.opacityProperty().bind(opacity);
        secondLabel.textProperty().bind(time.getSecondBinding());
        secondLabel.opacityProperty().bind(opacity);

        opacity.set(0.5);
    }

    private void initializeButtons()
    {
        startStopButton.setOnAction((ActionEvent event) ->
                {
                    startApp(startStopButton.selectedProperty().get());
                });

        alarmButton.setOnAction(this::setAlarmTime);
        alarm.bind(alarmButton.selectedProperty());

        settingsHBox.visibleProperty().bind(
                settingsButton.selectedProperty());
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
        LOG.info(event.toString());

        if (alarmButton.selectedProperty().get())
        {
            alarmTime = new TimeManager(
                    time.getHour().getValue(),
                    time.getMinute().getValue(),
                    0,
                    0);

            alarmButton.setText("Alarm set to " + alarmTime.toHhMmString());
        }
        else
        {
            alarmButton.setText("Set Alarm!");
            if (alarmRuns)
            {
                alarmRuns = false;
                opacity.set(1.0);
            }
        }
    }
}
