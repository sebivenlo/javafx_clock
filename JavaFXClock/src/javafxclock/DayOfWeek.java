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

    private static final String[] DAYS_OF_WEEK_EN
            = {
                "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"
            };

    private final StringProperty value = new SimpleStringProperty();

    /**
     *
     * @param value
     */
    public DayOfWeek(int value) {
        super(value - 1,
                DAYS_OF_WEEK_EN.length);
        this.value.set(DAYS_OF_WEEK_EN[value - 1]);
    }

    /**
     *
     * @param name
     *
     * @return
     */
    @Override
    public DayOfWeek named(final String name) {
        super.named(name);
        return this;
    }

    /**
     *
     * @return
     */
    public String getWeekday() {
        return DAYS_OF_WEEK_EN[super.getValue()];
    }

    @Override
    public void setValue(int value) {
        super.setValue(value);
        this.value.set(toString());
    }

    /**
     *
     * @return
     */
    public StringProperty weekdayProperty() {
        return value;
    }

    @Override
    public String toString() {
        return DAYS_OF_WEEK_EN[super.getValue()];
    }

    @Override
    public void decrement() {
        super.decrement();
        value.set(toString());
    }

    @Override
    public void increment() {
        super.increment();
        value.set(toString());
    }
}
