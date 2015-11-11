package javafxclock.model;

import java.time.LocalDateTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 *
 */
public class Time {

    //total time string hh:mm:ss
    public StringProperty total = new SimpleStringProperty();

    private StringProperty secStr = new SimpleStringProperty();
    private StringProperty minStr = new SimpleStringProperty();
    private StringProperty hrStr = new SimpleStringProperty();
    public StringProperty dStr=new SimpleStringProperty();
    public StringProperty dateStr = new SimpleStringProperty();
    
    private TimeUnit minute;
    private TimeUnit second;
    private TimeUnit hour;
    private WeekDay day;

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

    public TimeUnit getHour() {
        return hour;
    }

    public void setHour(TimeUnit hour) {
        this.hour = hour;
    }

    public WeekDay getDay() {
        return day;
    }

    public void setDay(WeekDay day) {
        this.day = day;
    }

    public Time(int h, int m, int s, int d) {

        setMinute(new TimeUnit(m, 60));
        setSecond(new TimeUnit(s, 60));
        setHour(new TimeUnit(h, 24));
        setDay(new WeekDay(d));
        binding();
        addChangeListeners();

    }

    public Time() {
        LocalDateTime syncTime = LocalDateTime.now();

        setMinute(new TimeUnit(syncTime.getMinute(), 60));
        setSecond(new TimeUnit(syncTime.getSecond(), 60));
        setHour(new TimeUnit(syncTime.getHour(), 24));
        setDay(new WeekDay(syncTime.getDayOfWeek().getValue()));
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
         dStr.bind(getDay().dayStringProperty());
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
        //add listener for days
        getDay().valueProperty().addListener(new ChangeListener<Object>(){

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                int x = getDay().valueProperty().getValue();
               getDay().setDayString(getDay().daysOfWeek[x-1]);
                dStr.bind(getDay().dayStringProperty());
            }
            
        });
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

    public void decrement(TimeUnit unit) {
        unit.decrement();

    }

    public void increment(TimeUnit unit) {
        unit.increment();
    }

    @Override
    public String toString() {
        return getHour() + " : " + getMinute() + " : " + getSecond();
    }
}
