// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        IMemcached.java  (25-May-2011)
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


package com.cilogi.util.cache;

public interface IMemcached {

    public void put(String key, byte[] data);

    public byte[] get(String key);

    public boolean has(String key);

    public boolean remove(String key);

    public void close();
}
