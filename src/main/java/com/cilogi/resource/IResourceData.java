// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        IResourceData.java  (21-Nov-16)
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

import com.google.common.collect.Multimap;

import java.util.Date;

public interface IResourceData {
    byte[] getData();
    String getMimeType();
    Date getModified();
    String getEtag();
    String getContentEncoding();
    IResourceData copy();

    IResourceData data(byte[] data);
    IResourceData mimeType(String s);
    IResourceData modified(Date d);
    IResourceData etag(String s);
    IResourceData contentEncoding(String s);

}
