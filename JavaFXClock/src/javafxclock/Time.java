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
public class Time
{

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
    public Time(int hour,
                int minute,
                int second,
                int dayOfWeek)
    {
        this.hour.setValue(hour);
        this.minute.setValue(minute);
        this.second.setValue(second);
        this.dayOfWeek.setValue(dayOfWeek);
    }

    /**
     *
     */
    public Time()
    {
        this(LocalDateTime.now().getHour(),
             LocalDateTime.now().getMinute(),
             LocalDateTime.now().getSecond(),
             LocalDateTime.now().getDayOfWeek().getValue());
    }

    /**
     *
     * @return
     */
    public DayOfWeek dayOfWeek()
    {
        return dayOfWeek;
    }

    /**
     *
     * @return
     */
    public StringProperty getDayOfWeek()
    {
        return dayOfWeek.weekdayProperty();
    }

    /**
     *
     * @return
     */
    public TimeUnit hour()
    {
        return hour;
    }

    /**
     *
     * @return
     */
    public StringBinding getHour()
    {
        return hour.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void hourIncrement(ActionEvent event)
    {
        hour.increment();
    }

    /**
     *
     * @param event
     */
    public void hourDecrement(ActionEvent event)
    {
        hour.decrement();
    }

    /**
     *
     * @return
     */
    public TimeUnit minute()
    {
        return minute;
    }

    /**
     *
     * @return
     */
    public StringBinding getMinute()
    {
        return minute.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void minuteIncrement(ActionEvent event)
    {
        minute.increment();
    }

    /**
     *
     * @param event
     */
    public void minuteDecrement(ActionEvent event)
    {
        minute.decrement();
    }

    /**
     *
     * @return
     */
    public TimeUnit second()
    {
        return second;
    }

    /**
     *
     * @return
     */
    public StringBinding getSecond()
    {
        return second.asStringBinding();
    }

    /**
     *
     * @param event
     */
    public void secondIncrement(ActionEvent event)
    {
        second.increment();
    }

    /**
     *
     * @param event
     */
    public void secondDecrement(ActionEvent event)
    {
        second.decrement();
    }

    /**
     *
     * @param event
     */
    public void sync(ActionEvent event)
    {
        sync();
    }

    /**
     *
     */
    public void sync()
    {
        LocalDateTime syncTime = LocalDateTime.now();
        hour.setValue(syncTime.getHour());
        minute.setValue(syncTime.getMinute());
        second.setValue(syncTime.getSecond());
        dayOfWeek.setValue(syncTime.getDayOfWeek().getValue() - 1);
    }

    /**
     *
     */
    public void tick()
    {
        second.increment();
    }

    public String toHhMmString()
    {
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
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (object == null)
        {
            return false;
        }

        if (getClass() != object.getClass())
        {
            return false;
        }

        final Time other = (Time) object;
        if (Integer.compare(hour.getValue(), other.hour.getValue()) == 0)
        {
            if (Integer.compare(minute.getValue(), other.minute.getValue()) == 0)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(hour);
        hash = 97 * hash + Objects.hashCode(minute);
        hash = 97 * hash + Objects.hashCode(second);
        hash = 97 * hash + Objects.hashCode(dayOfWeek);
        return hash;
    }
}
