// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestMimeTypes.java  (02-Aug-2011)
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

import junit.framework.TestCase;
import org.apache.log4j.Logger;

public class TestMimeTypes extends TestCase {
    static final Logger LOG = Logger.getLogger(TestMimeTypes.class);


    public TestMimeTypes(String nm) {
        super(nm);
    }

    @Override
    protected void setUp() {

    }

    public void testNoise() {
        assertEquals(MimeTypes.MIME_APPLICATION_OCTET_STREAM, MimeTypes.getMimeTypeFromPath("index.md~"));
    }
}