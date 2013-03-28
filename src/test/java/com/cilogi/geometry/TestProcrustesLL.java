// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestProcrustesLL.java  (29/05/12)
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

import javax.vecmath.Point2d;

public class TestProcrustesLL extends TestCase {
    static final Logger LOG = Logger.getLogger(TestProcrustesLL.class);


    public TestProcrustesLL(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }
    
    public void testFlip() {
        Point2d p = new Point2d();
        Point2d f = Procrustes2DLL.flip(p);
        Point2d back = Procrustes2DLL.unFlip(f);
        assertEquals(back, p);
    }
}