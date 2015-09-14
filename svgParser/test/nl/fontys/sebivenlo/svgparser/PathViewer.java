/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.svgparser;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static nl.fontys.sebivenlo.svgparser.SVGSimplePath.getEnclosingRectangle;

/**
 *
 * @author hom
 */
public class PathViewer {
        public static void main( String[] args ) throws IOException {
        JFrame f = new JFrame( "" );f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        final SVGParser parser = new SVGParser( PathViewer.class
                .getResource(
                        "newhands.svg" ).toString() );
        Map<String, Shape> svgPathMap = parser.getShapeMap();
        Set<String> mkeys = svgPathMap.keySet();
        final Shape[] s = new Shape[ mkeys.size() ];
        int i = 0;
        for ( String n : mkeys ) {
            s[ i++ ] = svgPathMap.get( n );
        }

        final Rectangle box = getEnclosingRectangle( s );
        JLabel l = new JLabel() {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension( ( int ) ( box.getX() + box.getWidth() ),
                        ( int ) ( box.getY() + box.getHeight() ) ); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void paintComponent( Graphics g ) {
                super.paintComponent( g ); //To change body of generated methods, choose Tools | Templates.
                Graphics2D g2 = ( Graphics2D ) g;
                g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON );
                for ( Shape ms : s ) {
                    Rectangle r = ms.getBounds();
                    g2.drawRect( r.x, r.y, r.width, r.height );
                    int mid = r.x + r.width / 2;
                    System.out.println( "mid = " + mid );
                    //g2.drawLine( mid, r.y, mid, r.y + r.height );
                    g2.draw( ms );
                }
            }

        };
        f.getContentPane().add( l );
        f.pack();
        f.setVisible( true );
    }

}
