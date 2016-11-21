// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        ResourceData.java  (20-Nov-16)
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

import com.cilogi.util.MimeTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;

/**
 * Resource without the path
 */
@EqualsAndHashCode
public class ResourceData implements Serializable, IResourceData {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(ResourceData.class);
    private static final long serialVersionUID = 2479255586029290591L;

    @Getter
    private String mimeType;
    @Getter
    private Date modified;
    @Getter
    private String etag;
    @Getter
    private String contentEncoding;
    @Getter
    private byte[] data;

    public ResourceData() {
        mimeType = MimeTypes.MIME_APPLICATION_OCTET_STREAM;
        modified = new Date();
    }

    public ResourceData(IResourceData resourceData) {
        mimeType = resourceData.getMimeType();
        modified = resourceData.getModified();
        etag = resourceData.getEtag();
        contentEncoding = resourceData.getContentEncoding();
        data = (resourceData.getData() == null) ? null : resourceData.getData().clone();
    }

    public ResourceData copy() {
        ResourceData out = new ResourceData();
        out.mimeType = this.getMimeType();
        out.modified = this.getModified();
        out.etag = this.getEtag();
        out.contentEncoding = this.getContentEncoding();
        out.data = (this.data == null) ? null : this.data.clone();
        return out;
    }

    public ResourceData mimeType(String mimeType) { this.mimeType = mimeType; return this; }
    public ResourceData modified(Date modified) { this.modified = modified; return this; }
    public ResourceData etag(String etag) { this.etag = etag; return this; }
    public ResourceData contentEncoding(String contentEncoding) { this.contentEncoding = contentEncoding; return this; }
    public ResourceData data(byte[] data) { this.data = data; return this; }
}
