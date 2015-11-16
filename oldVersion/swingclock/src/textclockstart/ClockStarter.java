/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclockstart;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import textclock.AlarmClock;
import textclockgui.ClockGUI;

/**
 *
 * @author hvd
 */
public class ClockStarter {

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                
                AlarmClock alarmClock = new AlarmClock();
                alarmClock.start();

                ClockGUI clockTextGUI = new ClockGUI(alarmClock);
                alarmClock.addObserver(clockTextGUI);

                clockTextGUI.show();
            }
        });
    }

}
