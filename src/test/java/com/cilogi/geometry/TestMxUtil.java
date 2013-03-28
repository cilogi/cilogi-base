// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestMxUtil.java  (11/04/12)
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

import javax.vecmath.Matrix3d;
import java.awt.geom.AffineTransform;

public class TestMxUtil extends TestCase {
    static final Logger LOG = Logger.getLogger(TestMxUtil.class);


    public TestMxUtil(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }
    
    public void testRound() {
        Matrix3d mx = new Matrix3d();
        mx.setIdentity();
        mx.rotZ(Math.PI/2);
        AffineTransform a = MxUtil.mx2affine(mx);
        Matrix3d back = MxUtil.affine2mx3d(a);
        assertTrue(mx.epsilonEquals(back, 1e-12));
    }
}