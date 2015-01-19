// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        ScaleTranslateProcrustes.java  (30/05/12)
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

import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;
import java.util.List;

/**
 * This class computes an scale and translate transform between two sets of points.
 * App Engine doesn't support rotation, just translation and scaling.
 */
public class ScaleTranslateProcrustes {
    static final Logger LOG = LoggerFactory.getLogger(ScaleTranslateProcrustes.class.getName());

    private Matrix3d mx;
    private Matrix3d imx;
    private Statistics error;

    public ScaleTranslateProcrustes(List<? extends Tuple2d> from, List<? extends Tuple2d> to) {
        this(from.toArray(new Tuple2d[from.size()]), to.toArray(new Tuple2d[to.size()]));
    }
    
    public ScaleTranslateProcrustes(Tuple2d[] from, Tuple2d[] to) {
        init(from, to);
    }
    
    public Point2d transform(Point2d p) {
        return transform(mx, p);
    }

    public Point2d inverseTransform(Point2d p) {
        return transform(imx, p);
    }

    public double getScale() {
        return mx.m00;
    }

    public double getTranslateX() {
        return mx.m02;
    }

    public double getTranslateY() {
        return mx.m12;
    }
    public Matrix3d getMatrix3D() {
        return new Matrix3d(mx);
    }

    private void init(Tuple2d[] from, Tuple2d[] to) {
        final int NDIM = 2;
        final int NPAR = 3;
        
        int nPoints = from.length;

        double[][] A = new double[NDIM * nPoints][NPAR];
        double[] x = new double[NPAR];
        double[] b = new double[NDIM * nPoints];
        for (int i = 0; i < nPoints; i++) {
            int index = i * NDIM;
            Tuple2d p = from[i];
            Tuple2d q = to[i];
            A[index][0] = p.x;
            A[index][1] = 1;
            A[index][2] = 0;
            b[index] = q.x;

            A[index + 1][0] = p.y;
            A[index + 1][1] = 0;
            A[index + 1][2] = 1;
            b[index + 1] = q.y;
        }

        mx = new Matrix3d();
        LeastSquaresColt.solve(A, x, b);
        //check(A, x, b);

        mx.m00 = x[0];
        mx.m01 = 0;
        mx.m02 = x[1];
        mx.m10 = 0;
        mx.m11 = x[0];
        mx.m12 = x[2];
        mx.m20 = 0;
        mx.m21 = 0;
        mx.m22 = 1;
        computeError(from, to);
        imx = new Matrix3d(mx);
        imx.invert();
    }

    private void computeError(Tuple2d[] from, Tuple2d[] to) {
        Point2d p = new Point2d();
        Vector2d diff = new Vector2d();
        error = new Statistics();
        for (int i = 0; i < from.length; i++) {
            p.set(from[i]);
            Point2d newP = transform(mx, p);
            diff.sub(newP, to[i]);
            error.add(diff.length());
        }
    }

    public Statistics getError() {
        return error;
    }
    
    
    private static Point2d transform(Matrix3d mx, Point2d p) {
        double x = p.x;
        double y = p.y;
        return new Point2d(mx.m00 * x + mx.m01 * y + mx.m02, mx.m10 * x + mx.m11 * y + mx.m12);
    }    
}
