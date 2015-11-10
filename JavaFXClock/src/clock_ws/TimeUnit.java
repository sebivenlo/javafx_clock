package clock_ws;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 */
public class TimeUnit {

    private int max;
    private final IntegerProperty value = new SimpleIntegerProperty(0);

    public int getValue() {
        return value.get();
    }

    public void setValue(int val) {
        value.set(val);
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    public TimeUnit(int value, int max) {
        setValue(value);
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void increment() {
        int old = getValue();
        setValue(old + 1);

    }

    public void decrement() {
        setValue(getValue() - 1);
    }

    @Override
    public String toString() {
        if (getValue() < 10) {
            return "0" + String.valueOf(getValue());
        }
        return String.valueOf(getValue());

    }

}
