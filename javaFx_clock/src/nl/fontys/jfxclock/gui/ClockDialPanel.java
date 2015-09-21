/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.IOException;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import nl.fontys.sebivenlo.svgparser.SVGParser;
import nl.fontys.sebivenlo.svgparser.SVGSimplePath;

/**
 * Utility class to do the panel clock dial painting.
 *
 * @author hom
 */
public class ClockDialPanel extends JPanel implements Observer {

    //private JLabel[] digits = new JLabel[ 12 ];
    private final String[] digits = new String[ 12 ];
    private final Point[] digitLocations = new Point[ 12 ];
    private final JLabel brandingLabel = new ReflectingLabel( "SEBI Venlo" );
    private Shape body;

    private Shape[] animatedFins;
    private Shape[] fins;
    private Rectangle logoBox;
    private Map<String, Shape> svgPathMap;

    public ClockDialPanel() {

        for ( int i = 1; i < 12; i++ ) {
            digits[ i ] = "" + i;
        }
        digits[ 0 ] = "12";

        for ( int p = 0; p < digitLocations.length; p++ ) {
            digitLocations[ p ] = new Point();
        }
        setLayout( new DialLayout() );
        add( brandingLabel );
        brandingLabel.setForeground( Color.lightGray );
        setBackground( Color.black );
        SVGParser parser;
        try {
            parser = new SVGParser( getClass().getResource(
                    "/resources/fontys-icon-animate.svg" ).toString() );
            Map<String, String> svgPaths = parser.getPaths();
            svgPathMap = SVGSimplePath.getShapeMap( svgPaths );
            body = svgPathMap.get( "body" );
            logoBox = body.getBounds();
            fins = new Shape[ 3 ];
            fins[ 0 ] = svgPathMap.get( "topfin" );
            fins[ 1 ] = svgPathMap.get( "topfin1" );
            fins[ 2 ] = svgPathMap.get( "topfin2" );
            animatedFins = new Shape[ fins.length ];
            animationTimer = new Timer( 200, new ActionListener() {
                int fin = 0;

                @Override
                public void actionPerformed( ActionEvent e ) {
                    animateFin();
                }
            } );
        } catch ( IOException ex ) {
            Logger.getLogger( ClockDialPanel.class.getName() )
                    .log( Level.SEVERE, null, ex );
        }

    }
    private Timer animationTimer;

    private Image image = null;

    private void paintStaticBackground() {
        image = createImage( getWidth(), getHeight() );
        Graphics ig = image.getGraphics();
        super.paintComponent( ig );

        Graphics2D g2 = ( Graphics2D ) ig;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        int d = min( getWidth(), getHeight() ) - 1;
        int x = ( getWidth() - d ) / 2;
        float strokeWidth = 5.0f;
        Point2D center = new Point2D.Float( x + d / 4, d / 4 );
        float radius = getHeight();
        float[] dist = { 0.0f, 0.5f };
        Color[] colors = { Color.darkGray, Color.black };
        RadialGradientPaint rpaint
                = new RadialGradientPaint( center, radius, dist, colors );
        g2.setPaint( rpaint );
        g2.fillOval( x, 0, d, d );
        g2.setPaint( rpaint );
        g2.setStroke( new BasicStroke( strokeWidth ) );
        g2.drawOval( x, ( int ) ( strokeWidth / 2 ), d, d
                - ( int ) strokeWidth );
        g2.setStroke( new BasicStroke( 2.0f ) );
        g2.setColor( Color.white );
        g2.drawOval( x, ( int ) ( strokeWidth / 2 ), d, d
                - ( int ) strokeWidth );
        drawMinutesTicks( g2 );
        drawDigits( g2 );
        g2.drawImage( image, 0, 0, this );
        ig.dispose();

    }

    private void drawMinutesTicks( Graphics2D g2 ) {
        //super.paintBorder( g2d );
        AffineTransform atS = g2.getTransform();
        int bx = getWidth() / 2 - 0;
        int bw = 1;
        int by = getHeight() / 25;
        int bh = by / 2;
        for ( int i = 0; i < 60; i++ ) {
            if ( i % 5 != 0 ) {
                g2.drawLine( bx, by, bx, bh );
            }
            g2.rotate( Math.toRadians( 6 ), getWidth() / 2, getHeight() / 2 );
        }
        g2.setTransform( atS );
    }

