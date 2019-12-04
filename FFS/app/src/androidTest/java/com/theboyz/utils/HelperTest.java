package com.theboyz.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelperTest {

    Helpers h;
    Double [] tester1;
    double [] tester2;

    @Before // setup the tester
    public void setup(){

        h = new Helpers();

        // setup two doubles to compare
        tester1 = new Double[] {2.0, 4.0, 7.0, 10.0};
        tester2 = new double[] {2.0, 4.0, 7.0, 10.0};

    } // end setup

    @Test
    public void testDoubleToPrimitive() {
        assertArrayEquals(tester2, h.DoubleToPrimitive(tester1), 0.0);
    } // end Double To Primitive
}