package javafx_clock.clock;

import java.util.Observable;

/**
 * Element of Time, like hour or minute. Maintains a value and provides a tick()
 * method to increment (increase by one unit) the value.
 *
 * @author hom
 */
public class TimeElement extends Observable implements Comparable<TimeElement> {

    /**
     * The current value.
     */
    private int value;
    /**
     * The limit on which roll over will occur. The limit itself is never
     * reached. If an increment would lead to this value, the value will instead
     * be set to lower limit (0).
     */
    private final int limit;

    /**
     * Create an element and set the initial value.
     *
     * @param value initial
     * @param limit of this element. The limit is never reached. In fact a
     * modulo limit computation is done internally.
     */
    public TimeElement( int value, int limit ) {
        this.value = value;
        this.limit = limit;
    }

    public TimeElement( int limit ) {
        this( 0, limit );

    }

    /**
     * Increase the value by one unit and return current value.
     *
     * @return the current value.
     */
    public int increment() {
        value = ( value + 1 ) % limit;
        if ( value == 0 && null != next ) {
            next.increment();
        }
        setChanged();
        notifyObservers( );
        return value;
    }

    /**
     * Decrease the value by one unit and return current value.
     *
     * @return the current value.
     */
    public int decrement() {
        value = ( value + limit - 1 ) % limit;
        if ( value == ( limit - 1 ) && null != next ) {
            next.decrement();
        }
        setChanged();
        notifyObservers( this );
        return value;
    }

    /**
     * Get the current value.
     *
     * @return the current value.
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format( "%02d", value );
    }

    /**
     * Set the value, no checks.
     *
     * @param i the new value.
     */
    void setValue( int i ) {
        value = i;
        setChanged();
        notifyObservers( );

    }

    private TimeElement next = null;

    /**
     * Get the value of next
     *
     * @return the value of next
     */
    public TimeElement getNext() {
        return next;
    }

    /**
     * Set the value of next
     *
     * @param next new value of next
     */
    public void setNext( TimeElement next ) {
        this.next = next;
    }

    /**
     * Compare this time element with other. Note that this is a mutable value
     * object, for which hashCode and equals are not well defined. Avoid using
     * this kind of objects as hash keys.
     *
     * @param other time element to compare
     * @return 0 if values are equal, -1 for the other's value being bigger, +1
     * for this value being bigger then other's/
     */
    @Override
    public int compareTo( TimeElement other ) {
        return this.value > other.value ? 1 : ( this.value < other.value ? -1 : 0 );
    }

    /**
     * For simulated analog clocks its provide the value of the next lower
     * element.
     */
    private TimeElement inferior;

    /**
     * Return the value that is never reached.
     *
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Gets the inferior time element.
     *
     * @return the inferior element
     */
    public TimeElement getInferior() {
        return inferior;
    }

    /**
     * Sets the inferior time element.
     *
     * @param inferior element
     */
    public void setInferior( TimeElement inferior ) {
        this.inferior = inferior;
    }

    /**
     * Gets the value of the inferior element.
     *
     * @return the inferior value
     */
    public int getInferiorValue() {
        if ( null == inferior ) {
            return 0;
        }
        return inferior.getValue();
    }

    /**
     * Gets the inferior limit.
     *
     * @return the inferior limit
     */
    public int getInferiorLimit() {
        if ( null == inferior ) {
            return this.limit;
        }
        return inferior.getLimit();
    }
}
