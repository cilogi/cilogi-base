// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestGlob.java  (28-Apr-2011)
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
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestGlob extends TestCase {
    static final Logger LOG = Logger.getLogger(TestGlob.class);


    public TestGlob(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testStart() {
        Glob glob = new Glob("*.md", true);
        assertTrue(glob.match("tim.md"));
        assertTrue(glob.match("tim.MD"));
        assertTrue(glob.match("a/b/c/tim.MD"));
        assertFalse(glob.match("tim.mc"));
    }

    public void testTilda() {
        Glob glob = new Glob("*~", true);
        assertTrue(glob.match("tim.md~"));
        assertTrue(glob.match("tim~"));
        assertFalse(glob.match("README"));
    }

    public void testPicasa() {
        Glob glob = new Glob("*picasaoriginals/*", true);
        assertTrue(glob.match("contents/trail/images/.picasaoriginals/.picasa.ini"));

    }

    public void testJson() {
        Glob glob = new Glob("contents/*.json", true);
        assertTrue(glob.match("contents/tim.json"));
        assertTrue(glob.match("contents/a/b/c/tim.JSON"));

    }
}