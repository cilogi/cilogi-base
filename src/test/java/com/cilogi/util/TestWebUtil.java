// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        TestWebUtil.java  (03/03/14)
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


package com.cilogi.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class TestWebUtil {
    static final Logger LOG = LoggerFactory.getLogger(TestWebUtil.class);

    public TestWebUtil() {}

    @Test
    public void testGetBBC() throws IOException {
        String s = WebUtil.getURL(new URL("http://news.bbc.co.uk"));
        assertNotNull(s);
    }
}
