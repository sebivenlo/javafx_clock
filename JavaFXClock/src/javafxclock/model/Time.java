package javafxclock.model;

import java.time.LocalDateTime;
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
    private final StringProperty totalTimeString = new SimpleStringProperty();
    //alarm time string hh:mm
    private final StringProperty alarmTimeString = new SimpleStringProperty();

    private TimeUnit hour;
    private TimeUnit minute;
    private TimeUnit second;
    private WeekDay weekday;
    private CustomDate date;

    private final StringProperty hourString = new SimpleStringProperty();
    private final StringProperty minuteString = new SimpleStringProperty();
    private final StringProperty secondString = new SimpleStringProperty();
    private final StringProperty weekdayString = new SimpleStringProperty();
    private final StringProperty dateString = new SimpleStringProperty();

    public Time(int hour, int minute, int second, int weekday, CustomDate date) {
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
        this.date = new CustomDate();
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

    public CustomDate getDate() {
        return date;
    }

    public void setDate(CustomDate date) {
        this.date = date;
    }

    public String getTotalTimeString() {
        return totalTimeString.get();
    }

    public void setTotalTimeString(String value) {
        totalTimeString.set(value);
    }

    public StringProperty totalTimeStringProperty() {
        return totalTimeString;
    }

    public String getAlarmTimeString() {
        return alarmTimeString.get();
    }

    public void setAlarmTimeString(String value) {
        alarmTimeString.set(value);
    }

    public StringProperty alarmTimeStringProperty() {
        return alarmTimeString;
    }

    public String getWeekdayString() {
        return weekdayString.get();
    }

    public void setWeekdayString(String value) {
        weekdayString.set(value);
    }

    public StringProperty weekdayStringProperty() {
        return weekdayString;
    }

    /**
     * bind the string properties with or without leading zero
     */
    private void binding() {
        if (getHour().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            hourString.bind(leading.concat(getHour().valueProperty().asString()));
        } else {
            hourString.bind(getHour().valueProperty().asString());
        }

        if (getMinute().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            minuteString.bind(leading.concat(getMinute().valueProperty().asString()));
        } else {
            minuteString.bind(getMinute().valueProperty().asString());
        }

        if (getSecond().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            secondString.bind(leading.concat(getSecond().valueProperty().asString()));
        } else {
            secondString.bind(getSecond().valueProperty().asString());

        }

        weekdayString.bind(getWeekday().dayStringProperty());

        totalTimeString.bind(hourString.concat(" : ").concat(minuteString).concat(" : ").concat(secondString));

        alarmTimeString.bind(hourString.concat(" : ").concat(minuteString));
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
            weekdayString.bind(getWeekday().dayStringProperty());
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
        return getDate() + " " + getWeekday() + ", " + getHour() + " : " + getMinute() + " : " + getSecond();
    }

    @Override
    public int compareTo(Time time) {
        return this.totalTimeString.get().compareToIgnoreCase(time.totalTimeString.get());
    }
}
