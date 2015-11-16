package textclock;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hom
 */
public class TimeElementTest {

    /**
     * Test of increment method, of class TimeElement.
     */
    @Test
    public void testIncrement() {
        System.out.println( "increment" );
        TimeElement te = new TimeElement( 11 );
        te.increment();
        assertEquals( "next should be one", 1, te.getValue() );
        System.out.println( "te = " + te );
        te.setValue( 9 );
        System.out.println( "te = " + te );
        te.increment();
        assertEquals( "next should be ten", "10", te.toString() );
        assertEquals( "rollover ", 0, te.increment() );
    }

    @Test
    public void testTimeElementChain() {
        System.out.println( "chain" );
        TimeElement te1 = new TimeElement( 10 );
        TimeElement te2 = new TimeElement( 10 );
        te1.setNext( te2 );
        assertSame( "next of te1 is te2", te2, te1.getNext() );
        te1.setValue( 9 );
        assertEquals( "start value te2", 0, te2.getValue() );
        te1.increment();
        assertEquals( "one tick after inc te1", 1, te2.getValue() );
    }

    /**
     * Test of compareTo method, of class TimeElement.
     */
    @Test
    public void testCompareTo() {
        TimeElement te1 = new TimeElement( 0, 20 );
        TimeElement te2 = new TimeElement( 0, 30 );
        assertEquals( "Compare only value based", 0, te1.compareTo( te2 ) );
        te1.increment();
        assertTrue( "Compare only value based, te1 wins", 
                    te1.compareTo( te2 ) > 0 );
        te2.increment();
        te2.increment();
        te2.increment();
        assertTrue( "Compare only value based, te2 wins",
                    te1.compareTo( te2 ) < 0 );

    }
}
