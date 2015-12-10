package javafxclock;

import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class TimeManager {

    private final DayOfWeek dayOfWeek = new DayOfWeek(7).named("weekday");
    private final TimeUnit hour = new TimeUnit(24).named("hour").setNext(
            dayOfWeek);
    private final TimeUnit minute = new TimeUnit(60).named("minute").setNext(
            hour);
    private final TimeUnit second = new TimeUnit(60).named("second").setNext(
            minute);

    /**
     *
     * @param hour
     * @param minute
     * @param second
     * @param dayOfWeek
     */
    public TimeManager(int hour,
            int minute,
            int second,
            int dayOfWeek) {
        this.hour.setValue(hour);
        this.minute.setValue(minute);
        this.second.setValue(second);
        this.dayOfWeek.setValue(dayOfWeek);
    }

    /**
     *
     */
    public TimeManager() {
        this(LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond(),
                LocalDateTime.now().getDayOfWeek().getValue());
    }

    /**
     *
     * @return
     */
    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     *
     * @return
     */
    public StringProperty getDayOfWeekProperty() {
        return dayOfWeek.weekdayProperty();
    }

    /**
     *
     * @return
     */
    public TimeUnit getHour() {
        return hour;
    }

    /**
     *
     * @return
     */
    public StringBinding getHourBinding() {
        return hour.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void hourIncrement(ActionEvent event) {
        hour.increment();
    }

    /**
     *
     * @param event
     */
    public void hourDecrement(ActionEvent event) {
        hour.decrement();
    }

    /**
     *
     * @return
     */
    public TimeUnit getMinute() {
        return minute;
    }

    /**
     *
     * @return
     */
    public StringBinding getMinuteBinding() {
        return minute.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void minuteIncrement(ActionEvent event) {
        minute.increment();
    }

    /**
     *
     * @param event
     */
    public void minuteDecrement(ActionEvent event) {
        minute.decrement();
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
     * @return
     */
    public StringBinding getSecondBinding() {
        return second.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void secondIncrement(ActionEvent event) {
        second.increment();
    }

    /**
     *
     * @param event
     */
    public void secondDecrement(ActionEvent event) {
        second.decrement();
    }

    /**
     *
     * @param event
     */
    public void sync(ActionEvent event) {
        sync();
    }

    /**
     *
     */
    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        hour.setValue(syncTime.getHour());
        minute.setValue(syncTime.getMinute());
        second.setValue(syncTime.getSecond());
        dayOfWeek.setValue(syncTime.getDayOfWeek().getValue() - 1);
    }

    /**
     *
     */
    public void tick() {
        second.increment();
    }

    /**
     *
     * @return
     */
    public String toHhMmString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(
                hour.getValue() < 10
                        ? "0" + hour.getValue()
                        : hour.getValue());
        stringBuilder.append(":");
        stringBuilder.append(
                minute.getValue() < 10
                        ? "0" + minute.getValue()
                        : minute.getValue());

        return stringBuilder.toString();
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

        final TimeManager other = (TimeManager) object;

        return Integer.compare(hour.getValue(), other.hour.getValue()) + Integer.compare(minute.getValue(), other.minute.getValue()) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.dayOfWeek);
        hash = 31 * hash + Objects.hashCode(this.hour);
        hash = 31 * hash + Objects.hashCode(this.minute);
        hash = 31 * hash + Objects.hashCode(this.second);
        return hash;
    }

}
