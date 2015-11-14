package javafxclock.model;

import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * a date format with string properties
 *
 */
public final class CustomDate extends Date {

    private final IntegerProperty dateDay = new SimpleIntegerProperty();

    public int getDateDay() {
        return dateDay.get();
    }

    public void setDateDay(int value) {
        dateDay.set(value);
    }

    public IntegerProperty dateDayProperty() {
        return dateDay;
    }
    private final IntegerProperty dateMonth = new SimpleIntegerProperty();

    public int getDateMonth() {
        return dateMonth.get();
    }

    public void setDateMonth(int value) {
        dateMonth.set(value);
    }

    public IntegerProperty dateMonthProperty() {
        return dateMonth;
    }
    private final IntegerProperty dateYear = new SimpleIntegerProperty();

    public int getDateYear() {
        return dateYear.get();
    }

    public void setDateYear(int value) {
        dateYear.set(value);
    }

    public IntegerProperty dateYearProperty() {
        return dateYear;
    }
    private final StringProperty dateString = new SimpleStringProperty();

    public String getDateString() {
        return dateString.get();
    }

    public void setDateString(String value) {
        dateString.set(value);
    }

    public StringProperty dateStringProperty() {
        return dateString;
    }

    public void incrementDay() {
        int oldDay=getDateDay();
        setDateDay(oldDay+1);
    }
    public void decrementDay() {
        int oldDay=getDateDay();
        setDateDay(oldDay-1);
    }
    public CustomDate() {
        Calendar c = Calendar.getInstance();
        setDateDay(c.get(Calendar.DAY_OF_MONTH));
        setDateMonth(c.get(Calendar.MONTH));
        setDateYear(c.get(Calendar.YEAR));
        dateString.bind(dateDayProperty().asString().concat(".").concat(dateMonthProperty().asString().concat(".").concat(dateYearProperty().asString())));

    }

}
