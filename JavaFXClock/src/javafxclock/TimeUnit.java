package javafxclock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 *
 * @author ron
 */
public class TimeUnit {

    private final int max;
    private final IntegerProperty value = new SimpleIntegerProperty(0);
    private boolean next;

    /**
     *
     * @param value
     * @param max
     */
    public TimeUnit(int value, int max) {
        this.value.set(value);
        this.max = max;
        this.next = false;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return this.value.get();
    }

    /**
     *
     * @param value
     */
    public void setValue(int value) {
        this.value.set(value);
    }

    /**
     *
     * @return
     */
    public IntegerProperty valueProperty() {
        return this.value;
    }

    /**
     *
     * @return
     */
    public int getMax() {
        return this.max;
    }

    /**
     *
     */
    public void increment() {
        if ((this.getValue()+ 1) > this.getMax()) {
            this.setValue(0);
            this.next = true;
        } else {
            this.setValue(this.getValue() + 1);
        }
    }

    /**
     *
     */
    public void decrement() {
        if ((this.getValue() - 1) < 0) {
            this.setValue(0);
            this.next = false;
        } else {
            this.setValue(this.getValue() - 1);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(valueProperty().asString("%02d"));
    }

}