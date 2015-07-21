// Copyright (c) 2011 Tim Niblett. All Rights Reserved.
//
// File:        TestJSON.java  (18-Nov-2011)
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


package com.cilogi.util;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TestJSON extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestJSON.class);


    public TestJSON(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testParsing() throws IOException {
        String s = IOUtil.loadStringUTF8(getClass().getResource("jsonTest.json"));
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
            assertEquals(1, obj.getInt("int"));
            assertEquals("car", obj.getString("string"));
            assertEquals(true, obj.getBoolean("boolean"));
        }  catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }
    
    public void testSimplify() throws IOException {
        String s = IOUtil.loadStringUTF8(getClass().getResource("simplify.json"));
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
            assertEquals(1, obj.getInt("id"));
            assertEquals(878, obj.getInt("x"));
            assertEquals(224, obj.getInt("y"));
            assertEquals("Curator's House", obj.getString("describe"));
        }  catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }
    
    public void testMarker() {
        String s = "{id : green, color: green, icon: camera, label: \"Botanics Trail\"}";
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
        }  catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }
    
    public void testImage() {
        String s = "{tile: osm, src : \"images/main.png\", include-src : false}";
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
        } catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }

    public void testLocate() {
        String s = "{from: [200, 200], to: [19.537, 21.325]}";
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
        } catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }

    public void testLevels() {
        String s = "{min : 14, max: 18, start: 17}";
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
        } catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }

    public void testSizes() {
        String s = "{14: 50%, 15: 60%, 16: 80%, 17: 100%, 18: 120%, threshold: 80%}";
        try {
            JSONObject obj = new JSONObject(s);
            LOG.debug("got back: " + obj.toString());
        } catch (JSONException e) {
            fail("Can't parse " + s + ": " + e.getMessage());
        }
    }
}