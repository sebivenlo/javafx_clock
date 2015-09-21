/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.clock;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Timer;

/**
 *
 * @author hvd
 */
public class AlarmClock extends Observable implements Observer {

    private final ClockTime time = new ClockTime(LocalDateTime.now());
    private final ClockTime alarmTime = new ClockTime();
    private final Timer timer = new Timer( 1000, time );

    {
        time.addSecondsObserver( this );
    }
    
    /**
     * Update this AlarmClock whenever it is relevant. (Which is every second).
     * Check if alarm time reached.
     * @param o expect a TimeElement.
     * @param arg the optional 
     */
    @Override
    public void update( Observable o, Object arg ) {
        System.out.println( time );
        setChanged();
        notifyObservers();
        if (alarmTime.equals(time)) {
            System.out.println( "Alarm" );
        }
    }

    /**
     * Start the alarm clock.
     *
     */
    public void start() {
        timer.start();
    }

    /**
     * Check if this clock is running.
     *
     * @return running state
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * Stop this clock.
     *
     */
    public void stop() {
        // stopping only if running 
        timer.stop();
    }

    public ClockTime getTime() {
        return time;
    }

    public ClockTime getAlarmTime() {
        return alarmTime;
    }
    
    @Override
    public String toString() {
        return time.toString();
    }
}
