package javafxclock;

import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 *
 * @author ron
 */
public class TimeUnit {

    private final int limit;
    private final IntegerProperty value;
    private TimeUnit next = null;
    private String name="TimeUnit";

    /**
     * Create a unit with a preset value and limit.
     *
     * @param value
     * @param limit
     */
    public TimeUnit( int value, int limit ) {
        this.value = new SimpleIntegerProperty( value );
        this.limit = limit;
    }

    /**
     * Create a unit starting at zero with limit.
     * @param limit 
     */
    public TimeUnit( int limit ) {
        this( 0, limit );
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
    public void setValue( int value ) {
        this.value.set( value );
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
        return this.limit;
    }

    /**
     *
     */
    public void increment() {
        if ( ( this.getValue() + 1 ) >= this.getMax() ) {
            if ( null != this.next ) {
                System.out.println( name+"next incr" );
                next.increment();
            }
            this.setValue( 0 );
        } else {
            this.setValue( this.getValue() + 1 );
        }
        System.out.println( name+ " inc done" );
    }

    /**
     *
     */
    public void decrement() {
        if ( ( this.getValue() - 1 ) < 0 ) {
            if ( null != this.next ) {
                System.out.println( name+" next decr" );
                next.decrement();
            }
            this.setValue( this.limit - 1 );
        } else {
            this.setValue( this.getValue() - 1 );
        }
        System.out.println( name+ " dec done" );
    }

    @Override
    public String toString() {
        return String.valueOf( value.asString( "%02d" ) );
    }

    public StringBinding asStringBinding() {
        return value.asString( "%02d" );
    }

    public TimeUnit getNext() {
        return next;
    }

    public TimeUnit setNext( TimeUnit next ) {
        this.next = next;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.limit;
        hash = 67 * hash + Objects.hashCode( this.value );
        return hash;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final TimeUnit other = (TimeUnit) obj;
        if ( this.limit != other.limit ) {
            return false;
        }
        if ( !Objects.equals( this.value, other.value ) ) {
            return false;
        }
        return true;
    }

    public TimeUnit named(String n){
        this.name=n;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }
    
}
