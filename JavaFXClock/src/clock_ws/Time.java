package clock_ws;

import java.time.LocalDateTime;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 *
 */
public class Time {

    private final ObjectProperty<TimeUnit> hour = new SimpleObjectProperty<TimeUnit>();
    private final ObjectProperty<WeekDay> day = new SimpleObjectProperty<WeekDay>();
    public StringProperty total = new SimpleStringProperty();

    public WeekDay getDay() {
        return day.get();
    }

    public void setDay(WeekDay value) {
        day.set(value);
    }

    public ObjectProperty dayProperty() {
        return day;
    }

    public TimeUnit getHour() {
        return hour.get();
    }

    public void setHour(TimeUnit value) {
        hour.set(value);
    }

    public ObjectProperty hourProperty() {
        return hour;
    }
    private final ObjectProperty<TimeUnit> minute = new SimpleObjectProperty<TimeUnit>();

    public TimeUnit getMinute() {
        return minute.get();
    }

    public void setMinute(TimeUnit value) {
        minute.set(value);
    }

    public ObjectProperty minuteProperty() {
        return minute;
    }
    private final ObjectProperty<TimeUnit> seconds = new SimpleObjectProperty<TimeUnit>();

    public TimeUnit getSeconds() {
        return seconds.get();
    }

    public void setSeconds(TimeUnit value) {
        seconds.set(value);
    }

    public ObjectProperty secondsProperty() {
        return seconds;
    }

    public Time(int h, int m, int s) {
        TimeUnit sec = new TimeUnit(s, 60);
        TimeUnit min = new TimeUnit(m, 60);
        TimeUnit hr = new TimeUnit(h, 24);
        setMinute(min);
        setSeconds(sec);
        setHour(hr);
        bind();
        addChangeListeners();

    }

    public Time() {
        LocalDateTime syncTime = LocalDateTime.now();
        TimeUnit sec = new TimeUnit(syncTime.getSecond(), 60);
        TimeUnit min = new TimeUnit(syncTime.getMinute(), 60);
        TimeUnit hr = new TimeUnit(syncTime.getHour(), 24);

        setMinute(min);
        setSeconds(sec);
        setHour(hr);
        bind();
        addChangeListeners();
    }

    public void bind() {
        total.bind(getHour().valueProperty().asString().concat(":").concat(getMinute().valueProperty().asString()).concat(":").concat(getSeconds().valueProperty().asString()));
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
                }
            }
        ;

        });
        //add listeners for seconds
        getSeconds().valueProperty().addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

                if ((int) newValue >= getSeconds().getMax()) {
                    getMinute().increment();
                    getSeconds().setValue(0);

                }
                if ((int) newValue < 0) {
                    //TODO update min
                    getMinute().decrement();
                }
            }
        ;

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
                }
            }
        ;

    }

    );
    }
    
    public void sync() {
        LocalDateTime ldt = LocalDateTime.now();

    }

    public void tick() {
        getSeconds().increment();

    }

    public void decrement(TimeUnit unit) {
        unit.decrement();

    }

    public void increment(TimeUnit unit) {
        unit.increment();
    }

    @Override
    public String toString() {
        return getHour() + " : " + getMinute() + " : " + getSeconds();
    }
}
