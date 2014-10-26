// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        AffineProcrustes.java  (30/05/12)
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

import com.cilogi.algebra.linear.LeastSquaresColt;
import com.cilogi.util.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.*;
import java.awt.geom.AffineTransform;
import java.util.List;


public class AffineProcrustes {
    static final Logger LOG = LoggerFactory.getLogger(AffineProcrustes.class);

    private Matrix3d mx;
    private Point2d[] from;
    private Point2d[] to;
    
    public AffineProcrustes(List<? extends Tuple2d> from, List<? extends Tuple2d> to) {
        this(from.toArray(new Tuple2d[from.size()]), to.toArray(new Tuple2d[to.size()]));
    }
    
    public AffineProcrustes(Tuple2d[] from, Tuple2d[] to) {
        this.from = cp(from);
        this.to = cp(to);
        init(from, to);
    }
    
    public Point2d transform(Point2d p) {
        return transform(mx, p);
    }
    
    public Matrix3d getMatrix3D() {
        return new Matrix3d(mx);
    }
    
    public AffineTransform getAffineTransform() {
        return MxUtil.mx2affine(mx);
    }

    private void init(Tuple2d[] from, Tuple2d[] to) {
        final int NDIM = 2;
        final int NPAR = 6;
        
        int nPoints = from.length;

        //double[] tmp = new double[NDIM * nPoints * NPAR];
        double[][] A = new double[NDIM * nPoints][NPAR];
        double[] x = new double[NPAR];
        double[] b = new double[NDIM * nPoints];
        for (int i = 0; i < nPoints; i++) {
            int index = i * NDIM;
            Tuple2d p = from[i];
            Tuple2d q = to[i];
            A[index][0] = p.x;
            A[index][1] = p.y;
            A[index][2] = 1;
            b[index] = q.x;

            A[index + 1][3] = p.x;
            A[index + 1][4] = p.y;
            A[index + 1][5] = 1;
            b[index + 1] = q.y;
        }

        //GVector B = new GVector(b);
        mx = new Matrix3d();
        LeastSquaresColt.solve(A, x, b);
        //check(A, x, b);

        mx.m00 = x[0];
        mx.m01 = x[1];
        mx.m02 = x[2];
        mx.m10 = x[3];
        mx.m11 = x[4];
        mx.m12 = x[5];
        mx.m20 = 0;
        mx.m21 = 0;
        mx.m22 = 1;
        computeError(from, to);
    }

    private void computeError(Tuple2d[] from, Tuple2d[] to) {
        Point2d p = new Point2d();
        Vector2d diff = new Vector2d();
        Statistics s = new Statistics();
        for (int i = 0; i < from.length; i++) {
            p.set(from[i]);
            Point2d newP = transform(mx, p);
            diff.sub(newP, to[i]);
            s.add(diff.length());
        }
        LOG.debug("stats = " + s);
    }
    
    
    public static Point2d transform(Matrix3d mx, Point2d p) {
        double x = p.x;
        double y = p.y;
        return new Point2d(mx.m00 * x + mx.m01 * y + mx.m02, mx.m10 * x + mx.m11 * y + mx.m12);
    }

    private static Point2d[] cp(Tuple2d[] in) {
        Point2d[] out = new Point2d[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = new Point2d(in[i]);
        }
        return out;
    }    
}
