/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nl.fontys.jfxclock.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import textclock.AlarmClock;
import textclock.ClockTime;

/**
 *
 * @author hvd
 */
public class ClockDigital extends JPanel {
    
    private final JLabel timeLabel;

    public ClockDigital(ClockTime clock) {
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
         
        timeLabel = new ReflectingLabel("Now 12:34:56");
        timeLabel.setFont( new Font( "Arial", Font.BOLD, 32 ) );
        timeLabel.setText( clock.toString() );
        setBackground( Color.black);
        timeLabel.setForeground( Color.white );
        add( timeLabel );
    }
    
    public void update(String text){
        timeLabel.setText(text);
    }
}
