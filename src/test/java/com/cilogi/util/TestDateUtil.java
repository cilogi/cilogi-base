// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        TestDateUtil.java  (05-Oct-2011)
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
import org.apache.log4j.PropertyConfigurator;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TestDateUtil extends TestCase {
    static final Logger LOG = Logger.getLogger(TestDateUtil.class);


    public TestDateUtil(String nm) {
        super(nm);
        PropertyConfigurator.configure(getClass().getResource("testlog.cfg"));
    }

    @Override
    protected void setUp() {

    }

    public void testFarFuture() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.YEAR, year+1);
        LOG.debug("cal is " + DateUtil.formatIso8601Date(cal.getTime()));
    }

    public void testDateFormat() {
        Date date = new Date();
        String s = date.toString();
        String ss = DateUtil.formatRFC1123Date(date);
        LOG.debug("date = " + s + " rfc " + ss);
    }
    
    public void testBlogFormat() throws ParseException {
        Date date = date(1, 3, 2012);
        String format = DateUtil.formatBlogDate(date);
        assertEquals("[2012-03-01]", format);
        Date back = DateUtil.parseBlogDate(format);
        assertEquals(date, back);
        
        date = date(21, 11, 2012);
        format = DateUtil.formatBlogDate(date);
        assertEquals("[2012-11-21]", format);
        back = DateUtil.parseBlogDate(format);
        assertEquals(date, back);
    }

    public void testJoda() {
        DateTime dt = new DateTime("2013-02-05");
        Date date = dt.toDate();
        LOG.debug("date is " + date);
    }
    
    private static Date date(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month-1, day);
        return cal.getTime();
    }
}