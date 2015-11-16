package bindingexample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 *
 */
public class TimeUnit {

    private final int max;
    private final IntegerProperty intValue = new SimpleIntegerProperty(0);

    //Add String property
    private final StringProperty value = new SimpleStringProperty();

    public TimeUnit(int value, int max) {
        this.intValue.set(value);
        /*
         Bind string property to integer property 
         format output to have a leading 0 for digit with a size of 1 
         */
        this.value.bind(intValue.asString("%02d"));
        this.max = max;
    }

    public int getValue() {
        return intValue.get();
    }

    public void setValue(int val) {
        intValue.set(val);
    }

    // Return String property

    public StringProperty valueProperty() {
        return value;
    }

    public int getMax() {
        return max;
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
