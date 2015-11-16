/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclockgui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import textclock.AlarmClock;

/**
 *
 * @author hvd
 */
public class ClockGUI implements Observer {

    private JFrame frame;
    private ClockBanner titlePanel;
    private JPanel centerPanel;
    private ClockDial analogDisplay;
    private ClockDigital digitalDisplay;
    private ClockControlPanel controlPanel;

    private final AlarmClock clock;

    public ClockGUI( AlarmClock clock ) {
        this.clock = clock;
        String lafName = UIManager.getCrossPlatformLookAndFeelClassName();
        System.out.println( "lafName = " + lafName );

        String os = System.getProperty( "os.name" );
        System.out.println( "os = " + os );

        if ( System.getProperty( "os.name" ).equals( "Mac OS X" ) ) {
            System.out.println( "running on apple" );
            try {
                UIManager.setLookAndFeel( UIManager
                        .getCrossPlatformLookAndFeelClassName() );
            } catch ( ClassNotFoundException | InstantiationException |
                    IllegalAccessException | UnsupportedLookAndFeelException e ) {
                System.out.println( 
                        "Cross platform look and feel can't be loaded" );
            }
        }
    }

    /**
     * Create a GUI with JFrame, label and Button. This implementation uses
     * Layout manager.
     */
    public void show() {
        frame = new JFrame( "Clock Graphical user Interface" );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true );

        Container contentPane = frame.getContentPane();
        contentPane.setLayout( new BorderLayout() );
        contentPane.setPreferredSize( new Dimension( 800, 600 ) );
        // force the frame to it's component's size. 
        frame.pack();

//        titlePanel = new ClockBanner();
//        contentPane.add( titlePanel , BorderLayout.NORTH);
        centerPanel = new JPanel( new BorderLayout() );
        contentPane.add( centerPanel );

        analogDisplay = ClockDial.createAnimatedClockDial(clock.getTime() );
        centerPanel.add( analogDisplay, BorderLayout.CENTER );

        digitalDisplay = new ClockDigital( clock.getTime() );
        centerPanel.add( digitalDisplay, BorderLayout.NORTH );

        controlPanel = new ClockControlPanel( clock );
        contentPane.add( controlPanel, BorderLayout.SOUTH );
    }

    @Override
    public void update( Observable o, Object arg ) {
        digitalDisplay.update( o.toString() );
    }

}
