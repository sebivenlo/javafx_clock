/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.svgparser;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import nl.fontys.sebivenlo.numbercollector.DoubleCollector;

/**
 * Limited svg path to Path2D.Double converter. This implementation has a
 * trivial parser implementation which brings about the following restrictions
 * to the accepted svg file format:
 * <ul><li>Only absolute path coordinates (Capital commands)</li>
 * <li>Commands must be repeated. The svg standard allows continuation of a
 * command when a previous command has consumed the necessary coordinate
 * arguments. For this parser, the commands must be repeated</li>
 * </ul>
 * These limitations can be addresses by preparing or converting the file with
 * e.g. inkscape, by setting the preferences&gt; svg output,&gt; Path data to
 * <b>disallow relative coordinates</b> and to <b>force repeat commands</b>.
 *
 * <p>
 * <b>Limitations:</b>
 * The current version only understands M (move), L (line) H (hor line), V (vert
 * line), Q (Quad Bezièr ) and C (Cubic Bezièr) commands. It closes the
 * paths.</p>
 *
 * @author hom
 */
public class SVGSimplePath {

    public static Shape getShape( String p1 ) {
        return getShape( p1, new Point2D.Double( 0, 0 ) );
    }

    public static Shape getShape( Map<String, String> map ) {
        return getShape( map, new Point2D.Double( 0, 0 ) );
    }

    public static Shape getShape( Map<String, String> map, Point2D.Double trans ) {
        Point2D.Double translate = new Point2D.Double( trans.x, trans.y );
        if ( map.containsKey( "transform" ) ) {
            String tfun = map.get( "transform" );
            String[] transformParts = tfun.split( "\\s*(\\)|,|\\()\\s*" );
            if ( transformParts.length == 3 ) { //translate(num,num)
                translate.x += Double.parseDouble( transformParts[ 1 ] );
                translate.y += Double.parseDouble( transformParts[ 2 ] );
            }
        }
        return getShape( map.get( "d" ), translate );
    }

    /**
     * Convert a svg path string to a Shape. This simple implementation splits
     * the path string and tries to parse it. The returned shape is a
     * GeneralPath, which is closed.
     *
     * @param p1 the paths as string
     * @return a Graphics2D shape.
     */
    public static Shape getShape( String p1, Point2D.Double trans ) {
        Path2D.Double path = new Path2D.Double();
        DoubleCollector collector = new DoubleCollector( p1 );
        // guessed that no more then 4 pairs are needed
        double[] coordinates = new double[ 8 ];
        // current point;
        Point2D.Double current = new Point2D.Double( 0, 0 );
        double x1, y1, xp0, yp0, xp1, yp1;
        while ( collector.hasNext() ) {
            char cmd = collector.lookingAt();
            collector.next();
            collector.skipOverWhiteSpace();
            switch ( cmd ) {
                case 'M':
                    readCoordinates( collector, coordinates, 2 );
                    x1 = coordinates[ 0 ] + trans.x;
                    y1 = coordinates[ 1 ] + trans.y;
                    path.moveTo( x1, y1 );
                    current.x = x1;
                    current.y = y1;
                    break;
                case 'C':
                    readCoordinates( collector, coordinates, 6 );
                    xp0 = coordinates[ 0 ] + trans.x;
                    yp0 = coordinates[ 1 ] + trans.y;
                    xp1 = coordinates[ 2 ] + trans.x;
                    yp1 = coordinates[ 3 ] + trans.y;
                    x1 = coordinates[ 4 ] + trans.x;
                    y1 = coordinates[ 5 ] + trans.y;
                    path.curveTo( xp0, yp0, xp1, yp1, x1, y1 );
                    current.x = x1;
                    current.y = y1;
                    break;
                case 'L':
                    readCoordinates( collector, coordinates, 2 );
                    x1 = coordinates[ 0 ] + trans.x;
                    y1 = coordinates[ 1 ] + trans.y;
                    path.lineTo( x1, y1 );
                    current.x = x1;
                    current.y = y1;
                    break;
                case 'H':
                    readCoordinates( collector, coordinates, 1 );

                    x1 = coordinates[ 0 ] + trans.x;
                    path.lineTo( x1, current.y );

                    current.x = x1;
                    break;
                case 'V':
                    y1 = coordinates[ 0 ] + trans.y;
                    path.lineTo( current.x, y1 );

                    current.y = y1;
                    break;
                case 'Q':
                    readCoordinates( collector, coordinates, 2 );
                    xp0 = coordinates[ 0 ] + trans.x;
                    yp0 = coordinates[ 1 ] + trans.y;
                    x1 = coordinates[ 2 ] + trans.x;
                    y1 = coordinates[ 3 ] + trans.y;
                    path.quadTo( xp0, yp0, x1, y1 );
                    current.x = coordinates[ 2 ];
                    current.y = coordinates[ 3 ];

                case 'Z':
                case 'z':
                    path.closePath();
                    break;
                default:
                    System.out.println( "command cmd = " + cmd
                            + " not supported in parser" );
                    break;
            }
        }
        path.closePath();
        return path;
    }

