package javafxclock;

import java.time.LocalDateTime;

/**
 *
 *
 * @author Ron Gebauer <mail@ron.gebauers.org>
 * @version 1
 */
public class Time implements Comparable<Time> {

    private final TimeUnit hourTimeUnit;
    private final TimeUnit minuteTimeUnit;
    private final TimeUnit secondTimeUnit;
    private final Weekday weekdayTimeUnit;
    private final CustomDate customDate;

    /**
     *
     * @param hour
     * @param minute
     * @param second
     * @param weekday
     * @param date
     */
    public Time(int hour, int minute, int second, int weekday, CustomDate date) {
        this.hourTimeUnit = new TimeUnit(hour, 24);
        this.minuteTimeUnit = new TimeUnit(minute, 60);
        this.secondTimeUnit = new TimeUnit(second, 60);
        this.weekdayTimeUnit = new Weekday(weekday, 7);
        this.customDate = date;

        addChangeListeners();
    }

    /**
     *
     */
    public Time() {
        this(LocalDateTime.now().getHour(),
                LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond(),
                LocalDateTime.now().getDayOfWeek().getValue(),
                new CustomDate());
    }

    private void addChangeListeners() {
        getHourTimeUnit().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (getHourTimeUnit().isNext()) {
                getWeekdayTimeUnit().increment();
                getCustomDate().incrementDay();
            } else if (getHourTimeUnit().isPrevious()) {
                getWeekdayTimeUnit().decrement();
                getCustomDate().decrementDay();
            }
        });

        getMinuteTimeUnit().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (getMinuteTimeUnit().isNext()) {
                getHourTimeUnit().increment();
            } else if (getMinuteTimeUnit().isPrevious()) {
                getHourTimeUnit().decrement();
            }
        });

        getSecondTimeUnit().valueProperty().addListener((observable, oldValue, newValue) -> {
            if (getSecondTimeUnit().isNext()) {
                getMinuteTimeUnit().increment();
            } else if (getSecondTimeUnit().isPrevious()) {
                getMinuteTimeUnit().decrement();
            }
        });
    }

    /**
     *
     * @return
     */
    public TimeUnit getHourTimeUnit() {
        return hourTimeUnit;
    }

    /**
     *
     * @return
     */
    public TimeUnit getMinuteTimeUnit() {
        return minuteTimeUnit;
    }

    /**
     *
     * @return
     */
    public TimeUnit getSecondTimeUnit() {
        return secondTimeUnit;
    }

    /**
     *
     * @return
     */
    public Weekday getWeekdayTimeUnit() {
        return weekdayTimeUnit;
    }

    /**
     *
     * @return
     */
    public CustomDate getCustomDate() {
        return customDate;
    }

    /**
     *
     */
    public void sync() {
        LocalDateTime syncTime = LocalDateTime.now();
        getHourTimeUnit().setValue(syncTime.getHour());
        getMinuteTimeUnit().setValue(syncTime.getMinute());
        getSecondTimeUnit().setValue(syncTime.getSecond());
        getWeekdayTimeUnit().setValue(syncTime.getDayOfWeek().getValue());
        getCustomDate().sync();
    }

    /**
     *
     */
    public void tick() {
        getSecondTimeUnit().increment();
    }

    @Override
    public int compareTo(Time time) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(this.getHourTimeUnit().getValue());
        stringBuilder1.append(this.getMinuteTimeUnit().getValue());
        stringBuilder1.append(this.getSecondTimeUnit().getValue());

        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(time.getHourTimeUnit().getValue());
        stringBuilder2.append(time.getMinuteTimeUnit().getValue());
        stringBuilder2.append(time.getSecondTimeUnit().getValue());

        return stringBuilder1.toString().compareToIgnoreCase(stringBuilder2.toString());
    }
}
