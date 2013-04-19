// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        ResourceStore.java  (11-Apr-2011)
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


import java.util.List;
import java.util.Set;

public interface IResourceStore {

    public void addExternalResource(String name);

    public Set<String> getExternalResourceNames();

    /**
     * List resources in the store matching a particular prefix.
     * @param pattern The matching pattern, a regex
     * @return  A list of the resources with this pattern.
     * @throws ResourceStoreException if there is an issue.
     */
    public List<String> list(String pattern);

    /**
     * List resources in the store matching a particular prefix.
     * @param pattern The matching pattern, a regex
     * @param maxNumberToReturn  Limits the maximum number of objects that can be returned.
     * @return  A list of the resources with this pattern.  There may be more than the max.
     * @throws ResourceStoreException if there is an issue, or the max is too many.
     */
    public List<String> list(String pattern, int maxNumberToReturn);

    /**
     * Store a resource
     * @param resource  the resource to store.  Must be non-null.
     * @throws ResourceStoreException if the store fails
     */
    public void put(IResource resource);

    /**
     * Get a resource from a store
     * @param resourceName  The name of the resource.   Must be non-null.
     * @return  The reosurce, or null if no such resource exists
     * @throws ResourceStoreException if the get fails other than by resource non-existence, in
     * which case the resource may or may not exist.
     */
    public IResource get(String resourceName);

    /**
     * Delete a resource.  If no error is thrown a resource with that name will not exist any more.
     * @param resourceName The name of the resource.  Must be non-null.
     * @throws ResourceStoreException if there is a problem deleting the resource, in which case
     * the operation may or may not have succeeded.  There is no exception if the resource doesn't exist.
     * @throws UnsupportedOperationException if this object does not support the (optional) operation
     */
    public void delete(String resourceName);

    /**
     * Delete all resources
     */
    public void clear();

    /**
     * Output this store to some form of permanent memory
     */
    public void store();

    /**
     * Create a new resource compatible with this store
     * @param path  The path for the resource
     * @param data  The data in the resource
     * @return The new resource
     */
    public IResource newResource(String path, byte[] data);

    /**
     * Sort the resources in this store by path.
     * The point is to provide a consistent ordering so that
     * changes from run to run are minimised.  We do a lot of parallel
     * execution which means the ordering will be variable otherwise.
     */
    public void sort();

    public static class ResourceStoreException extends RuntimeException {
        public ResourceStoreException(String s) {
            super(s);
        }
        public ResourceStoreException(Exception e) {
            super(e);
        }
    }
}
