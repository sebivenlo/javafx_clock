package javafxclock;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class TimeUnit {

    private final int max;
    private final IntegerProperty value;
    private boolean previous, next;

    /**
     *
     * @param value
     * @param max
     */
    public TimeUnit(int value, int max) {
        if (value >= max) {
            throw new ExceptionInInitializerError("value is bigger as max");
        }
        this.value = new SimpleIntegerProperty(value);
        this.max = max;
        this.previous = false;
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
     * @return
     */
    public boolean isPrevious() {
        return previous;
    }

    /**
     *
     * @return
     */
    public boolean isNext() {
        return next;
    }

    /**
     *
     */
    public void increment() {
        if ((this.getValue() + 1) >= this.getMax()) {
            this.setValue(0);
            this.previous = false;
            this.next = true;
        } else {
            this.setValue(this.getValue() + 1);
        }
    }

    /**
     *
     */
    public void decrement() {
        if ((this.getValue() - 1) <= 0) {
            this.setValue(this.getMax() - 1);
            this.previous = true;
            this.next = false;
        } else {
            this.setValue(this.getValue() - 1);
        }
    }
}
