// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestIOUtil.java  (10-Oct-2011)
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


package com.cilogi.util;

import com.google.common.base.Charsets;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestIOUtil extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestIOUtil.class);


    public TestIOUtil(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testZip() {
        String in = "Hello World";
        byte[] zipped = IOUtil.gzip(in.getBytes(Charsets.UTF_8));
        byte[] unzipped = IOUtil.gunzip(zipped);
        assertEquals(in, new String(unzipped, Charsets.UTF_8));
    }

    public void testInflateDeflate() {
        String in = "Hello World";
        byte[] zipped = IOUtil.deflate(in.getBytes(Charsets.UTF_8));
        byte[] unzipped = IOUtil.inflate(zipped);
        assertEquals(in, new String(unzipped, Charsets.UTF_8));
    }

    public void testLoadBytes() throws IOException {
        final int SZ = 1000000;
        File test = testFile(SZ, (byte)1);
        byte[] data = IOUtil.loadBytes(test);
        assertEquals(SZ, data.length);
        for (int i = 0; i < SZ; i++) {
            assertEquals((byte)1, data[i]);
        }
    }

    static File testFile(int len, byte val) throws IOException {
        File file = File.createTempFile("ioutil", "dat");
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            data[i] = val;
        }
        FileOutputStream os = new FileOutputStream(file);
        os.write(data);
        os.close();
        file.deleteOnExit();
        return file;
    }
}