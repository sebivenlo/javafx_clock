/*
 * Created in the module cmod in 2015
 */
package nl.fontys.jfxclock;

import nl.fontys.jfxclock.clock.AlarmClock;
import nl.fontys.jfxclock.gui.ClockGUI;

/**
 * @author Maximilian Walter, Ron Gebauer
 * @version 1.0
 */
public class ClockStarter {

    public static void main(String[] args) {
        AlarmClock alarmClock = new AlarmClock();
        alarmClock.start();

        ClockGUI clockGUI = new ClockGUI(alarmClock);
        alarmClock.addObserver(clockGUI);

        clockGUI.show();
    }

}
