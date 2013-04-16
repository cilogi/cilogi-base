// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        TestRunProcess.java  (17/06/12)
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
import org.apache.log4j.PropertyConfigurator;

import java.io.IOException;

public class TestRunProcess extends TestCase {
    static final Logger LOG = LoggerFactory.getLogger(TestRunProcess.class);


    public TestRunProcess(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testConvert() throws IOException {
        byte[] imgData = IOUtil.loadBytes(getClass().getResource("process.png"));
        RunProcess run = new RunProcess("C:\\Program Files\\ImageMagick-6.7.5-Q16\\convert.exe",
                "-define", "png:format=png8",
                "-define", "png:compression-level=9",
                "-", "-");
        run.run(imgData);
        try {
            int status = run.waitFor();
            if (status == 0) {
                byte[] data = run.output();
                //LOG.debug("output length is " + data.length);
                assertEquals(8985, data.length);
            } else {
                LOG.debug("status was " + status);
            }
        }  catch (InterruptedException e) {
            LOG.warn("oops", e);
        }
        
    }
}