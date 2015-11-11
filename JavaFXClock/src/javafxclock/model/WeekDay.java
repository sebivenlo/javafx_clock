package javafxclock.model;

import javafx.animation.Timeline;

/**
 *
 *
 */
public class WeekDay extends TimeUnit {

    private String[] daysOfWeek;

    
    public WeekDay(int value, int max) {
        super(value, max);
        this.daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    }

    public WeekDay(int value) {
        super(value, 7);
        this.daysOfWeek = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    }

    @Override
    public String toString() {
        return daysOfWeek[getValue() - 1];
    }

}
