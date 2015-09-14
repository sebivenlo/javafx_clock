/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclockgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import textclock.AlarmClock;
import textclock.TimeElement;
import textclockgui.ArrowButton.Orientation;

/**
 *
 * @author hvd
 */
public class ClockControlPanel extends JPanel {

    private JButton startStopButton;

    public ClockControlPanel( AlarmClock clock ) {

        setBackground( Color.black );
        JButton syn = new JButton( "Sync" );
        ArrowButton hoursUp = new ArrowButton( "H", Orientation.UP, Color.red
                .darker(), Color.red.brighter() );

        ArrowButton minutesUp = new ArrowButton( "M", Orientation.UP, Color.red
                .darker(), Color.red.brighter() );
        minutesUp.addMouseListener( new Incrementor( clock.getTime()
                .getMinutes() ) );
        hoursUp
                .addMouseListener( new Incrementor( clock.getTime().getHours() ) );
        add( hoursUp );
        add( minutesUp );
        URL imgURL = getClass().getResource( "/resources/wheels.gif" );
        final ImageIcon icn = new ImageIcon( imgURL, "running wheels" );
        imgURL = getClass().getResource( "/resources/wheels-0.png" );
        final ImageIcon icnStopped = new ImageIcon( imgURL, "stopped wheels" );
        final Color startColor = Color.green.darker();
        final Color stopColor = Color.red.darker();
        startStopButton = new JButton( "Start/Stop", icn );
        startStopButton.setRolloverIcon( icn );
        icn.setImageObserver( startStopButton );
        startStopButton.setFont( new Font( "Arial", Font.BOLD, 24 ) );
        startStopButton.setForeground( startColor );

        startStopButton.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {

                if ( clock.isRunning() ) {
                    clock.stop();
                    startStopButton.setIcon( icnStopped );
                    startStopButton.setRolloverIcon( icnStopped );
                    startStopButton.setForeground( stopColor );
                } else {
                    clock.start();
                    startStopButton.setIcon( icn );
                    startStopButton.setRolloverIcon( icn );
                    startStopButton.setForeground( startColor );
                }
                startStopButton.repaint();
            }

        } );
        add( startStopButton );

        ArrowButton minutesDown = new ArrowButton( "M", Orientation.DOWN,
                Color.green.darker(), Color.green.brighter() );
        minutesDown.addActionListener( new Decrementor( clock.getTime()
                .getMinutes() ) );
        add( minutesDown );
        ArrowButton hoursDown = new ArrowButton( "H", Orientation.DOWN,
                Color.green.darker(), Color.green.brighter() );
        hoursDown.addActionListener(
                new Decrementor( clock.getTime().getHours() ) );
        add( hoursDown );
        syn.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed( ActionEvent e ) {
                clock.getTime().syncToWallClock();
            }
        } );
        add( syn );
    }

    /**
     * Increments a time element on (button) action.
     */
    private static class Incrementor extends MouseAdapter {

        private final TimeElement element;
        private final Timer repeatTimer;

        public Incrementor( TimeElement element ) {
            this.element = element;
            repeatTimer = new Timer( 120, new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ) {
                    doWork();
                }
            }
            );
        }

        @Override
        public void mousePressed( MouseEvent e ) {
            doWork();
            repeatTimer.start();
        }

        @Override
        public void mouseReleased( MouseEvent e ) {
            repeatTimer.stop(); //To change body of generated methods, choose Tools | Templates.
        }

        private void doWork() {
            element.increment();
        }
    }

    /**
     * Decrements a time element on (button) action.
     */
    private static class Decrementor implements ActionListener {

        private final TimeElement element;

        public Decrementor( TimeElement element ) {
            this.element = element;
            System.out.println( "-" + element );
        }

        @Override
        public void actionPerformed( ActionEvent e ) {
            doWork();
        }

        private void doWork() {
            element.decrement();
        }
    }

    public JButton getStartStopButton() {
        return startStopButton;
    }

}
