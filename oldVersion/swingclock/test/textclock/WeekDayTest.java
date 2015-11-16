/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textclock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hom
 */
public class WeekDayTest {
    
    public WeekDayTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class WeekDay.
     */
    @Test
    public void testToString() {
        System.out.println( "toString" );
        WeekDay instance = new WeekDay(4);
        String expResult = "Fri";
        String result = instance.toString();
        assertEquals( expResult, result );
    }
    
}
