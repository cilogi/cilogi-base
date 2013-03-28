// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        Procrustes2DLL.java  (29/05/12)
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

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;

import javax.vecmath.Point2d;
import java.util.List;


class Procrustes2DLL {
    static final Logger LOG = Logger.getLogger(Procrustes2DLL.class);

    private Procrustes2D procrustes;

    Procrustes2DLL(List<Point2d> from, List<Point2d> to, boolean b) {
        procrustes = new Procrustes2D(from, flip(to), b);
    }
    
    Point2d transform(Point2d from, Point2d to) {
        Point2d fromFlip = flip(from);
        procrustes.transform(from, to);
        unFlip(to);
        return to;
    }

    private static List<Point2d> flip(List<Point2d> points) {
        List<Point2d> out = Lists.newArrayList();
        for (Point2d p : points) {
            out.add(flip(p));
        }
        return out;
        
    }
    
    static Point2d flip(Point2d p) {
        return new Point2d(p.y, -p.x);
    }

    static Point2d unFlip(Point2d p) {
        double x = p.x;
        double y = p.y;
        p.y = x;
        p.x = -y;
        return p;
    }
}
