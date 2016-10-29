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


import java.io.IOException;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
interface IResourceStore extends IResourceLoader {


    /**
     * List resources in the store matching a particular prefix.
     * @param pattern The matching pattern, a regex, non null
     * @return  A list of the resources with this pattern.
     * @throws ResourceStoreException if there is an issue.
     */
    List<String> list(String pattern);

    /**
     * Store a resource
     * @param resource  the resource to store.  Must be non-null.
     * @throws ResourceStoreException if the store fails
     */
    void put(IResource resource);

    /**
     * Get a resource from a store
     * @param resourceName  The name of the resource.   Must be non-null.
     * @return  The resource, or null if no such resource exists
     * @throws ResourceStoreException if the get fails other than by resource non-existence, in
     * which case the resource may or may not exist.
     */
    @Override
    IResource get(String resourceName);

    /**
     * Delete a resource.  If no error is thrown a resource with that name will not exist any more.
     * @param resourceName The name of the resource.  Must be non-null.
     * @throws ResourceStoreException if there is a problem deleting the resource, in which case
     * the operation may or may not have succeeded.  There is no exception if the resource doesn't exist.
     * @throws UnsupportedOperationException if this object does not support the (optional) operation
     */
    void delete(String resourceName);

    /**
     * Delete all resources
     */
    void clear();

    /**
     * Output this store to some form of permanent memory
     */
    void store();

    /**
     * Create a new resource compatible with this store
     * @param path  The path for the resource
     * @param dataSource  The data in the resource
     * @return The new resource
     */
    IResource newResource(String path, IDataSource dataSource);


    class ResourceStoreException extends RuntimeException {
        ResourceStoreException(String s) {
            super(s);
        }
        ResourceStoreException(Exception e) {
            super(e);
        }
    }
}
