/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclockgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author hvd
 */
public class ArrowButton extends JButton {
    
    public enum Orientation {
        
        UP, DOWN
    }
    private final Orientation orientation;
    private Color currentColor;
    private Shape polygon = null;
    
    public ArrowButton( String text, Orientation orientation, Color color,
            Color hoverColor ) {
        super( text );
        currentColor = color;
        //setBackground( Color.darkGray);
        this.orientation = orientation;
        //setPreferredSize( new Dimension( 50, 40 ) );
        addMouseListener( new MouseAdapter() {
            
            @Override
            public void mouseEntered( MouseEvent e ) {
                System.out.println( "Entered" );
                currentColor = hoverColor;
                repaint();
            }
            
            @Override
            public void mouseExited( MouseEvent e ) {
                System.out.println( "exited" );
                currentColor = color;
                repaint();
            }
            
        } );
    }
    
    private Shape computeShape( Orientation orientation ) {
        Shape result;
        int w = getWidth();
        int h = getHeight();
        
        switch ( orientation ) {
            case UP:
                result = new Polygon( new int[]{ w / 10, w / 2, 9 * w / 10 },
                        new int[]{ 8 * h / 10, h / 10, 8 * h / 10 }, 3 );
                break;
            case DOWN:
            default:
                result = new Polygon( new int[]{ w / 10, w / 2, 9 * w / 10 },
                        new int[]{ 2 * h / 10, 9 * h / 10, 2 * h / 10 }, 3 );
        }
        return result;
    }
    
    @Override
    public void paintComponent( Graphics g ) {
        
        super.paintComponent( g );
        //this.setContentAreaFilled(false);
        Graphics2D g2d = ( Graphics2D ) g;
        g2d.setColor( currentColor );
        if ( polygon == null ) {
            polygon = computeShape( this.orientation );
        }
        GradientPaint paint = new GradientPaint( 0, 0, currentColor.brighter()
                .brighter(), 0, getHeight(), currentColor.darker().darker() );
        g2d.setPaint( paint );
        g2d.fill( polygon );
        
        g2d.setColor( currentColor.darker() );
        g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
        
        g2d.draw( polygon );
        g2d.setColor( Color.black );
        int x = ( getWidth() - getFontMetrics( getFont() ).stringWidth(
                getText() ) ) / 2;
        g2d.drawString( getText(), x, getBaseline( getWidth(), getHeight() ) );
    }
    
}
