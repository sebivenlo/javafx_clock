package javafxclock;

import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * a date format with string properties
 *
 */
public final class CustomDate extends Date {

    private final IntegerProperty dateDay = new SimpleIntegerProperty(1);

    private StringProperty monthString;
    private String[] daysStrArr = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV", "DEZ"};
    private Integer[] daysIntArr = new Integer[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final IntegerProperty dateMonth = new SimpleIntegerProperty(1);
    private final IntegerProperty dateYear = new SimpleIntegerProperty(1);
    private final StringProperty dateString = new SimpleStringProperty();
    public CustomDate() {
        bind();
        sync();
        
    }

    public int getDateDay() {
        return dateDay.get();
    }

    public void setDateDay(int value) {
        dateDay.set(value);
    }

    public IntegerProperty dateDayProperty() {
        return dateDay;
    }

    public int getDateMonth() {
        return dateMonth.get();
    }

    public void setDateMonth(int value) {
        dateMonth.set(value);
    }

    public IntegerProperty dateMonthProperty() {
        return dateMonth;
    }

    public int getDateYear() {
        return dateYear.get();
    }

    public void setDateYear(int value) {
        dateYear.set(value);
    }

    public IntegerProperty dateYearProperty() {
        return dateYear;
    }

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
        int oldDay = getDateDay();
        setDateDay(oldDay + 1);
    }

    public void decrementDay() {
        int oldDay = getDateDay();
        setDateDay(oldDay - 1);
    }

    public void sync() {
        Calendar c = Calendar.getInstance();
        setDateDay(c.get(Calendar.DAY_OF_MONTH));
        setDateMonth(c.get(Calendar.MONTH));
        setDateYear(c.get(Calendar.YEAR));
        dateString.bind(dateDayProperty().asString().concat(".").concat(monthString).concat(".").concat(dateYearProperty().asString()));

    }

    private void bind() {

        monthString = new SimpleStringProperty("JAN");
        monthString.setValue(daysStrArr[getMonth()]);

    }

    public void addChangeListener() {
        dateDay.addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                int max = daysIntArr[getDateMonth() - 1];
                if ((int) newValue > max) {
                    int oldMonth = getDateMonth();
                    setDateDay(oldMonth + 1);
                    monthString = new SimpleStringProperty(daysStrArr[getMonth()]);

                }

            }

        });
        dateMonth.addListener(new ChangeListener<Object>() {

            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
                if ((int) newValue > 12) {
                    setDateMonth(1);
                    int oldYear = getYear();
                    setYear(oldYear + 1);
                }

                if ((int) newValue < 0) {
                    setDateMonth(0);
                    monthString = new SimpleStringProperty(daysStrArr[getMonth()]);
                }
            }

        });
    }


}
