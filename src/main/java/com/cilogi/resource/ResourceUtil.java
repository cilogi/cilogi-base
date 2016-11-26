// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        ResourceUtil.java  (14-Sep-2011)
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


package com.cilogi.resource;

import com.cilogi.util.DateUtil;
import com.cilogi.util.IOUtil;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ResourceUtil {
    static final Logger LOG = LoggerFactory.getLogger(ResourceUtil.class);

    private ResourceUtil() {

    }

    public static void setCreatedFromMetaData(IResource resource) {
        Collection<Object> created = resource.getMetaData().get("modified");
        if (created.size() > 0) {
            try {
                Date date = DateUtil.parseIso8601Date((String) created.iterator().next());
                resource.modified(date);
            } catch (ParseException e) {
                LOG.error("Can't parse " + created + " as ISO 8601 date of format yyyy-MM-dd'T'HH:mm:ss'Z'");
            }
        }
    }

    public static String combineLines(List<String> lines) {
        StringBuffer buf = new StringBuffer();
        for (String line : lines) {
            buf.append(line);
        }
        return buf.toString();
    }

    public static String resource2string(IResource resource) {
        Preconditions.checkNotNull(resource, "resource is null");
        return new String(resource.getData(), Charsets.UTF_8);
    }
    
    public static List<String> resource2lines(IResource resource) {
        String s = resource2string(resource);
        return IOUtil.loadLines(s);
    }

    public static Dimension imageDimension(IResource resource) {
        try {
            ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(resource.getData()));
            try {
                final Iterator readers = ImageIO.getImageReaders(in);
                if (readers.hasNext()) {
                    ImageReader reader = (ImageReader) readers.next();
                    try {
                        reader.setInput(in);
                        return new Dimension(reader.getWidth(0), reader.getHeight(0));
                    } finally {
                        reader.dispose();
                    }
                }
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        } catch (IOException e) {
            LOG.warn("Error computing image size for " + resource.getPath());
        }
        return null;
    }
}
