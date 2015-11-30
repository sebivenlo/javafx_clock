package javafxclock;

import java.time.LocalDateTime;
import java.util.Objects;
import javafx.beans.binding.StringBinding;
import javafx.event.ActionEvent;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class Time {

    private final DayOfWeek dayOfWeek = new DayOfWeek(7);
    private final TimeUnit hour = new TimeUnit(24).named("hour").setNext(dayOfWeek);
    private final TimeUnit minute = new TimeUnit(60).named("minute").setNext(hour);
    private final TimeUnit second = new TimeUnit(60).named("second").setNext(minute);

    /**
     *
     * @param hour
     * @param minute
     * @param second
     * @param dayOfWeek
     */
    public Time(int hour, int minute, int second, int dayOfWeek) {
        this.hour.setValue(hour);
        this.minute.setValue(minute);
        this.second.setValue(second);
        this.dayOfWeek.setValue(dayOfWeek);
    }

    /**
     *
     */
    public Time() {
        this(LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond(),
                LocalDateTime.now().getDayOfWeek().getValue());
    }

    /**
     *
     * @return
     */
    public StringBinding getWeekday() {
        return dayOfWeek.asStringBinding();
    }

    /**
     *
     * @return
     */
    public StringBinding getHour() {
        return hour.asStringBinding();
    }

    public void hourIncrement(ActionEvent event) {
        hour.increment();
    }

    public void hourDecrement(ActionEvent event) {
        hour.decrement();
    }

    /**
     *
     * @return
     */
    public StringBinding getMinute() {
        return minute.asStringBinding();
    }

    public void minuteIncrement(ActionEvent event) {
        minute.increment();
    }

    public void minuteDecrement(ActionEvent event) {
        minute.decrement();
    }

    /**
     *
     * @return
     */
    public StringBinding getSecond() {
        return second.asStringBinding();
    }

    public void secondIncrement(ActionEvent event) {
        second.increment();
    }

    public void secondDecrement(ActionEvent event) {
        second.decrement();
    }

    /**
     *
     * @param event
     */
    public void sync(ActionEvent event) {
        LocalDateTime syncTime = LocalDateTime.now();
        hour.setValue(syncTime.getHour());
        minute.setValue(syncTime.getMinute());
        second.setValue(syncTime.getSecond());
        dayOfWeek.setValue(syncTime.getDayOfWeek().getValue());
    }

    /**
     *
     */
    public void tick() {
        second.increment();
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

        final Time other = (Time) object;
        if (hour.getValue() == other.hour.getValue()) {
            if (minute.getValue() == other.minute.getValue()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(hour);
        hash = 97 * hash + Objects.hashCode(minute);
        hash = 97 * hash + Objects.hashCode(second);
        hash = 97 * hash + Objects.hashCode(dayOfWeek);
        return hash;
    }
}
