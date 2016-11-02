// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        BaseResourceStore.java  (11-Apr-2011)
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

import com.cilogi.util.IOUtil;
import com.google.common.base.Preconditions;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseResourceStore<T extends IResource> implements IResourceStore {

    private final Map<String,T> resources;

    protected BaseResourceStore() {
        resources = Collections.synchronizedMap(new LinkedHashMap<String,T>());
    }

    @Override
    public synchronized List<String> list(@NonNull String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        List<String> out = new ArrayList<>();
        for (String path : resources.keySet()) {
            Matcher m = p.matcher(path);
            if (m.matches()) {
                out.add(path);
            }
        }
        return out;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public synchronized void put(@NonNull IResource resource) {
        resources.put(resource.getPath(), (T)resource);
    }


    @Override
    public synchronized T get(@NonNull String resourceName) {
        return resources.get(resourceName);
    }

    @Override
    public synchronized void delete(String resourceName) {
        resources.remove(resourceName);
    }

    @Override
    public synchronized void clear() {
        resources.clear();
    }

    @Override
    public synchronized void store() {
        throw new UnsupportedOperationException("initialize not implemented");
    }

    @Override
    public abstract T newResource(String path, IDataSource dataSource);

    
    protected synchronized Collection<T> getAll() {
        return Collections.unmodifiableCollection(resources.values());
    }

    @SuppressWarnings({"unused"})
    public synchronized void saveToDir(File dir) throws IOException {
        Preconditions.checkArgument(dir.isDirectory());
        if (dir.mkdirs()) {
            for (T resource : getAll()) {
                String path = resource.getPath();
                File file = new File(dir, path);
                if (file.getParentFile().mkdirs()) {
                    IOUtil.storeBytes(resource.getData(), file);
                }
            }
        }
    }

    @SuppressWarnings({"unused"})
    public synchronized IResource importResource(String path, IResource resource) throws IOException {
        IResource newResource = newResource(path, resource.getDataSource())
                .metaData(resource.getMetaData())
                .mimeType(resource.getMimeType())
                .contentEncoding(resource.getContentEncoding())
                .modified(resource.getModified());
        put(newResource);
        return newResource;
    }

}
