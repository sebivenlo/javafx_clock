/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.clock;

import java.time.LocalDateTime;

/**
 *
 * @author max
 */
public class ClockTime implements Comparable<ClockTime>{

    private final TimeElement seconds;
    private final TimeElement minutes;
    private final TimeElement hours;
    private final TimeElement days;

    /**
     * Create a clocktime set to a specific time.
     *
     * @param day
     * @param hour
     * @param minute
     * @param second
     */
   public ClockTime( int day, int hour, int minute, int second ) {
        seconds = new TimeElement( second, 60 );
        minutes = new TimeElement( minute, 60 );
        hours = new TimeElement( hour, 24 );
        minutes.setInferior( seconds );
        hours.setInferior( minutes );
        days = new WeekDay( day ); //Thu 
        seconds.setNext( minutes );
        minutes.setNext( hours );
        hours.setNext( days );
        
    }
        /**
     * Default constructor, set value to to Thursday evening 23:59:55.
     */
    public ClockTime() {
        this( 3, 23, 59, 55 );
    }

    public TimeElement getSeconds() {
        return seconds;
    }

    public TimeElement getMinutes() {
        return minutes;
    }

    public TimeElement getHours() {
        return hours;
    }

    public TimeElement getDays() {
        return days;
    }
        /**
     * Set the clock to time specified as LocalDateTime.
     *
     * @param time
     */
    
    ClockTime( LocalDateTime time ) {
        this( time.getDayOfWeek().getValue() - 1,
                time.getHour(),
                time.getMinute(),
                time.getSecond() );
    }
        public void syncToWallClock() {
        LocalDateTime time = LocalDateTime.now();
        seconds.setValue( time.getSecond() );
        minutes.setValue( time.getMinute() );
        hours.setValue( time.getHour() );
        days.setValue( time.getDayOfWeek().getValue() - 1 );
    }

    /**
     * Increment clock value.
     */
    public void tick() {
        seconds.increment();
    }
        @Override
    public String toString() {
        return days + " " + hours + ":" + minutes + ":" + seconds;
    }


    /**
     * Full comparison of this clock time and the other.
     *
     * @param other clock time to compare.
     * @return the difference as an int
     */
    @Override
    public int compareTo( ClockTime other ) {
        int result = 0;
        
        if ( 0 != ( result = this.days.compareTo( other.days ) ) ) {
            return result;
        }
        if ( 0 != ( result = compareHourAndMinutes( other ) ) ) {
            return result;
        }
        if ( 0 != ( result = this.seconds.compareTo( other.seconds ) ) ) {
            return result;
        }
        return result;
    }

    /**
     * Compare hour an minutes value for e.g. alarm clock usage.
     *
     * @param other clock time to compare.
     * @return The difference as an integer.
     */
    public int compareHourAndMinutes( ClockTime other ) {
        int result = 0;
        if ( 0 != ( result = this.hours.compareTo( other.hours ) ) ) {
            return result;
        }
        if ( 0 != ( result = this.minutes.compareTo( other.minutes ) ) ) {
            return result;
        }
        return result;
    }
    }