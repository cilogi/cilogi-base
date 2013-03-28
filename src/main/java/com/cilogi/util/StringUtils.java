// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        StringUtils.java  (30/03/12)
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

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


public class StringUtils {
    static final Logger LOG = Logger.getLogger(StringUtils.class);

    private StringUtils() {}
    
    public static List<String> splitLines(String text) {
        List<String> out = Lists.newLinkedList();
        LineNumberReader rdr = new LineNumberReader(new StringReader(text));
        try {
            for (String line = rdr.readLine(); line != null; line = rdr.readLine()) {
                out.add(line);
            }
            return out;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected reader issue: " + e.getMessage());
        }
    }


    public static Map<String,String> strings2map(String... args) {
        Preconditions.checkArgument(args.length % 2 == 0, "Even number of arguments, not " + args.length);
        Map<String,String> out = Maps.newHashMap();
        for (int i = 0; i < args.length; i += 2) {
                out.put(args[i], args[i+1]);
        }
        return out;
    }

    public static String urlEncode(String query) {
        try {
            String encode = URLEncoder.encode(query, Charsets.UTF_8.name());
            return encode.replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            LOG.warn("Can't encode URL: " + e.getMessage());
            return query;
        }
    }
}
