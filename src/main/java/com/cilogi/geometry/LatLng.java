// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        LatLng.java  (03-Sep-2012)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
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


package com.cilogi.geometry;

import java.io.Serializable;


public class LatLng implements Serializable {

    public static final double DEG_TO_RAD =  Math.PI / 180.0;
    public static final double RAD_TO_DEG =  180.0 / Math.PI;

    private final double lat;
    private final double lng;

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
    
    @Override
    public String toString() {
        return "[" + lat + "," + lng + "]";
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(lat) * 31L + Double.doubleToLongBits(lng);
        return (int)(bits ^ (bits >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LatLng) {
            LatLng ll = (LatLng)o;
            return Double.doubleToLongBits(ll.lat) == Double.doubleToLongBits(lat) &&
                   Double.doubleToLongBits(ll.lng) == Double.doubleToLongBits(lng);
        }
        return false;
    }
}
