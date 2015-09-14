/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.numbercollector;

import java.text.StringCharacterIterator;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author hom
 */
public class DoubleCollectorTest {

    public DoubleCollectorTest() {
    }

    DoubleCollector collector;

    @Before
    public void setUp() {
        String source = "M123.45.56L 123T.987Q-12.34,+6745 L1E-34";
        collector = new DoubleCollector( new StringCharacterIterator( source ) );
    }

    @Test
    public void getDouble1() {
        collector.next();
        double d = collector.nextDouble();
        assertEquals( "123.45", 123.45, d, 0.001 );
        assertFalse( collector.isIntegral() );
        System.out.println( "found d = " + d );

        d = collector.nextDouble();
        assertEquals( "0.56", 0.56, d, 0.001 );
        assertFalse( collector.isIntegral() );
        System.out.println( "found d = " + d );

        collector.skipOverWhiteSpace();
        assertEquals( 'L', collector.lookingAt() );
        collector.next();
        collector.skipOverWhiteSpace();
        d = collector.nextDouble();
        assertEquals( "123", 123, d, 0.001 );
        assertTrue( collector.isIntegral() );
        assertEquals( "123i", 123, collector.getAsInt() );

        System.out.println( "found d = " + d );

        assertEquals( 'T', collector.lookingAt() );
        collector.next();
        d = collector.nextDouble();
        assertEquals( ".987", 0.987, d, 0.001 );
        assertFalse( collector.isIntegral() );
        System.out.println( "found d = " + d );

        assertEquals( 'Q', collector.lookingAt() );
        collector.next();
        d = collector.nextDouble();
        assertEquals( "-12.34", -12.34, d, 0.001 );
        assertFalse( collector.isIntegral() );
        System.out.println( "found d = " + d );

        assertEquals( ',', collector.lookingAt() );
        collector.next();
        d = collector.nextDouble();
        assertEquals( "6745", +6745, d, 0.001 );
        assertTrue( collector.isIntegral() );
        System.out.println( "found d = " + d );

        collector.skipOverWhiteSpace();
        assertEquals( 'L', collector.lookingAt() );
        collector.next();
        d = collector.nextDouble();
        assertEquals( "1E-13", 1E-34, d, 0.001 );
        assertFalse( collector.isIntegral() );
        System.out.println( "found d = " + d );

    }
}
