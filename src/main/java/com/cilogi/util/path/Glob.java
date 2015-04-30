// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        Glob.java  (28-Apr-2011)
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

import com.cilogi.resource.IResource;
import com.google.common.collect.Lists;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"unused"})
public class Glob {
    static final Logger LOG = LoggerFactory.getLogger(Glob.class);

    private final List<Pattern> patterns;
    private final boolean isIgnoreCase;

    public Glob() {
        patterns = Lists.newLinkedList();
        isIgnoreCase = true;
    }

    public Glob(String glob, boolean isIgnoreCase) {
        this(Arrays.asList(glob), isIgnoreCase);
    }

    /**
     * Create a glob.
     *
     * @param globs        The list of patterns.  A match occurs is ANY pattern matches
     * @param isIgnoreCase Do we ignore the case of the match?
     */
    public Glob(List<String> globs, boolean isIgnoreCase) {
        patterns = new ArrayList<>(globs.size());
        for (String glob : globs) {
            if (glob.trim().startsWith("#")) {
                continue;
            }
            String regex = convertGlobToRegEx(glob);
            patterns.add(isIgnoreCase ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE) : Pattern.compile(regex));
        }
        this.isIgnoreCase = isIgnoreCase;
    }

    public Glob(@NonNull IResource resource, boolean isIgnoreCase) {
        this(file2strings(resource), isIgnoreCase);
    }

    public Glob(byte[] data, boolean isIgnoreCase)  {
        this(bytes2list(data), isIgnoreCase);
    }

    public void add(String glob) {
        String regex = convertGlobToRegEx(glob);
        patterns.add(isIgnoreCase ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE) : Pattern.compile(regex));
    }

    private static List<String> file2strings(IResource resource)  {
        return bytes2list(resource.getData());
    }

    private static List<String> bytes2list(byte[] data) {
        List<String> out = Lists.newLinkedList();
        try {
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new ByteArrayInputStream(data), Charset.forName("utf-8")));
            String line;
            while ((line = reader.readLine()) != null) {
                out.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out;
    }


    public boolean match(String name) {
        for (Pattern pattern : patterns) {
            Matcher match = pattern.matcher(name);
            if (match.matches()) {
                return true;
            }
        }
        return false;
    }

    private String convertGlobToRegEx(String line) {
        line = line.trim();
        int strLen = line.length();
        StringBuilder sb = new StringBuilder(strLen);
        boolean escaping = false;
        int inCurlies = 0;
        for (char currentChar : line.toCharArray()) {
            switch (currentChar) {
                case '*':
                    if (escaping)
                        sb.append("\\*");
                    else
                        sb.append(".*");
                    escaping = false;
                    break;
                case '?':
                    if (escaping)
                        sb.append("\\?");
                    else
                        sb.append('.');
                    escaping = false;
                    break;
                case '.':
                case '(':
                case ')':
                case '+':
                case '|':
                case '^':
                case '$':
                case '@':
                case '%':
                    sb.append('\\');
                    sb.append(currentChar);
                    escaping = false;
                    break;
                case '\\':
                    if (escaping) {
                        sb.append("\\\\");
                        escaping = false;
                    } else
                        escaping = true;
                    break;
                case '{':
                    if (escaping) {
                        sb.append("\\{");
                    } else {
                        sb.append('(');
                        inCurlies++;
                    }
                    escaping = false;
                    break;
                case '}':
                    if (inCurlies > 0 && !escaping) {
                        sb.append(')');
                        inCurlies--;
                    } else if (escaping)
                        sb.append("\\}");
                    else
                        sb.append("}");
                    escaping = false;
                    break;
                case ',':
                    if (inCurlies > 0 && !escaping) {
                        sb.append('|');
                    } else if (escaping)
                        sb.append("\\,");
                    else
                        sb.append(",");
                    break;
                default:
                    escaping = false;
                    sb.append(currentChar);
            }
        }
        return sb.toString();
    }
}
