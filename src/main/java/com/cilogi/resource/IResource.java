// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        IResource.java  (11-Apr-2011)
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

import com.google.common.collect.Multimap;

import java.util.Date;


public interface IResource {
    public String getPath();
    public byte[] getData();
    public String getMimeType();
    public Date getModified();
    public String getEtag();
    public Multimap<String,Object> getMetaData();
    public IDataSource getDataSource();

    public IResource mimeType(String s);
    public IResource modified(Date d);
    public IResource etag(String s);
    public IResource dataSource(IDataSource dataSource);
    public IResource data(byte[] data);

    /**
     * Change the path of a resource
     * @param path  The new path
     * @return  A copy of this resource with a new path.  There
     * should be nothing shared, so that the old and new resources
     * are independent.
     */
    public IResource withPath(String path);

    /**
     * Set the metadata
     * @param metaData The data to be passed in.  Cannot be null
     * @return  The same resource with metadata added.
     */
    public IResource metaData(Multimap<String, Object> metaData);

    /**
     * Gat an alternative, efficient, location from which this resource can be served
     * @return  The url, or null if there is none
     */
    public String getServingUrl();
}