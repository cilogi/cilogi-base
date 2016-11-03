// Copyright (c) 2016 Cilogi. All Rights Reserved.
//
// File:        MemoryCache.java  (8/26/16)
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

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused"})
public class MemoryCache implements IMemcached {
    @SuppressWarnings("unused")
    static final Logger LOG = LoggerFactory.getLogger(MemoryCache.class);

    private final Map<String, byte[]> cache;

    public MemoryCache() {
        cache = new HashMap<>();
    }

    public void put(@NonNull String key, byte[] data) {
        cache.put(key, data);
    }

    public byte[] get(@NonNull String key) {
        return cache.get(key);
    }

    public boolean has(@NonNull String key) { return cache.containsKey(key); }

    public void close() {}
}
