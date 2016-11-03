// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        SimpleResource.java  (03-Nov-16)
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

import com.cilogi.util.Digest;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
public class SimpleResource implements IResource, Serializable {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(SimpleResource.class);
    private static final long serialVersionUID = -1391139347703352785L;

    private final String path;

    private byte[] data;
    private Date modified;
    private String mimeType;
    private String contentEncoding;
    private String etag;
    private HashMultimap<String,Object> metaData;

    public SimpleResource(IResource resource) {
        path = resource.getPath();
        data = resource.getData();
        modified = resource.getModified();
        mimeType = resource.getMimeType();
        contentEncoding = resource.getContentEncoding();
        etag = resource.getEtag();
        metaData = HashMultimap.create(resource.getMetaData());
    }

    private SimpleResource(String path) {
        this.path = path;
        data = new byte[0];
        modified = new Date();
        metaData = HashMultimap.create();
    }

    public SimpleResource copy() {
        SimpleResource copy = new SimpleResource(getPath());
        copy.data(getData());
        copy.modified(modified);
        copy.mimeType(mimeType);
        copy.contentEncoding(contentEncoding);
        copy.etag(etag);
        copy.metaData(LinkedHashMultimap.create(metaData));
        return copy;
    }

    @Override public SimpleResource data(byte[] d) {
        data = d;
        etag = (data == null) ? null : Digest.digestHex(data, Digest.Algorithm.MD5);
        return this;
    }
    @Override public SimpleResource modified(Date d) { modified = d; return this; }
    @Override public SimpleResource mimeType(String s) { mimeType = s; return this; }
    @Override public SimpleResource contentEncoding(String d) { contentEncoding = d; return this; }
    @Override public SimpleResource etag(String d) { etag = d; return this; }
    @Override public SimpleResource metaData(Multimap<String,Object> d) { metaData = HashMultimap.create(d); return this; }

    @Override
    public void addMeta(@NonNull String key, Object object) {
        Multimap<String,Object> map = getMetaData();
        map.put(key, object);
        metaData(map);
    }

    @Override public String getServingUrl() {
         return null;
     }

    @Override public IDataSource getDataSource() {
        return new ByteArrayDataSource(getData());
    }

    @Override public SimpleResource dataSource(IDataSource source) {
        data(source.getData()); return this;
    }

    @Override
    public SimpleResource withPath(String newPath) {
        Preconditions.checkNotNull(newPath);
        SimpleResource out = new SimpleResource(newPath);
        return out
                .data(data.clone())
                .modified(new Date(modified.getTime()))
                .contentEncoding(contentEncoding)
                .etag(etag)
                .mimeType(mimeType)
                .metaData(HashMultimap.create(metaData));
    }

    @Override
    public Object firstMeta(@NonNull String key) {
        Collection<Object> meta = getMetaData().get(key);
        return (meta.size() == 0) ? null : meta.iterator().next();
    }
}
