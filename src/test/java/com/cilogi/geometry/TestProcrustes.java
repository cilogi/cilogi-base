// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestProcrustes.java  (11/04/12)
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
import org.slf4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;

import javax.vecmath.*;

public class TestProcrustes extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestProcrustes.class);


    public TestProcrustes(String nm) {
        super(nm);
        PropertyConfigurator.configure(getClass().getResource("testlog.cfg"));
    }

    @Override
    protected void setUp() {

    }
    
    public void testImage() {
        Point2d[] from = new Point2d[]{
                new Point2d(6, 42),   // top gate
                new Point2d(1139, 241), // side gate
                new Point2d(1009, 603)  // bottom gate
        };
        /*
        Point2d[] to = new Point2d[]{
                new Point2d(55.881681,-4.293718),
                new Point2d(55.878952,-4.287736),
                new Point2d(55.878045,-4.289697)
        };
        */
        Point2d[] to = new Point2d[]{
                new Point2d(-4.293718, -55.881681),
                new Point2d(-4.287736, -55.878952),
                new Point2d(-4.289697, -55.878045)
        };
        
        double x0 = to[0].x;
        double y0 = to[0].y;

        for (int i = 0; i < to.length; i++) {
            to[i].x = (to[i].x - x0) * 180000;
            to[i].y = (to[i].y - y0) * 165000;
        }
        Procrustes2D proc = new Procrustes2D(from, to, true);
        
        LOG.debug("stats " + proc.stats(from, to));

        Matrix3d mx = proc.getTransform();
        Vector2d p0 = transform(mx, new Vector2d(1,0));
        Vector2d p1 = transform(mx, new Vector2d(0,1));
        p0.normalize();
        p1.normalize();
        double dd = p0.dot(p1);
        assertEquals(0.0, dd, 1e-10);
    }


    
    private Point2d transform(Matrix3d mx, Point2d p) {
        Point3d pp = new Point3d(p.x, p.y, 1);
        mx.transform(pp);
        return new Point2d(pp.x, pp.y);
    }
    
    private Vector2d transform(Matrix3d mx, Vector2d v) {
        Vector3d pp = new Vector3d(v.x, v.y, 0);
        mx.transform(pp);
        return new Vector2d(pp.x, pp.y);
        
    }
}