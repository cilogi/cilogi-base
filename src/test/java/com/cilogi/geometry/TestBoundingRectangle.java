// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestBoundingRectangle.java  (12/04/12)
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

public class TestBoundingRectangle extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestBoundingRectangle.class);


    public TestBoundingRectangle(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }
    
    public void testOverlap() {
        BoundingRectangle a = new BoundingRectangle(0, 0, 1, 1);
        BoundingRectangle b = new BoundingRectangle(2, 2, 3, 3);
        BoundingRectangle overlap = a.intersection(b);
        assertFalse(a.overlaps(b));
        assertFalse(b.overlaps(a));
    }
}