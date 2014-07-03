// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        Resource.java  (11-Apr-2011)
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

import com.cilogi.util.Digest;
import com.cilogi.util.MimeTypes;
import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

// Not sure if I should make this class immutable or not.
// At the moment its not.
public class Resource implements IResource, Comparable<Resource> {
    static final Logger LOG = LoggerFactory.getLogger(Resource.class);

    private final String path;

    private IDataSource dataSource;
    private Date created;
    private Date modified;
    private String mimeType;
    private String etag;
    private Multimap<String,Object> metaData;

    public Resource(String path) {
        this(path, new ByteArrayDataSource());
    }

    public Resource(String path, IDataSource dataSource) {
        Preconditions.checkNotNull(path);

        this.path = path;
        this.dataSource = dataSource;
        created = new Date();
        modified = new Date();
        mimeType = null;
        etag = null;
        metaData = LinkedHashMultimap.create();
    }


    @Override
    public Resource created(Date created) {
        Preconditions.checkNotNull(created);
        this.created = created; return this;
    }

    @Override
    public Resource modified(Date modified) {
        Preconditions.checkNotNull(modified);
        this.modified = modified; return this;
    }

    @Override
    public Resource mimeType(String mimeType) {
        this.mimeType = mimeType; return this;
    }

    @Override
    public Resource dataSource(IDataSource dataSource) {
        Preconditions.checkNotNull(dataSource);
        this.dataSource = dataSource;
        return this;
    }

    @Override
    public Resource data(byte[] data) {
        Preconditions.checkNotNull(data);
        dataSource.setData(data);
        return this;
    }

    @Override
    public Resource withPath(String newPath) {
        Preconditions.checkNotNull(newPath);
        Resource out = new Resource(newPath);
        return out
                .dataSource(dataSource.copy())
                .created(new Date(created.getTime()))
                .modified(new Date(modified.getTime()))
                .mimeType(mimeType);
    }

    @Override
    public Resource metaData(Multimap<String,Object> metaData) {
        Preconditions.checkNotNull(metaData, "metadata can't be null");
        this.metaData = LinkedHashMultimap.create();
        this.metaData.putAll(metaData); return this;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public synchronized byte[] getData() {
        LOG.info("GET " + getPath());
        return dataSource.getData();
    }

    @Override
    public synchronized IDataSource getDataSource() {
        return dataSource;
    }

    @Override
    public String getMimeType() {
        if (mimeType != null) {
            return mimeType;
        }
        int dot = path.lastIndexOf(".");
        return (dot == -1) ? "application/binary" : MimeTypes.getMimeType(path.substring(dot + 1));
    }

    @Override
    public String getEtag() {
        return etag;
    }

    @Override
    public IResource etag(String etag) {
        this.etag = etag; return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public Date getModified() {
        return modified;
    }

    @Override
    public Multimap<String,Object> getMetaData() {
        Multimap<String,Object> map = LinkedHashMultimap.create(metaData);
        return map;
    }

    @Override
    public String getServingUrl() {
        return null;
    }


    @Override
    public int hashCode() {
        return getPath().hashCode() * 31;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Resource) {
            Resource r = (Resource)o;
            return getPath().equals(r.getPath());
        } else {
            return false;
        }
    }

    public String getDigest() {
        return Digest.digestHex(getData(), Digest.Algorithm.MD5);
    }

    @Override
    public String toString() {
        return getPath() + "(" + getData().length + ")";
    }
    
    @Override
    public int compareTo(Resource resource) {
        return getPath().compareTo(resource.getPath());
    }
}
