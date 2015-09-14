/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclock;

/**
 *
 * @author hom
 */
class WeekDay extends TimeElement {

    public WeekDay(int d) {
       this( d,days.length );
    }
    private static String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
        "Sun" };

    WeekDay( int i, int limit ) {
        super(i, limit);
    }

    @Override
    public String toString() {
        return days[ getValue() ];
    }

}
