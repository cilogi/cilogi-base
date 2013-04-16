// Copyright (c) 2013 Cilogi. All Rights Reserved.
//
// File:        MemcacheClassLoader.java  (16/04/13)
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


package com.cilogi.util.cache;

import com.google.common.cache.CacheLoader;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.util.logging.Logger;

/**
 * This class can only be used when you're running a Memcached server locally.  The idea
 * is to have a smallish in-memory cache, backed up by Memcached, which can be as big as you like.
 * @param <T>
 */
public class MemcacheClassLoader<T> extends CacheLoader<String,T> {
    static final Logger LOG = Logger.getLogger(MemcacheClassLoader.class.getName());

    private final IMemcached cache;
    private final CacheLoader<String,T> base;

    public MemcacheClassLoader(IMemcached cache, CacheLoader<String,T> base) {
        this.cache = cache;
        this.base = base;
    }

    @SuppressWarnings({"unchecked"})
    public T load(String key) throws Exception {
        byte[] data = cache.get(key);
        if (data != null) {
            return (T)SerializationUtils.deserialize(data);
        } else {
            T obj = base.load(key);
            cache.put(key, SerializationUtils.serialize((Serializable)obj));
            return obj;
        }
    }
}
