package javafxclock.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 */
public class WeekDay extends TimeUnit {

    protected final String[] daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private final StringProperty dayString = new SimpleStringProperty();

    public WeekDay(int value, int max) {
        super(value, max);
        dayString.set(daysOfWeek[value - 1]);
    }

    public WeekDay(int value) {
        super(value, 7);
        dayString.set(daysOfWeek[value - 1]);
    }

    public String getDayString() {
        return dayString.get();
    }

    public void setDayString(String value) {
        dayString.set(value);
    }

    public StringProperty dayStringProperty() {
        return dayString;
    }

    @Override
    public String toString() {
        return daysOfWeek[getValue() - 1];
    }

}
