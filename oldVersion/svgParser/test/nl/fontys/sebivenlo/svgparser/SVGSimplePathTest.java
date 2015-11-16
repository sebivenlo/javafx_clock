/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.svgparser;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import org.junit.Test;
import static org.junit.Assert.*;
import static java.awt.geom.PathIterator.*;
/**
 *
 * @author hom
 */
public class SVGSimplePathTest {
    
    public SVGSimplePathTest() {
    }

    @Test
    public void testParse() {
        String p = "M 12,34 L 34,23 C 12,34 56,78 90,21 Q 23,56 23,76";
        Shape s = SVGSimplePath.getShape( p );
        PathIterator itr = s.getPathIterator( null );
        double [] six = new double[6];
        while(!itr.isDone()) {
            int c = itr.currentSegment( six );
            System.out.print( "c = " + c + " ");
            switch(c) {
                case SEG_CLOSE: System.out.println( "close" ); break;
                case SEG_CUBICTO: System.out.println( "cubic to" ); break;
                case SEG_LINETO: System.out.println( "line to" ); break;
                case SEG_MOVETO: System.out.println( "move to" ); break;
                case SEG_QUADTO: System.out.println( "quad to" ); break;
                    default: System.out.println( "??" ); break;
            }
            itr.next();
        }
    }
    
}
