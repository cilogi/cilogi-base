// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        MxUtil.java  (11/04/12)
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Matrix3d;
import java.awt.geom.AffineTransform;


public class MxUtil {
    static final Logger LOG = LoggerFactory.getLogger(MxUtil.class);

    private MxUtil() {

    }
    
    public static AffineTransform mx2affine(Matrix3d mx) {
        return new AffineTransform(mx.m00, mx.m10, mx.m01, mx.m11, mx.m02, mx.m12);
    }
    
    public static Matrix3d affine2mx3d(AffineTransform a) {
        return new Matrix3d(a.getScaleX(), a.getShearX(), a.getTranslateX(),
                            a.getShearY(), a.getScaleY(), a.getTranslateY(),
                            0, 0, 1.0);
    }
}
