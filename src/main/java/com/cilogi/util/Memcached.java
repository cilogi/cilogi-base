// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        Memcached.java  (25-May-2011)
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


package com.cilogi.util;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;


public class Memcached implements IMemcached {
    static final Logger LOG = Logger.getLogger(Memcached.class);

    // default time to cache is 1 week
    private static final int DEFAULT_CACHE_TIME = 60 * 60 * 24 * 7;
    private static final int MAX_KEY_LENGTH = 30;
    
    private static final int DEFAULT_MEMCACHED_PORT = 11211;
    
    private final String prefix;
    private final MemcachedClient client;

    public Memcached(String prefix) throws IOException {
        Preconditions.checkNotNull(prefix);
        this.prefix = prefix;
        this.client = new MemcachedClient(new ConnectionFactoryBuilder()
                .setDaemon(true)
                .build(),
                Arrays.asList(new InetSocketAddress("localhost", DEFAULT_MEMCACHED_PORT)));
    }

    public void put(String key, String value) {
        put(key, value, DEFAULT_CACHE_TIME);
    }

    public void put(String key, long value) {
        put(key, Long.toString(value));
    }


    public void put(String key, String value, int cacheTime) {
        Preconditions.checkNotNull(key, "memcached key can't be null");
        Preconditions.checkNotNull(value, "memcached value can't be null");
        Preconditions.checkArgument(cacheTime > 0, "cache time must be > 0 seconds, not " + cacheTime);
        client.set(makeSafe(key), cacheTime, value);
    }

    @Override
    public byte[] getBytes(String key) {
        Preconditions.checkNotNull(key, "memcached key to retrieve can't be null");
        return (byte[])client.get(makeSafe(key));
    }

    @Override
    public void put(String key, byte[] value) {
        put(key, value, DEFAULT_CACHE_TIME);
    }

    public void put(String key, byte[] value, int cacheTime) {
        Preconditions.checkNotNull(key, "memcached key can't be null");
        Preconditions.checkNotNull(value, "memcached value can't be null");
        Preconditions.checkArgument(cacheTime > 0, "cache time must be > 0 seconds, not " + cacheTime);
        client.set(makeSafe(key), cacheTime, value);
    }

    public String getString(String key) {
        Preconditions.checkNotNull(key, "memcached key to retrieve can't be null");
        return (String)client.get(makeSafe(key));
    }


    /**
     * Get a long
     * @param key The key to look for
     * @return  0 if there is no key or the retrieved value
     */
    public long getLong(String key) {
        Preconditions.checkNotNull(key, "memcached key to retrieve can't be null");
        String value = getString(key);
        return (value == null) ? 0 : Long.parseLong(value);
    }

    String makeSafe(String key) {
        char[] ILLEGALS = {'\n','\r','\t','\0'};

        StringBuilder b = new StringBuilder(prefix);
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            boolean omit = false;
            for (char ILLEGAL : ILLEGALS) {
                if (c == ILLEGAL) {
                    omit = true;
                    break;
                }
            }
            if (!omit) {
                b.append((c == ' ') ? '_' : c);
            }
        }
        String longString = b.toString();
        if (longString.length() <= MAX_KEY_LENGTH) {
            return longString;
        }  else {
            String  hash = Digest.digestHex(longString.getBytes(Charsets.UTF_8), Digest.Algorithm.MD5);
            return (hash.length() <= MAX_KEY_LENGTH) ? hash : hash.substring(0, MAX_KEY_LENGTH);
        }
    }

}
