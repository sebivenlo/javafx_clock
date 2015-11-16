package javafxclock;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 * @author ron
 */
public class WeekDay extends TimeUnit {

    /**
     *
     */
    protected final String[] daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    private final StringProperty dayString = new SimpleStringProperty();

    /**
     *
     * @param value
     * @param max
     */
    public WeekDay(int value, int max) {
        super(value, max);
        dayString.set(daysOfWeek[value - 1]);
    }

    /**
     *
     * @param value
     */
    public WeekDay(int value) {
        super(value, 7);
        dayString.set(daysOfWeek[value - 1]);
    }

    /**
     *
     * @return
     */
    public String getDayString() {
        return dayString.get();
    }

    /**
     *
     * @param value
     */
    public void setDayString(String value) {
        dayString.set(value);
    }

    /**
     *
     * @return
     */
    public StringProperty dayStringProperty() {
        return dayString;
    }

    @Override
    public String toString() {
        return daysOfWeek[getValue() - 1];
    }

}
