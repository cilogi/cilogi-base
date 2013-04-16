//Copyright (c) 2008 Virtual Mirrors All Rights Reserved.
//
// File:        ${NAME}.java  (${DATE})
// Author:      ${USER}
// $Header: /var/lib/cvsroot/software/java/src/net/vmjava/testtemplate/TestTemplate.java,v 1.2 2007/07/19 18:20:56 joe Exp $
//
// Copyright in the whole and every part of this source file belongs to
// Virtual Mirrors (the Author) and may not be used,
// sold, licensed, transferred, copied or reproduced in whole or in
// part in any manner or form or in or on any media to any person
// other than in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//

package com.cilogi.util;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PropertyConfigurator;

public class TestHistoStatistics extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestHistoStatistics.class);

    private static boolean config = true;

    public TestHistoStatistics(String nm) {
        super(nm);
        if (config) {
            PropertyConfigurator.configure(getClass().getResource("testlog.cfg"));
            config = false;
        }
    }

    protected void setUp() {
    }
    
    public void testLT() {
        HistoStatistics histo = new HistoStatistics();
        assertEquals("", 0, histo.nLT(2));
        
        histo.add(1);
        assertEquals("", 0, histo.nLT(1));
        
        histo.add(2);
        histo.add(3);
        
        assertEquals("", 1, histo.nLT(2));
        assertEquals("", 0, histo.nLT(1));
        assertEquals("", 2, histo.nLT(3));
    }

    public void testLE() {
        HistoStatistics histo = new HistoStatistics();
        assertEquals("", 0, histo.nLE(2));
        
        histo.add(1);
        assertEquals("", 1, histo.nLE(1));
        
        histo.add(2);
        histo.add(3);
        
        assertEquals("", 2, histo.nLE(2));
        assertEquals("", 1, histo.nLE(1));
        assertEquals("", 3, histo.nLE(3));
    }

    public void testGT() {
        HistoStatistics histo = new HistoStatistics();
        assertEquals("", 0, histo.nGT(2));
        
        histo.add(1);
        assertEquals("", 0, histo.nGT(1));
        
        histo.add(2);
        histo.add(3);
        
        assertEquals("", 1, histo.nGT(2));
        assertEquals("", 2, histo.nGT(1));
        assertEquals("", 0, histo.nGT(3));
    }
    
    public void testGE() {
        HistoStatistics histo = new HistoStatistics();
        assertEquals("", 0, histo.nGE(2));
        
        histo.add(1);
        assertEquals("", 1, histo.nGE(1));
        
        histo.add(2);
        histo.add(3);
        
        assertEquals("", 2, histo.nGE(2));
        assertEquals("", 3, histo.nGE(1));
        assertEquals("", 1, histo.nGE(3));
    }

    public void testQuant() {
        HistoStatistics histo = new HistoStatistics();
        
        for (int i = 1; i <= 100; i++) {
            histo.add(i);
        }
        for (int i = 1; i <= 10; i++) {
            double v = i*0.01;
            double q = histo.quant(v);        
            assertEquals(i, q, 1e-10);
        }
        double v = 0.95;
        double q = histo.quant(v);        
        assertEquals(v*100, q, 1e-10);
    }
    
    public void testQuantCentered() {
        HistoStatistics histo = new HistoStatistics();
        
        for (int i = 1; i <= 100; i++) {
            histo.add(i);
        }

        double[] q = histo.quantCentered(0.8);        
        //System.err.println(Arrays.toString(q));
        assertEquals(10, q[0], 1e-10);
        assertEquals(90, q[1], 1e-10);
    }
        
    public void testHisto() {
        HistoStatistics histo = new HistoStatistics();
        assertNull(histo.histo(4));
        
        histo.add(1);
        histo.add(1);
        double[][] h = histo.histo(4);
        assertEquals(1.0, h[0][0]);
        assertEquals(2.0, h[1][0]);

        histo.add(2);
        histo.add(2);
        histo.add(3);
        h = histo.histo(4);
//        for (int i = 0; i < h[0].length; i++) {
//            System.err.println(h[0][i] + ", " + h[1][i]);
//        }
        assertEquals(1.25, h[0][0]);
        assertEquals(2.75, h[0][3]);
        assertEquals(2.0, h[1][0]);
        assertEquals(1.0, h[1][3]);
    }

    public void testSingle() {
        HistoStatistics s = new HistoStatistics();
        s.add(1.0);
        assertEquals(1.0, s.getMean());
    }

    public static void main(String[] args) {

        TestRunner.run(new TestSuite(TestHistoStatistics.class));
    }
}