    /**
     * Reflect a point around another point. The reflection is around a point is
     * around + negative displacement from around to (x,y).
     */
    private Point2D.Double reflect( Point2D.Double around, double x, double y ) {
        return new Point2D.Double( around.x - ( x - around.x ), around.y - ( y
                - around.y ) );
    }

    private static class ArrayIterator<T> implements Iterator<T> {

        final T[] a;
        private int index = 0;

        public ArrayIterator( T[] a ) {
            this.a = a;
        }

        boolean isEmpty() {
            return index >= a.length;
        }

        @Override
        public boolean hasNext() {
            return index < a.length;

        }

        @Override
        public T next() {
            return a[ index++ ];
        }
    }

    private static void readCoordinates( DoubleCollector collector,
            double[] coordinates, int count ) {
        for ( int c = 0; c < count; c++ ) {
            coordinates[ c ] = collector.nextDouble();
            if ( Character.isWhitespace( collector.lookingAt() ) || ','
                    == collector.lookingAt() ) {
                collector.next();
            }
        }
    }

    private static void readCoordinatePairs( ArrayIterator<String> list,
            double[] coordinates, int count ) throws NumberFormatException {
        String[] pair;
        for ( int c = 0; c < count; c++ ) {
            pair = list.next().split( "," );
            coordinates[ 2 * c + 0 ] = Double.parseDouble( pair[ 0 ] );
            coordinates[ 2 * c + 1 ] = Double.parseDouble( pair[ 1 ] );
        }
    }

    public static Shape getShape( String... p ) {
        Path2D.Double path = ( Path2D.Double ) getShape( p[ 0 ] );
        for ( int i = 1; i < p.length; i++ ) {
            path.append( getShape( p[ i ] ), false );
        }
        return path;
    }

    public static Shape getShapeCombined( Map<String, String> map,
            boolean connect ) {
        Path2D.Double path = null;
        Iterator<String> itr = map.values().iterator();
        if ( itr.hasNext() ) {
            path = ( Path2D.Double ) getShape( itr.next() );
            while ( itr.hasNext() ) {
                path.append( getShape( itr.next() ), connect );
            }
        }
        return path;
    }

    public static Map<String, Shape> getShapeMap( Map<String, String> map ) {
        Map<String, Shape> result = new HashMap<>();
        for ( String key : map.keySet() ) {
            result.put( key, getShape( map.get( key ) ) );
        }
        return result;
    }

    public static Rectangle getEnclosingRectangle( Shape... s ) {
        Rectangle result = s[ 0 ].getBounds();
        for ( int i = 1; i < s.length; i++ ) {
            result = result.union( s[ i ].getBounds() );
        }
        return result;
    }

}
