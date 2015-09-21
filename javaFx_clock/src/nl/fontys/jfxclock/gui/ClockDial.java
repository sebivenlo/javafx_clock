/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.fontys.sebivenlo.svgparser.SVGParser;
import nl.fontys.sebivenlo.svgparser.SVGSimplePath;

import textclock.ClockTime;

/**
 * ClockDial if an analog clock face. The background is formed by int x =
 * (int)(xOrg + radius*Math.sin( Math.toRadians( 30 * i ) )); the dial with
 * numbers, hands are placed in front of it.
 *
 * @author hom
 */
public class ClockDial extends ClockDialPanel {

    private ClockHand[] hands;
    private final ClockTime time;
    private ClockDial( ClockTime time ) {
        this.time=time;
        URL url = getClass().getResource( "/resources/newhands.svg" );
        System.out.println( "url = " + url + " ext " + url.toExternalForm()
                + " url tostring " + url.toString() );
        Map<String, Shape> shapeMap = null;
        try {
            shapeMap = new SVGParser( url.toString() ).getShapeMap();
        } catch ( IOException ex ) {
            Logger.getLogger( ClockDial.class.getName() ).log( Level.SEVERE,
                    null, ex );
        }
        Shape hs = SVGSimplePath.getShape( ClockHand.altPathString );
        Shape secondsHand = ClockHand.handShapeFormSVGMap( shapeMap,
                "secondshand" );
        Shape minutesHand = ClockHand.handShapeFormSVGMap( shapeMap, "minutes" );
        Shape hoursHand = ClockHand.handShapeFormSVGMap( shapeMap, "hours" );
        if ( null == secondsHand ) {
            secondsHand = hs;
        }
        ClockHand.Builder builder = new ClockHand.Builder();

        ClockHand hourHand = builder.withShape( hoursHand )
                .forTimeElement( time.getHours() )
                .withSteps( 12 )
                .build();
        ClockHand minuteHand = builder.withShape( minutesHand )
                .forTimeElement( time.getMinutes() )
                .withSteps( 60 )
                .build();
        ClockHand secondHand = builder.withShape( secondsHand )
                .forTimeElement( time.getSeconds() )
                .build().scale( 0.95, 0.95 );
        hourHand.setForeground( Color.LIGHT_GRAY );
        minuteHand.setForeground( Color.red );
        secondHand.setForeground( Color.blue );

        addHands( hourHand, minuteHand, secondHand );

        setPreferredSize( new Dimension( 400, 400 ) );

        Observable o = time.getSeconds();
        o.addObserver( secondHand );
        time.getMinutes().addObserver( minuteHand );
        time.getMinutes().addObserver( hourHand );
        time.getHours().addObserver( hourHand );

        // Make sure that actual time is displayed
        hourHand.update( time.getHours(), null );
        minuteHand.update( time.getMinutes(), null );
        secondHand.update( time.getSeconds(), null );

    }
    static ClockDial createAnimatedClockDial(ClockTime t ){
        ClockDial result = new ClockDial(t);
        t.getSeconds().addObserver( result);
        return result;
    }

    /**
     * Paint this component. Normally your would override paintComponent, but
     * since we want the hands on top of the child components, this method lets
     * super do its work and after super.paint's completion, the hands are drawn
     * on top.
     *
     * @param g context to draw on.
     */
    @Override
    public void paint( Graphics g ) {
        Graphics2D g2 = ( Graphics2D ) g;
        super.paint( g );
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        if ( hands != null ) {
            for ( ClockHand h : this.hands ) {
                h.draw( g2 );
            }
        }
        g2.setColor( Color.white );
        // paint small disc in the center on top of the pivot point of the hands.
        int d2 = 3;
        int cx = getWidth() / 2 - d2;
        int cy = getHeight() / 2 - d2;
        int w = d2 + d2;
        g2.fillOval( cx, cy, w, w );
    }

    final ClockDial addHands( ClockHand... hands ) {
        this.hands = hands;
        for ( ClockHand h : this.hands ) {
            h.setParent( this );
        }
        return this;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension( 400, 400 );
    }
}
