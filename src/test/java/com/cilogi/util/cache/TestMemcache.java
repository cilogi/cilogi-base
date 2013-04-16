// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestMemcache.java  (14-Apr-2011)
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


package com.cilogi.util.cache;

import com.google.common.collect.Lists;
import junit.framework.TestCase;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.transcoders.SerializingTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Random;

public class TestMemcache extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestMemcache.class);


    public TestMemcache(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testThatItWorks() throws IOException {
        SerializingTranscoder coder = new SerializingTranscoder();
        coder.setCompressionThreshold(10000000);
        ConnectionFactory connect = new ConnectionFactoryBuilder()
                .setShouldOptimize(false)
                .setTranscoder(coder)
                .setOpTimeout(5000L)
                .setTimeoutExceptionThreshold(5000) 
                .build();
        MemcachedClient c = new MemcachedClient(
                Lists.newArrayList(new InetSocketAddress("localhost", 11211)));
        c.set("dummy", 10, "a");

        Random r = new Random(1);
        byte[] b = new byte[275000];
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte)(((int)(r.nextDouble() * 255.0))&0xff);
        }

        // Store a value (async) for one hour
        c.set("key", 3600, b);

        // Retrieve a value (synchronously).
        byte[] out = (byte[])c.get("key");
        assertTrue(Arrays.equals(b, out));
    }

    public void testMakeSafe() throws IOException {
        String key = "One Two Three\nFour\rFive  Six\0Seven ";
        String out = new Memcached("").makeSafe(key);
        assertEquals(out, "a6003a4ce5f66d74fa30539cae7145");
    }
}