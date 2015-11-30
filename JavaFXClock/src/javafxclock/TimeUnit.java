package javafxclock;

import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class TimeUnit {

    private final int limit;
    private final IntegerProperty value;

    private TimeUnit next = null;
    private String name = "TimeUnit";

    /**
     *
     * @param value
     * @param limit
     */
    public TimeUnit(int value, int limit) {
        if (value >= limit) {
            throw new ExceptionInInitializerError("value is bigger as max");
        }
        this.value = new SimpleIntegerProperty(value);
        this.limit = limit;
    }

    /**
     *
     * @param limit
     */
    public TimeUnit(int limit) {
        this(0, limit);
    }

    public TimeUnit named(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return value.get();
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
        return value;
    }

    /**
     *
     * @return
     */
    public int getLimit() {
        return limit;
    }

    /**
     *
     */
    public void increment() {
        if ((getValue() + 1) >= getLimit()) {
            if (null != next) {
                next.increment();
            }
            setValue(0);
        } else {
            setValue(getValue() + 1);
        }
    }

    /**
     *
     */
    public void decrement() {
        if ((getValue() - 1) <= 0) {
            if (null != next) {
                next.decrement();
            }
            setValue(getLimit() - 1);
        } else {
            setValue(getValue() - 1);
        }
    }

    @Override
    public String toString() {
        return String.valueOf(value.asString("%02d"));
    }

    public StringBinding asStringBinding() {
        return value.asString("%02d");
    }

    public TimeUnit getNext() {
        return next;
    }

    public TimeUnit setNext(TimeUnit next) {
        this.next = next;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        final TimeUnit other = (TimeUnit) object;
        if (this.limit != other.limit) {
            return false;
        }

        return !Objects.equals(this.value, other.value);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.limit;
        hash = 67 * hash + Objects.hashCode(this.value);
        return hash;
    }

}
