// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        PathBetween.java  (02-Jun-2011)
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

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


public class PathBetween {
    static final Logger LOG = LoggerFactory.getLogger(PathBetween.class);

    private final String from;
    private final String to;

    private String[] fromPath;
    private String[] toPath;

    @SuppressWarnings({"unused"})
    public static String compute(String from, String to) {
        return new PathBetween(from, to).compute();
    }

    public PathBetween(String from, String to) {
        this.from = from;
        this.to = to;
        fromPath = from.split("/");
        toPath = to.split("/");
    }

    String[] getFromPath() {
        return fromPath;
    }

    String[] getToPath() {
        return toPath;
    }

    public String computeFullPathForTo() {
        try {
            List<String> from = Lists.newLinkedList();
            from.addAll(Arrays.asList(fromPath));

            List<String> to = Lists.newLinkedList();
            to.addAll(Arrays.asList(toPath));

            from.remove(from.size() - 1);
            while (to.get(0).equals("..")) {
                from.remove(from.size() - 1);
                to.remove(0);
            }
            from.addAll(to);
            return concat(from, "/");
        } catch (IndexOutOfBoundsException e) {
            LOG.warn("Can't compute full path for " + to + " from " + from);
            return null;
        }
    }

    public String compute() {
        String out = "";
        String[] common = common(fromPath, toPath);

        int upDist = fromPath.length - common.length - 1;
        for (int i = 0; i < upDist; i++) {
            out += "../";
        }

        int downDist = toPath.length - common.length - 1;
        for (int i = 0; i < downDist; i++) {
            out += toPath[common.length + i] + "/";
        }
        out += toPath[toPath.length - 1];
        return out;
    }

    String[] common(String[] a, String[] b) {
        int i = 0;
        for (; i < a.length && i < b.length; i++) {
            if (!a[i].equals(b[i])) {
                break;
            }
        }
        String[] out = new String[i];
        System.arraycopy(a, 0, out, 0, i);
        return out;
    }

    static String concat(List<String> list, String glue) {
        String out = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            out += glue + list.get(i);
        }
        return out;
    }
}