    @Override
    protected void paintComponent( Graphics g ) {
        if ( null == image ) {
            paintStaticBackground();
        }
        super.paintComponent( g );
        Graphics2D g2 = ( Graphics2D ) g;
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        g2.drawImage( image, 0, 0, this );
        drawLogo( g2 );
        super.paintChildren( g2 );
    }
    private Shape finalBodyShape;
    private Rectangle finRepaintRegion;
    private int fin;

    private void drawLogo( Graphics2D g2 ) {
        g2.setColor( Color.cyan );
        if ( finalBodyShape == null ) {
            placeLogo();
        }
        Rectangle r = finalBodyShape.getBounds();
        g2.setPaint( new GradientPaint( r.x, r.y, Color.WHITE, r.x + r.width,
                r.y + r.height,
                Color.darkGray ) );

        g2.fill( finalBodyShape );
        Shape animatedFin = animatedFins[ fin ];
        g2.fill( animatedFin );
        g2.setColor( Color.white );
        g2.draw( finalBodyShape );
        g2.draw( animatedFin );
    }

    private void placeLogo() {
        AffineTransform at
                = AffineTransform.getTranslateInstance( ( getWidth()
                        - logoBox.width ) / 2, getHeight() / 4 );

        finalBodyShape = at.createTransformedShape( body );
        for ( int i = 0; i < animatedFins.length; i++ ) {
            animatedFins[ i ] = at.createTransformedShape( fins[ i ] );
        }
        finRepaintRegion = animatedFins[ 0 ].getBounds().union(
                animatedFins[ 2 ].getBounds() );
        //animatedFin = animatedFins[ 0 ];
    }

    public void animateFin() {
        if ( finalBodyShape == null ) {
            placeLogo();
        }
        fin = ( fin + 1 ) % animatedFins.length;
        if ( fin == 0 ) {
            animationTimer.stop();
        }

        repaintFin();
    }

    private void repaintFin() {
        repaint( finRepaintRegion.x, finRepaintRegion.y,
                finRepaintRegion.width, finRepaintRegion.height );
    }

    private void drawDigits( Graphics2D g2 ) {
        Font font = new Font( "Arial", Font.BOLD, getHeight() * 64 / 1000 );
        g2.setFont( font );
        for ( int i = 0; i < digits.length; i++ ) {
            g2.drawString( digits[ i ], digitLocations[ i ].x,
                    digitLocations[ i ].y );
        }
    }

    @Override
    public void invalidate() {
        image = null;
        super.invalidate(); //To change body of generated methods, choose Tools | Templates.
    }

    public void startAnimation() {
        fin = 0;
        animationTimer.start();
        repaintFin();
    }

    protected void placeDigits() {
        Font font = new Font( "Arial", Font.BOLD, getHeight() * 64 / 1000 );
        FontMetrics fm = getFontMetrics( font );
        for ( int i = 0; i < digits.length;
                i++ ) {
            int xOrg = getWidth() / 2;
            int yOrg = getHeight() / 2;
            double radius = 0.9 * min( xOrg, yOrg );
            int width = fm.stringWidth( digits[ i ] );
            int height = fm.getHeight();
            int x = -width / 2 + ( int ) ( xOrg + radius * sin(
                    toRadians( 30 * i ) ) );
            digitLocations[ i ].x = x;
            int y
                    = -height / 2 + ( int ) ( yOrg - radius * cos(
                            toRadians( 30 * i ) ) );
            digitLocations[ i ].y = y + fm.getAscent();
            brandingLabel.setFont( font );
            Dimension dim = brandingLabel.getPreferredSize();
            brandingLabel.setBounds( ( getWidth() - dim.width ) / 2,
                    13 * getHeight() / 20, dim.width, dim.height );
            finalBodyShape = null;
        }
    }

    @Override
    public void update( Observable o, Object arg ) {
        startAnimation();
    }

    private static class DialLayout implements LayoutManager {

        @Override
        public void addLayoutComponent( String name, Component comp ) {
            throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void removeLayoutComponent( Component comp ) {
            throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Dimension preferredLayoutSize( Container parent ) {
            throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Dimension minimumLayoutSize( Container parent ) {
            throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void layoutContainer( Container parent ) {
            if ( parent instanceof ClockDial ) {
                ClockDial dial = ( ClockDial ) parent;
                dial.placeDigits();
            }
        }
    }
}
