package javafxclock;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class Weekday extends TimeUnit {

    private static final String[] WEEKDAY = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    private final StringProperty weekday = new SimpleStringProperty();

    /**
     *
     * @param value
     * @param max
     */
    public Weekday(int value, int max) {
        super(value, max);
        System.out.println(value);
    }

    public String getWeekday() {
        return WEEKDAY[super.getValue() - 1];
    }

    public void setWeekday(int value) {
        weekday.set(WEEKDAY[value - 1]);
    }

    public StringProperty weekdayProperty() {
        return weekday;
    }

}
