package javafxclock;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class DayOfWeek extends TimeUnit {

    private static final String[] DAYS_OF_WEEK_EN = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};

    private final StringProperty dayOfWeekAsString = new SimpleStringProperty();

    /**
     *
     * @param value
     * @param limit
     */
    public DayOfWeek(int value, int limit) {
        super(value, limit);
        dayOfWeekAsString.set(DAYS_OF_WEEK_EN[value - 1]);
    }

    /**
     *
     * @param value
     */
    public DayOfWeek(int value) {
        super(value, DAYS_OF_WEEK_EN.length);
        dayOfWeekAsString.set(DAYS_OF_WEEK_EN[value - 1]);
    }

    @Override
    public DayOfWeek named(String name) {
        super.named(name);
        return this;
    }

    public String getWeekday() {
        return DAYS_OF_WEEK_EN[super.getValue() - 1];
    }

    public void setWeekday(int value) {
        dayOfWeekAsString.set(DAYS_OF_WEEK_EN[value - 1]);
    }

    public StringProperty weekdayProperty() {
        return dayOfWeekAsString;
    }

    @Override
    public String toString() {
        return DAYS_OF_WEEK_EN[getValue()];
    }

    @Override
    public void decrement() {
        super.decrement();
        dayOfWeekAsString.set(toString());
    }

    @Override
    public void increment() {
        super.increment();
        dayOfWeekAsString.set(toString());
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        dayOfWeekAsString.set(toString());
    }

}
