// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestPathUtil.java  (14-Jun-2011)
// Author:      tim
// $Id$
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used,
// sold, licenced, transferred, copied or reproduced in whole or in
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


package com.cilogi.util.path;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPathUtil extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestPathUtil.class);


    public TestPathUtil(String nm) {
        super(nm);
    }

    public void testChangeName() {
        assertEquals("wilma.txt", PathUtil.changeName("fred.txt", "wilma.txt"));
        assertEquals("a/b/wilma.txt", PathUtil.changeName("a/b/fred.txt", "wilma.txt"));
        assertEquals("a/b/wilma.txt", PathUtil.changeName("a/b/", "wilma.txt"));
    }

    public void testName() {
        assertEquals("fred.txt", PathUtil.name("fred.txt"));
        assertEquals("fred.txt", PathUtil.name("a/b/fred.txt"));
        assertEquals("", PathUtil.name("a/b/"));
    }

    public void testDir() {
        assertEquals("images", PathUtil.dir("images/fred.jpg"));
    }

    public void testIsContentJson() {
        assertTrue(PathUtil.isContentJson("index.json"));
        assertTrue(PathUtil.isContentJson(("contents/pages/page1.json")));
        assertFalse(PathUtil.isContentJson(("contents/pages/page1.jsn")));
        assertFalse(PathUtil.isContentJson(("config.json")));
        assertFalse(PathUtil.isContentJson(("diagrams/map1.json")));
    }


    public void testChangeRelative() {
        assertEquals("fred.txt", PathUtil.changeRelative("wilma.json", "fred.txt"));
        assertEquals("a/b/fred.txt", PathUtil.changeRelative("a/b/wilma.json", "fred.txt"));
        assertEquals("a/b/c/d/fred.txt", PathUtil.changeRelative("a/b/wilma.json", "c/d/fred.txt"));
        assertEquals("a/fred.txt", PathUtil.changeRelative("a/b/wilma.json", "../fred.txt"));
        assertEquals("a/c/d/fred.txt", PathUtil.changeRelative("a/b/wilma.json", "../c/d/fred.txt"));
        assertEquals("c/d/fred.txt", PathUtil.changeRelative("a/b/wilma.json", "../../c/d/fred.txt"));
    }
}