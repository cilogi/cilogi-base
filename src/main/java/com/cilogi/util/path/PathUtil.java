// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        PathUtil.java  (04-Jun-2011)
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
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"unused"})
public class PathUtil {
    static final Logger LOG = LoggerFactory.getLogger(PathUtil.class);


    private static Glob glob = new Glob(Lists.newArrayList(
            "index.json", "contents/*"
    ), true);

    private PathUtil() {}

    public static boolean isContentJson(@NonNull String path) {
        String pathLC = path.toLowerCase();
        return pathLC.endsWith(".json") && glob.match(path);
    }

    public static String rootPathFrom(String path) {
        String[] subs = path.split("/");
        String out = "";
        for (int i = 0; i < subs.length - 1; i++) {
            out += "../";
        }
        return out;
    }

    public static String changeExtension(String path, String extension) {
        String end = (extension == null || "".equals(extension)) ? "" : "." + extension;
        int idx = path.lastIndexOf(".");
        if (idx == -1) {
            return path + end;
        } else {
            return path.substring(0, idx) + end;
        }
    }

    /**
     * Change the name component of a path, from say <pre>a/b/fred.txt</pre> to <pre>a/b/wilma.txt</pre>
     *
     * @param path The original path, including name
     * @param name The new name
     * @return The path with the name component replaced.
     */
    public static String changeName(String path, String name) {
        int index = path.lastIndexOf("/");
        return (index == -1) ? name : path.substring(0, index + 1) + name;
    }

    public static String name(String path) {
        int index = path.lastIndexOf("/");
        return (index == -1) ? path : path.substring(index + 1);
    }

    public static String dir(String path) {
        int index = path.lastIndexOf("/");
        return (index == -1) ? "" : path.substring(0, index);
    }
}
