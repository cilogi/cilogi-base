// Copyright (c) 2014 Cilogi. All Rights Reserved.
//
// File:        ByteArrayDataSource.java  (01/07/14)
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

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteArrayDataSource implements IDataSource {
    static final Logger LOG = LoggerFactory.getLogger(ByteArrayDataSource.class);

    private byte[] data;

    public ByteArrayDataSource() {
        data = new byte[0];
    }

    public ByteArrayDataSource(byte[] data) {
        Preconditions.checkNotNull(data, "Data cannot be null");
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return data;
    };

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public IDataSource copy() {
        return new ByteArrayDataSource(data.clone());
    }
}
