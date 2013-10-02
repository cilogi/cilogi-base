// Copyright (c) 2011 Tim Niblett All Rights Reserved.
//
// File:        Pair.java  (10-Oct-2011)
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

import java.io.Serializable;

/**
 * Generic pair. Either or both elements can be null.  Refer to the fields directly.
 * @param <S>  first element
 * @param <T>  second element
 */
public class Pair<S,T> implements Serializable {

    public final S first;
    public final T second;

    public static<S,T> Pair<S,T> of(S first, T second) {
        return new Pair<S,T>(first, second);
    }

    protected Pair(S first, T second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return ((first == null) ? 0 : first.hashCode()) * 31 + ((second == null) ? 0 : second.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Pair) {
            Pair p = (Pair)o;
            return     ((first == null) ? p.first == null : first.equals(p.first))
                    && ((second == null) ? p.second == null : second.equals(p.second));
        }
        return false;
    }

    @Override
    public String toString() {
        return "<" + first + "," + second + ">";
    }
}

