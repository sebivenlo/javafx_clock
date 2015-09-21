/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.jfxclock.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import textclock.TimeElement;

/**
 * Draws a ClockHand on a graphics. Is supposed to have a parent of type
 * Component. Follows parent size.
 *
 * @author hom
 */
class ClockHand extends ComponentAdapter implements Observer {

    // the design in pixels, width and height.
    public static final int DESIGN_SIZE = 1000;
    private static final int ORIGIN = 500;
    private final int steps; // how may steps per revolution.
    public static final Shape defaultShape
            = new Polygon( new int[]{ 4, 6, 5 + 5, 5, 0 },
            new int[]{ 0, 0, 500, 535, 500 }, 5 );
    //   public static String altPathString="M 4,0 L 6,0 L 10,500 L 5,535 L 0,500";
    public static String altPathString = "M 4,0 L 6,0 L 10,500 L 5,535 L 0,500";
    private TimeElement timeElement;

    /**
     * Create a ClockHand with fixed shape and 60 steps.
     */
    public ClockHand() {
        this( defaultShape, new TimeElement( 60 ) );
    }

    /**
     * Create clock hand completing round in steps steps.
     *
     * @param steps the number of steps to come full circle.
     */
    public ClockHand( TimeElement te ) {
        this( defaultShape, te );
    }

    /**
     * *
     * Create a hand with default shape and steps
     *
     * @param timeElement to watch
     * @param steps per revolution.
     */
    public ClockHand( TimeElement timeElement, int steps ) {
        this( defaultShape, timeElement, steps );
    }

    /**
     * Create a ClockHand with steps equal to TimeElement limit.
     *
     * @param shape for the hand
     * @param te time element to watch.
     */
    public ClockHand( Shape shape, TimeElement te ) {
        this( shape, te, te.getLimit() );
    }

    /**
     * Create a hand with a given shape and number of steps.
     *
     * @param shape to draw
     * @param te time element to watch
     * @param steps per revolution.
     */
    public ClockHand( Shape shape, TimeElement te, int steps ) {
        initialHand = shape;
        this.timeElement = te;
        this.steps = steps;
    }

    /**
     *
     *
     * @param factor to scale
     * @return this clockhand.
     */
    public ClockHand scale( double xFactor, double yFactor ) {
        AffineTransform scale = AffineTransform.getScaleInstance( xFactor,
                yFactor );
        initialHand = scale.createTransformedShape( initialHand );
        return this;
    }

    /**
     * Translate this hands shape inside the coordinate space.
     *
     * @param factor to scale
     * @return this clockhand.
     */
    public ClockHand translate( double xFactor, double yFactor ) {
        AffineTransform scale = AffineTransform.getTranslateInstance( xFactor,
                yFactor );
        initialHand = scale.createTransformedShape( initialHand );
        return this;
    }

    private Component parent;
    private Shape initialHand;
    private Shape hand;
    private int value; // the current rotation
    private Color foreground;

    public Color getForeground() {
        return foreground;
    }

    public void setForeground( Color foreground ) {
        this.foreground = foreground;
    }

    protected void draw( Graphics2D g2 ) {
        g2.setColor( foreground );
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );

        hand = getShape();
        g2.fill( hand );
    }

    @Override
    public void update( Observable o, Object arg ) {
        value = 360 * timeElement.getValue() + ( 360 * timeElement.
                getInferiorValue() ) / timeElement.
                getInferiorLimit();
        invalidate();
    }

    private Rectangle drawBounds;

    private void invalidate() {
        hand = null; // force recalculation.
        hand = getShape();
        if ( null != parent ) {
            if ( drawBounds == null ) {
                drawBounds = parent.getBounds();
            }
            Rectangle r = hand.getBounds();
            //dirty = r.getBounds();
            Rectangle dirty = r.union( drawBounds );
            parent.repaint( dirty.x, dirty.y, dirty.width, dirty.height );
            drawBounds = r;
        }
    }

    private Shape getShape() {
        if ( hand == null ) {
            int displayValue = value / steps;
            double angle = Math.toRadians( displayValue % 360 );
            double centerx = 0;//parent.getWidth()/2;
            double centery = 0;//parent.getHeight()/2;
            double scaleX = ( ( double ) parent.getWidth() ) / DESIGN_SIZE;
            double scaleY = ( ( double ) parent.getHeight() ) / DESIGN_SIZE;
            double scaleM = Math.min( scaleX, scaleY );
            AffineTransform rotate = AffineTransform.getRotateInstance( angle,
                    centerx, centery );
            AffineTransform scale = AffineTransform.
                    getScaleInstance( scaleM, scaleM );
            scale.concatenate( rotate );

            double w = parent.getWidth() / 2;
            double h = parent.getHeight() / 2;
            AffineTransform at = AffineTransform.getTranslateInstance( w, h );
            at.concatenate( scale );
            hand = at.createTransformedShape( initialHand );
        }

        return hand;
    }

    public Component getParent() {
        return parent;
    }

    public void setParent( Component parent ) {
        this.parent = parent;
        parent.addComponentListener( this );
    }

    @Override
    public void componentResized( ComponentEvent e ) {
        invalidate();
    }

    public static class Builder {

        private Shape shape = ClockHand.defaultShape;
        private int steps = 60;
        private TimeElement timeElement;

        ClockHand build() {
            return new ClockHand( shape, timeElement, steps );
        }

        Builder withShape( Shape s ) {
            shape = s;
            return this;
        }

        Builder forTimeElement( TimeElement te ) {
            this.timeElement = te;
            return this;
        }

        Builder withSteps( int steps ) {
            this.steps = steps;
            return this;
        }
    }

    public static Point2D.Double getCenter( Shape s ) {
        Rectangle2D r = s.getBounds2D();
        return new Point2D.Double( r.getCenterX(), r.getCenterY() );
    }

    /**
     * Get the vertical length of the hand, measured from a pivot point.
     *
     * @param hand to measure
     * @param pivot reference point
     * @return the length
     */
    public static double getDesignLength( Shape hand, Point2D.Double pivot ) {
        return Math.abs( hand.getBounds2D().getY() - pivot.y );
    }

    /**
     * Get a hand with pivot from shape map and do the required transformations.
     * The pivot shape is assumed to honor the name convention
     * shapename'-pivot'.
     *
     * @param shapeMap to find shape and pivot definitions.
     * @param shapeName the name of the shape to retrieve.
     * @return the shape
     */
    public static Shape handShapeFormSVGMap( Map<String, Shape> shapeMap,
            String shapeName ) {
        Shape result = shapeMap.get( shapeName );
        Point2D.Double pivot = getCenter( shapeMap.get( shapeName + "-pivot" ) );
        System.out.println( shapeName+ " pivot = " +  pivot );
        double length = getDesignLength( result, pivot );
        System.out.println( shapeName + " length = " + length );
        AffineTransform toPivot = AffineTransform
                .getTranslateInstance( -pivot.x, -pivot.y );
        result = toPivot.createTransformedShape( result );
        return result;
    }
}
