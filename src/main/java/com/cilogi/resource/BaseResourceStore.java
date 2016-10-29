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
import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseResourceStore<T extends IResource> implements IResourceStore {

    protected final List<T> resources;

    protected BaseResourceStore() {
        resources = Collections.synchronizedList(Lists.<T>newLinkedList());
    }

    @Override
    public synchronized List<String> list(String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        List<String> out = Lists.newLinkedList();
        for (IResource resource : resources) {
            String path = resource.getPath();
            Matcher m = p.matcher(path);
            if (m.matches()) {
                out.add(path);
            }
        }
        return out;
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public synchronized void put(IResource resource) {
        delete(resource.getPath());
        resources.add((T)resource);
    }


    @Override
    public synchronized T get(String resourceName) {
        for (T resource : resources) {
            if (resource.getPath().equals(resourceName)) {
                return resource;
            }
        }
        return null;
    }

    @Override
    public synchronized void delete(String resourceName) {
        for (T resource : resources) {
            if (resource.getPath().equals(resourceName)) {
                resources.remove(resource);
                return;
            }
        }
    }

    @Override
    public synchronized void clear() {
        resources.clear();
    }

    @Override
    public void store() {
        throw new UnsupportedOperationException("initialize not implemented");
    }

    @Override
    public abstract T newResource(String path, IDataSource dataSource);

    
    protected List<T> getAll() {
        return Collections.unmodifiableList(resources);
    }

    @SuppressWarnings({"unused"})
    public void saveToDir(File dir) throws IOException {
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

}
