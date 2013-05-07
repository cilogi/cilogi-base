// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestScaleTranslateProcrustes.java  (03-Sep-2012)
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
import org.slf4j.LoggerFactory;

import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

public class TestScaleTranslateProcrustes extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestScaleTranslateProcrustes.class.getName());

    private static final double EPS = 1e-12;

    public TestScaleTranslateProcrustes(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testNoiseless() {
        double s = 3;
        double tx = 19;
        double ty = -17;
        Matrix3d mx = new Matrix3d(s, 0, tx, 0, s, ty, 0, 0, 1);
        Point2d[] from = new Point2d[] {
            new Point2d(0,0), new Point2d(1,0), new Point2d(1,1), new Point2d(0, 1)
        };
        Point2d[] to = new Point2d[from.length];
        for (int i = 0; i < from.length; i++) {
            to[i] = transform(mx, from[i]);
        }
        ScaleTranslateProcrustes proc = new ScaleTranslateProcrustes(from, to);
        for (int i = 0; i < from.length; i++) {
            Point2d p = proc.transform(from[i]);
            assertEquals(0, p.distance(to[i]), EPS);
        }
        assertEquals(s, proc.getScale(), EPS);
        assertEquals(tx, proc.getTranslateX(), EPS);
        assertEquals(ty, proc.getTranslateY(), EPS);
    }

    Point2d transform(Matrix3d mx, Point2d p) {
        Point3d q = new Point3d(p.x, p.y, 1);
        mx.transform(q);
        return new Point2d(q.x, q.y);
    }
}