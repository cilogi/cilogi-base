// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        ResourceWrapper.java  (28-Apr-2011)
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


import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Date;

/**
 * Base wrapper for resources so we can extend
 */
public class ResourceWrapper implements IResource {

    private final IResource resource;

    public ResourceWrapper(IResource resource) {
        Preconditions.checkNotNull(resource);
        this.resource = resource;
    }

    @Override
    public String getPath() { return resource.getPath(); }
    @Override
    public byte[] getData() { return resource.getData(); }
    @Override
    public IResource data(byte[] data) { resource.data(data); return this; }
    @Override
    public IDataSource getDataSource() {return resource.getDataSource();}
    @Override
    public String getMimeType() { return resource.getMimeType(); }
    @Override
    public Date getCreated() { return resource.getCreated(); }
    @Override
    public String getEtag() { return resource.getEtag(); }
    @Override
    public Multimap<String,Object> getMetaData() { return resource.getMetaData(); }

    @Override
    public IResource dataSource(IDataSource data) { resource.dataSource(data); return this; }
    @Override
    public IResource mimeType(String s) { resource.mimeType(s); return this; }
    @Override
    public IResource created(Date d) { resource.created(d); return this; }
    @Override
    public IResource etag(String s) { resource.etag(s); return this; }
    @Override
    public IResource withPath(String path) { return new ResourceWrapper(resource.withPath(path)); }
    @Override
    public IResource metaData(Multimap<String,Object> metaData) { resource.metaData(metaData); return this; }
    @Override
    public String getServingUrl() { return resource.getServingUrl(); }
    @Override
    public String toString() {
        return resource.toString();
    }
    @Override
    public int hashCode() {
        return resource.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        return (o instanceof IResource) && resource.equals(o);
    }

    // Som useful extra stuff
    public Object getFirstMetaData(String key) {
        Collection<Object> all =  getMetaData().get(key);
        return (all.size() == 0) ? null : all.iterator().next();
    }
}
