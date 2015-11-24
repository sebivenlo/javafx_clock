package javafxclock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 *
 * @author ron
 */
public class Time {

    //total time string hh:mm:ss
    private final StringProperty totalTimeString = new SimpleStringProperty();
    //alarm time string hh:mm
    private final StringProperty alarmTimeString = new SimpleStringProperty();

    private TimeUnit hour;
    private TimeUnit minute;
    private TimeUnit second;
    private WeekDay weekday;
    private LocalDate date;

    private final StringProperty hourString = new SimpleStringProperty();
    private final StringProperty minuteString = new SimpleStringProperty();
    private final StringProperty secondString = new SimpleStringProperty();
    private final StringProperty weekdayString = new SimpleStringProperty();
    private final StringProperty dateString = new SimpleStringProperty();

    /**
     *
     * @param hour
     * @param minute
     * @param second
     * @param weekday
     * @param date
     */
//    public Time( int hour, int minute, int second, int weekday ) {
//        LocalDateTime x = LocalDateTime.of( date.getDateYear(),hour, minute,second);
//        this.weekday = new WeekDay( weekday );
//        this.hour = new TimeUnit( hour, 24 ).setNext( this.weekday );
//        this.minute = new TimeUnit( minute, 60 ).setNext( this.hour );
//        this.second = new TimeUnit( second, 60 ).setNext( this.minute );
//        this.date = date;
//        binding();
//        //     addChangeListeners();
//    }
    /**
     * Create Time from LocalDateTime.
     *
     * @param syncTime time to start with
     */
    public Time( LocalDateTime syncTime ) {
        //  LocalDateTime syncTime = LocalDateTime.now();

        weekday = new WeekDay( syncTime.getDayOfWeek().getValue() );
        hour = new TimeUnit( syncTime.getHour(), 24 ).setNext( weekday );
        minute = new TimeUnit( syncTime.getMinute(), 60 ).setNext( hour );
        second = new TimeUnit( syncTime.getSecond(), 60 ).setNext( minute );
        date = LocalDate.now();
        dateString.set( date.toString());
        binding();
        //   addChangeListeners();
    }

    public Time() {
        this( LocalDateTime.now() );
    }

    /**
     * Get hour.
     *
     * @return current hour
     */
    public TimeUnit getHour() {
        return hour;
    }

    /**
     * Set the hour.
     *
     * @param hour to set
     */
    public void setHour( TimeUnit hour ) {
        this.hour = hour;
    }

    /**
     * Get the minute.
     *
     * @return the 
     */
    public TimeUnit getMinute() {
        return minute;
    }

    /**
     * Set the Minute.
     *
     * @param minute
     */
    public Time setMinute( TimeUnit minute ) {
        this.minute = minute;
        return this;
    }

    /**
     *
     * @return
     */
    public TimeUnit getSecond() {
        return second;
    }

    /**
     *
     * @param second
     */
    public Time setSecond( TimeUnit second ) {
        this.second = second;
        return this;
    }

    /**
     *
     * @return
     */
    public WeekDay getWeekday() {
        return weekday;
    }

    /**
     *
     * @param weekday
     */
    public Time setWeekday( WeekDay weekday ) {
        this.weekday = weekday;
        return this;

    }

    /**
     *
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     *
     * @param date
     */
    public Time setDate( LocalDate date ) {
        this.date = date;
        dateString.set( date.toString());
        return this;
    }

    /**
     *
     * @return
     */
    public String getTotalTimeString() {
        return totalTimeString.get();
    }

    /**
     *
     * @param value
     */
    public void setTotalTimeString( String value ) {
        totalTimeString.set( value );
    }

    /**
     *
     * @return
     */
    public StringProperty totalTimeStringProperty() {
        return totalTimeString;
    }

    /**
     *
     * @return
     */
    public String getAlarmTimeString() {
        return alarmTimeString.get();
    }

    /**
     *
     * @param value
     */
    public void setAlarmTimeString( String value ) {
        alarmTimeString.set( value );
    }

    /**
     *
     * @return
     */
    public StringProperty alarmTimeStringProperty() {
        return alarmTimeString;
    }

    /**
     *
     * @return
     */
    public String getWeekdayString() {
        return weekdayString.get();
    }

    /**
     *
     * @param value
     */
    public void setWeekdayString( String value ) {
        weekdayString.set( value );
    }

    /**
     *
     * @return
     */
    public StringProperty weekdayStringProperty() {
        return weekdayString;
    }

    /**
     * Bind string properties bind the string properties with or without leading zero
     */
    private void binding() {
        hourString.bind( hour.valueProperty().asString( "%02d" ) );
        minuteString.bind( minute.valueProperty().asString( "%02d" ) );
        secondString.bind( second.valueProperty().asString( "%02d" ) );

        weekdayString.bind( getWeekday().dayStringProperty() );

        totalTimeString.bind(
                hourString.concat( " : " ).concat( minuteString ).concat( " : " ).concat(
                secondString ) );

        alarmTimeString.bind( hourString.concat( " : " ).concat( minuteString ) );
    }

    /**
     *
     */
    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        getHour().setValue( syncTime.getHour() );
        getMinute().setValue( syncTime.getMinute() );
        getSecond().setValue( syncTime.getSecond() );
        getWeekday().setValue( syncTime.getDayOfWeek().getValue() );
        date = LocalDate.now();
    }

    /**
     *
     */
    public void tick() {
        getSecond().increment();
    }

    /**
     *
     * @param timeUnit
     */
    public void decrement( TimeUnit timeUnit ) {
        timeUnit.decrement();

    }

    /**
     *
     * @param timeUnit
     */
    public void increment( TimeUnit timeUnit ) {
        timeUnit.increment();
    }

    @Override
    public String toString() {
        return getDate() + " " + getWeekday() + ", " + getHour() + " : "
                + getMinute() + " : " + getSecond();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode( this.hour );
        hash = 61 * hash + Objects.hashCode( this.minute );
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
        final Time other = (Time) obj;
        if ( !Objects.equals( this.hour, other.hour ) ) {
            return false;
        }
        if ( !Objects.equals( this.minute, other.minute ) ) {
            return false;
        }
        return true;
    }

    public StringProperty dateStringProperty(){
        return dateString;
    }
    
    public StringBinding hourStringBinding(){
        return hour.asStringBinding();
    }
    public StringBinding minStringBinding(){
        return minute.asStringBinding();
    }
    public StringBinding secStringBinding(){
        return second.asStringBinding();
    }
}
