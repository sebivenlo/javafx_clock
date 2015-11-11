package javafxclock.model;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 */
public class WeekDay extends TimeUnit {

    public  final String[] daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private  final StringProperty dayString = new SimpleStringProperty();

    public String getDayString() {
        return dayString.get();
    }

    public void setDayString(String value) {
        dayString.set(value);
    }

    public StringProperty dayStringProperty() {
        return dayString;
    }

    public WeekDay(int value, int max) {
        super(value, max);
        setDayString(daysOfWeek[value - 1]);
    }

    public WeekDay(int value) {
        super(value, 7);
        setDayString(daysOfWeek[value - 1]);
    }

    @Override
    public String toString() {
        return daysOfWeek[getValue() - 1];
    }

}
