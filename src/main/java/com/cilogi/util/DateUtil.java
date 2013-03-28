// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        DateUtil.java  (13-Apr-2011)
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

import com.google.common.base.Preconditions;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class DateUtil {
    static final Logger LOG = Logger.getLogger(DateUtil.class);

    private DateUtil() {

    }

    private static final SimpleDateFormat blogFormat = new SimpleDateFormat(
        "[yyyy-MM-dd]"
    );

    private static final SimpleDateFormat rfc1123DateParser = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss zzz");

    private static final SimpleDateFormat iso8601DateParser = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final SimpleDateFormat rfc822DateParser = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

    static {
        blogFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        iso8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
        rfc822DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
        rfc1123DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    public static Date oldParseBlogDate(String dateString) throws ParseException {
        synchronized (blogFormat) {
            Date date = blogFormat.parse(dateString);
            //SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");
            //LOG.info(dateString + " -> " + dt.format(date));
            return date;
        }
    }

    public static Date parseBlogDate(String dateString) {
        DateTime dt = new DateTime(dateString.replaceAll("\\[", "").replaceAll("\\]", ""));
        return dt.toDate();
    }

    public static String formatBlogDate(Date date) {
        Preconditions.checkNotNull(date, "Date can't be null when formatted");
        synchronized (blogFormat) {
            return blogFormat.format(date);
        }
    }

    public static Date parseIso8601Date(String dateString) throws ParseException {
        synchronized (iso8601DateParser) {
            return iso8601DateParser.parse(dateString);
        }
    }

    public static String formatIso8601Date(Date date) {
        synchronized (iso8601DateParser) {
            return iso8601DateParser.format(date);
        }
    }

    public static Date parseRFC822Date(String dateString) throws ParseException {
        synchronized (rfc822DateParser) {
            return rfc822DateParser.parse(dateString);
        }
    }

    public static String formatRFC822Date(Date date) {
        synchronized (rfc822DateParser) {
            return rfc822DateParser.format(date);
        }
    }

    public static Date parseRFC1123Date(String dateString) throws ParseException {
        synchronized (rfc1123DateParser) {
            return rfc1123DateParser.parse(dateString);
        }
    }

    public static String formatRFC1123Date(Date date) {
        synchronized (rfc1123DateParser) {
            return rfc1123DateParser.format(date);
        }
    }

    /**
     * Get a far future date
     * @return A date about a year from now, which by rfc2616 is as long as one is allowed
     */
    public static Date farFuture() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.YEAR, year+1);
        return cal.getTime();
    }
}
