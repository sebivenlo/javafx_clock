/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclockgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import javafx.scene.transform.Affine;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This label does NOT paint its border.
 * @author hom
 */
class ReflectingLabel extends JLabel {

    public ReflectingLabel( String text, Icon icon, int horizontalAlignment ) {
        super( text, icon, horizontalAlignment );
    }

    public ReflectingLabel( String text, int horizontalAlignment ) {
        super( text, horizontalAlignment );
    }

    public ReflectingLabel( String text ) {
        super( text );
    }

    public ReflectingLabel( Icon image, int horizontalAlignment ) {
        super( image, horizontalAlignment );
    }

    public ReflectingLabel( Icon image ) {
        super( image );
    }

    public ReflectingLabel() {
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension higher = new Dimension( super.getPreferredSize().width, 3
                * super.getPreferredSize().height / 2 );
        return higher;
    }
    static GradientPaint paint = null;

    @Override
    public void paint( Graphics g ) {
        paintComponent( g );
        paintChildren( g );
        
    }

    
    @Override
    protected void paintComponent( Graphics g ) {
        Graphics2D g2 = ( Graphics2D ) g;
//        Shape triangle = new Polygon( new int[]{ 0, getWidth() / 2, getWidth() },
//                new int[]{
//                    getHeight(), 0, getHeight() }, 3 );
//        g2.setColor( Color.red );
        int width = getWidth();
        int height = getHeight();
        int baseline = getBaseline( width, height );
        int maxAscent = getFontMetrics( getFont() ).getMaxAscent();
        int ascent = getFontMetrics( getFont() ).getAscent();
        int gradientStop = Math.min( height, baseline + 2*ascent/3);
        AffineTransform ct = g2.getTransform();

        //g2.setTransform( ct );
        paint = new GradientPaint( 0, -baseline-1,
                new Color( 128, 128, 128, 255 ),
                0, -gradientStop, new Color( 0, 0, 0, 0 ) );

        AffineTransform at = new AffineTransform( ct );
        at.concatenate( AffineTransform.getScaleInstance( 1, -1 ) );
        AffineTransform bt = AffineTransform.getTranslateInstance( 0,
                -getHeight() );
//        triangle = bt.createTransformedShape( triangle );
        g2.setTransform( at );
        //g2.setColor(Color.cyan);
        g2.setPaint( paint );
  //      g2.fill( triangle );
        g2.drawString( getText(), 0, -baseline +1 );
        g2.setTransform( ct );
        g2.setColor( getForeground() );
        super.paintComponent( g );
    }

//    public static void main( String[] args ) {
//        JFrame f = new JFrame( "Helllo" );
//        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//        ReflectingLabel l = new ReflectingLabel( "Hello World" );
//        l.setFont( new Font( "Arial", Font.BOLD, 48 ) );
//        f.getContentPane().add( l );
//        f.pack();
//        f.setVisible( true );
//    }
}
