// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestPathBetween.java  (02-Jun-2011)
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

public class TestPathBetween extends TestCase {
    static final Logger LOG = Logger.getLogger(TestPathBetween.class);


    public TestPathBetween(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testCommon() {
        PathBetween p = new PathBetween("a/b/c", "a/d/e");
        String[] common = p.common(p.getFromPath(), p.getToPath());
        assertEquals(1, common.length);
        assertEquals("a", common[0]);
    }

    public void testCommonNoCommon() {
        PathBetween p = new PathBetween("a/b/c", "f/d/e");
        String[] common = p.common(p.getFromPath(), p.getToPath());
        assertEquals(0, common.length);
    }

    public void testCommonMany() {
        PathBetween p = new PathBetween("a/b/c", "a/b/d");
        String[] common = p.common(p.getFromPath(), p.getToPath());
        assertEquals(2, common.length);
        assertEquals("a", common[0]);
    }

    public void testComputeSameDir() {
        PathBetween p = new PathBetween("a/b/c.html", "a/b/d.html");
        String out = p.compute();
        assertEquals("d.html", out);
    }

    public void testComputeAcrossDir() {
        PathBetween p = new PathBetween("a/b/jan/c.html", "a/b/feb/d.html");
        String out = p.compute();
        assertEquals("../feb/d.html", out);
    }

    public void testDiagram() {
        PathBetween p = new PathBetween("diagrams/botanics.md", "contents/trail/images/curators-house.jpg");
        String out = p.compute();
        assertEquals("../contents/trail/images/curators-house.jpg", out);
    }

    public void testComputeAcrossYear() {
        PathBetween p = new PathBetween("blog/2011/jan/c.html", "blog/2010/feb/d.html");
        String out = p.compute();
        assertEquals("../../2010/feb/d.html", out);
    }

    public void testComputeFullPathForTo() {
        assertEquals("a/b/c/foo.html", new PathBetween("a/b/c/foo.html", "foo.html").computeFullPathForTo());
        assertEquals("a/b/fred/foo.html", new PathBetween("a/b/c/foo.html", "../fred/foo.html").computeFullPathForTo());
    }

    public void testComputeComplex() {
        assertEquals("thumbx240-fred.jpg", new PathBetween("a/b/c/fred.jpg", "a/b/c/thumbx240-fred.jpg").compute());
    }

    public void testExternal() {
        String external = "http://acme.com/images/fred.jpg";
        String path = new PathBetween("fred.html", external).computeFullPathForTo();
        assertEquals(external, path);
    }

    public void testDiagrams() {
        String original = "diagrams";
        String current = "contents/pages/fred.json";
        String path = new PathBetween(current, original).compute();
        assertEquals("../../diagrams", path);
    }

    public void testLinks() {
        String kibble = "contents/pages/kibble.html";
        String next = "contents/pages/next.html";
        String path = new PathBetween(kibble, next).compute();
        assertEquals("next.html", path);
    }
}