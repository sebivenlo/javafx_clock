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

    private final StringProperty dayOfWeek = new SimpleStringProperty();

    /**
     *
     * @param value
     */
    public DayOfWeek(int value) {
        super(value - 1, DAYS_OF_WEEK_EN.length);
        dayOfWeek.set(DAYS_OF_WEEK_EN[value - 1]);
    }

    @Override
    public DayOfWeek named(String name) {
        super.named(name);
        return this;
    }

    public String getWeekday() {
        return DAYS_OF_WEEK_EN[super.getValue()];
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        dayOfWeek.set(toString());
    }

    public StringProperty weekdayProperty() {
        return dayOfWeek;
    }

    @Override
    public String toString() {
        return DAYS_OF_WEEK_EN[super.getValue()];
    }

    @Override
    public void decrement() {
        super.decrement();
        dayOfWeek.set(toString());
    }

    @Override
    public void increment() {
        super.increment();
        dayOfWeek.set(toString());
    }
}
