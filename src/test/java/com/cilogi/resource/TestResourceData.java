// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        TestResourceData.java  (21-Nov-16)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Cilogi (the Author) and may not be used, sold, licenced, 
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


package com.cilogi.resource;

import com.google.common.base.Charsets;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.Assert.*;

public class TestResourceData {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(TestResourceData.class);


    public TestResourceData() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void testCopy() {
        byte[] data = "hello".getBytes(Charsets.UTF_8);
        ResourceData resourceData = new ResourceData()
                .data(data)
                .mimeType("text/plain")
                .contentEncoding("utf-8")
                .modified(new Date());
        ResourceData copy = resourceData.copy();
        assertEquals(copy, resourceData);

        ResourceData build = new ResourceData(resourceData);
        assertEquals(build, resourceData);
    }
}