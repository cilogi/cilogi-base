// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        URLResourceLoader.java  (8/26/16)
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


package com.cilogi.resource;

import com.cilogi.util.IOUtil;
import com.cilogi.util.MimeTypes;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Date;

@SuppressWarnings({"unused"})
public class URLResourceLoader implements IResourceLoader {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(URLResourceLoader.class);

    public URLResourceLoader() {}

    @Override
    public IResource get(@NonNull String resourceName) {
        try {
            URL url = new URL(resourceName);
            byte[] data = IOUtil.loadBytes(url);
            if (data == null || data.length == 0) {
                return null;
            } else {
                IDataSource source = new ByteArrayDataSource(data);
                return new Resource(resourceName, source)
                        .mimeType(MimeTypes.getMimeTypeFromPath(resourceName))
                        .modified(new Date());
            }
        } catch (Exception e) {
            LOG.warn("Can't load URL " + resourceName + ": " + e.getMessage());
            return null;
        }
    }
}
