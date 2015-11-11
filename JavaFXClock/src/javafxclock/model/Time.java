package javafxclock.model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 *
 */
public class Time {

    //total time string DAY:hh:mm:ss
    public StringProperty total = new SimpleStringProperty();

    private StringProperty secStr = new SimpleStringProperty();
    private StringProperty minStr = new SimpleStringProperty();
    private StringProperty hrStr = new SimpleStringProperty();

    private clock_ws.TimeUnit minute;
    private clock_ws.TimeUnit second;
    private clock_ws.TimeUnit hour;
    private clock_ws.WeekDay day;

    public clock_ws.TimeUnit getMinute() {
        return minute;
    }

    public void setMinute(clock_ws.TimeUnit minute) {
        this.minute = minute;
    }

    public clock_ws.TimeUnit getSecond() {
        return second;
    }

    public void setSecond(clock_ws.TimeUnit second) {
        this.second = second;
    }

    public clock_ws.TimeUnit getHour() {
        return hour;
    }

    public void setHour(clock_ws.TimeUnit hour) {
        this.hour = hour;
    }

    public clock_ws.WeekDay getDay() {
        return day;
    }

    public void setDay(clock_ws.WeekDay day) {
        this.day = day;
    }

    public Time(int h, int m, int s, int d) {

        setMinute(new clock_ws.TimeUnit(m, 60));
        setSecond(new clock_ws.TimeUnit(s, 60));
        setHour(new clock_ws.TimeUnit(h, 24));
        setDay(new clock_ws.WeekDay(d));
        binding();
        addChangeListeners();

    }

    public Time() {
        LocalDateTime syncTime = LocalDateTime.now();

        setMinute(new clock_ws.TimeUnit(syncTime.getMinute(), 60));
        setSecond(new clock_ws.TimeUnit(syncTime.getSecond(), 60));
        setHour(new clock_ws.TimeUnit(syncTime.getHour(), 24));
        setDay(new clock_ws.WeekDay(syncTime.getDayOfWeek().getValue()));
        binding();
        addChangeListeners();
    }

    /**
     * bind the string properties
     * with or without leading zero
     */
    public void binding() {
        if (getSecond().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            secStr.bind(leading.concat(getSecond().valueProperty().asString()));
        } else {
            secStr.bind(getSecond().valueProperty().asString());

        }
        if (getMinute().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            minStr.bind(leading.concat(getMinute().valueProperty().asString()));
        } else {
            minStr.bind(getMinute().valueProperty().asString());
        }
        if (getHour().valueProperty().getValue() < 10) {
            StringProperty leading = new SimpleStringProperty("0");
            hrStr.bind(leading.concat(getHour().valueProperty().asString()));
        } else {
            hrStr.bind(getHour().valueProperty().asString());
        }
        total.bind(hrStr.concat(" : ").concat(minStr).concat(" : ").concat(secStr));
    }

    public void addChangeListeners() {
        //add Listeners to minutes
        getMinute().valueProperty().addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if ((int) newValue >= getMinute().getMax()) {
                    getHour().increment();
                    getMinute().setValue(0);
                }
                if ((int) newValue < 0) {

                    getHour().decrement();
                    getMinute().setValue(getMinute().getMax() - 1);
                }
                binding();

            };

        });
        //add listeners for seconds
        getSecond().valueProperty().addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

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
            };

        });
     
        //add listeners for hours
        getHour().valueProperty().addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if ((int) newValue >= getHour().getMax()) {
                    getDay().increment();
                    getHour().setValue(0);
                }
                if ((int) newValue < 0) {
                    //TODO update days
                    getHour().setValue(getHour().getMax() - 1);
                    getDay().decrement();
                }
                binding();
            };

    }

    );
    }

    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        getHour().setValue(syncTime.getHour());
        getMinute().setValue(syncTime.getMinute());
        getSecond().setValue(syncTime.getSecond());
        getDay().setValue(syncTime.getDayOfWeek().getValue());
    }

    public void tick() {
        getSecond().increment();
    }

    public void decrement(clock_ws.TimeUnit unit) {
        unit.decrement();

    }

    public void increment(clock_ws.TimeUnit unit) {
        unit.increment();
    }

    @Override
    public String toString() {
        return getHour() + " : " + getMinute() + " : " + getSecond();
    }
}
