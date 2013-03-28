// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestAffineProcrustes.java  (30/05/12)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.geometry;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;

public class TestAffineProcrustes extends TestCase {
    static final Logger LOG = Logger.getLogger(TestAffineProcrustes.class);


    public TestAffineProcrustes(String nm) {
        super(nm);
        PropertyConfigurator.configure(getClass().getResource("testlog.cfg"));
    }

    @Override
    protected void setUp() {

    }
    
    public void testRecover() {
        Point2d[] from = new Point2d[]{
                new Point2d(), new Point2d(1,0), new Point2d(1,1), new Point2d(0, 1)
        };
        Matrix3d mx = new Matrix3d(1, 2, 3, 4, 5, 6, 0, 0, 1);
        Point2d[] to = new Point2d[from.length];
        for (int i = 0; i < from.length; i++) {
            to[i] = AffineProcrustes.transform(mx, from[i]);
        }
        
        AffineProcrustes proc = new AffineProcrustes(from, to);
        Matrix3d out = proc.getMatrix3D();
        assertTrue(mx.epsilonEquals(out, 1e-9));
    }
}