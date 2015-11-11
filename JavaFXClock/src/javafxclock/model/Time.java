package javafxclock.model;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 *
 */
public class Time implements Comparable<Time> {

    //total time string hh:mm:ss
    private final StringProperty totalTimeStringProperty = new SimpleStringProperty();
    //alarm time string hh:mm
    private final StringProperty alarmTimeStringProperty = new SimpleStringProperty();

    private TimeUnit hour;
    private TimeUnit minute;
    private TimeUnit second;
    private WeekDay weekday;
    private Date date;

    private final StringProperty hourStringProperty = new SimpleStringProperty();
    private final StringProperty minuteStringProperty = new SimpleStringProperty();
    private final StringProperty secondStringProperty = new SimpleStringProperty();
    private final StringProperty weekdayStringProperty = new SimpleStringProperty();
    private final StringProperty dateStringProperty = new SimpleStringProperty();

    public Time(int hour, int minute, int second, int weekday, Date date) {
        this.hour = new TimeUnit(hour, 24);
        this.minute = new TimeUnit(minute, 60);
        this.second = new TimeUnit(second, 60);
        this.weekday = new WeekDay(weekday);
        this.date = date;
        binding();
        addChangeListeners();
    }

    public Time() {
        LocalDateTime syncTime = LocalDateTime.now();

        this.hour = new TimeUnit(syncTime.getHour(), 24);
        this.minute = new TimeUnit(syncTime.getMinute(), 60);
        this.second = new TimeUnit(syncTime.getSecond(), 60);
        this.weekday = new WeekDay(syncTime.getDayOfWeek().getValue());
        this.date = new Date();
        binding();
        addChangeListeners();
    }

    public TimeUnit getHour() {
        return hour;
    }

    public void setHour(TimeUnit hour) {
        this.hour = hour;
    }

    public TimeUnit getMinute() {
        return minute;
    }

    public void setMinute(TimeUnit minute) {
        this.minute = minute;
    }

    public TimeUnit getSecond() {
        return second;
    }

    public void setSecond(TimeUnit second) {
        this.second = second;
    }

    public WeekDay getWeekday() {
        return weekday;
    }

    public void setWeekday(WeekDay weekday) {
        this.weekday = weekday;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTotalTimeString() {
        return totalTimeStringProperty.get();
    }

    public void setTotalTimeString(String value) {
        totalTimeStringProperty.set(value);
    }

    public StringProperty totalTimeStringProperty() {
        return totalTimeStringProperty;
    }

    public String getAlarmTimeString() {
        return alarmTimeStringProperty.get();
    }

    public void setAlarmTimeString(String value) {
        alarmTimeStringProperty.set(value);
    }

    public StringProperty alarmTimeStringProperty() {
        return alarmTimeStringProperty;
    }

    public String getWeekdayString() {
        return weekdayStringProperty.get();
    }

    public void setWeekdayString(String value) {
        weekdayStringProperty.set(value);
    }

    public StringProperty weekdayStringProperty() {
        return weekdayStringProperty;
    }

    /**
     * bind the string properties with or without leading zero
     */
    private void binding() {
        if (getHour().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            hourStringProperty.bind(leading.concat(getHour().valueProperty().asString()));
        } else {
            hourStringProperty.bind(getHour().valueProperty().asString());
        }

        if (getMinute().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            minuteStringProperty.bind(leading.concat(getMinute().valueProperty().asString()));
        } else {
            minuteStringProperty.bind(getMinute().valueProperty().asString());
        }

        if (getSecond().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            secondStringProperty.bind(leading.concat(getSecond().valueProperty().asString()));
        } else {
            secondStringProperty.bind(getSecond().valueProperty().asString());

        }

        weekdayStringProperty.bind(getWeekday().dayStringProperty());

        totalTimeStringProperty.bind(hourStringProperty.concat(" : ").concat(minuteStringProperty).concat(" : ").concat(secondStringProperty));

        alarmTimeStringProperty.bind(hourStringProperty.concat(" : ").concat(minuteStringProperty));
    }

    private void addChangeListeners() {
        //add Listeners to minutes
        getMinute().valueProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            if ((int) newValue >= getMinute().getMax()) {
                getHour().increment();
                getMinute().setValue(0);
            }
            if ((int) newValue < 0) {

                getHour().decrement();
                getMinute().setValue(getMinute().getMax() - 1);
            }
            binding();
        });
        //add listeners for seconds
        getSecond().valueProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            if ((int) newValue >= getSecond().getMax()) {
                getMinute().increment();
                getSecond().setValue(0);

            }
            if ((int) newValue < 0) {
                //TODO update min

                getMinute().decrement();
                getSecond().setValue(getSecond().getMax() - 1);
            }

            binding();
        });

        //add listeners for hours
        getHour().valueProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            if ((int) newValue >= getHour().getMax()) {
                getWeekday().increment();
                getHour().setValue(0);
            }
            if ((int) newValue < 0) {
                //TODO update days
                getHour().setValue(getHour().getMax() - 1);
                getWeekday().decrement();
            }
            binding();
        });
        //add listener for days
        getWeekday().valueProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            int x = getWeekday().valueProperty().getValue();
            getWeekday().setDayString(getWeekday().daysOfWeek[x - 1]);
            weekdayStringProperty.bind(getWeekday().dayStringProperty());
        });
    }

    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        getHour().setValue(syncTime.getHour());
        getMinute().setValue(syncTime.getMinute());
        getSecond().setValue(syncTime.getSecond());
        getWeekday().setValue(syncTime.getDayOfWeek().getValue());
    }

    public void tick() {
        getSecond().increment();
    }

    public void decrement(TimeUnit timeUnit) {
        timeUnit.decrement();

    }

    public void increment(TimeUnit timeUnit) {
        timeUnit.increment();
    }

    @Override
    public String toString() {
        return getDate() + " " + getWeekday() + " " + getHour() + " : " + getMinute() + " : " + getSecond();
    }

    @Override
    public int compareTo(Time time) {
        return this.totalTimeStringProperty.get().compareToIgnoreCase(time.totalTimeStringProperty.get());
    }
}
