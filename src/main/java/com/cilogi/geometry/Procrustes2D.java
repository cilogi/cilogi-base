// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        Procrustes2D.java  (11/04/12)
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

import com.cilogi.util.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.vecmath.Matrix3d;
import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Procrustes2D implements IPointTransformer {
    static final Logger LOG = LoggerFactory.getLogger(Procrustes2D.class);

    private Matrix3d transform;

    public Procrustes2D(List<Point2d> from, List<Point2d> to, boolean isScale) {
        this(from.toArray(new Point2d[from.size()]), to.toArray(new Point2d[to.size()]), isScale);
    }

    /**
     * Find the Procrustes transform between two sets of points.
     *
     * @param from    The from points.
     * @param to      The to points.  Should be the same length as the to points.
     * @param isScale true iff a scaling parameter is sought, otherwise a
     *                rigid body transform will be found without scaling.
     */
    public Procrustes2D(Point2d[] from, Point2d[] to, boolean isScale) {
        if (from.length != to.length) {
            throw new IllegalArgumentException("from " + from.length + " to " + to.length +
                    " length mismatch, shoul dbe equal");
        }
        transform = identity3d();
        compute(copy(from), copy(to), isScale);
    }
    
    public AffineTransform getAffineTransform() {
        return MxUtil.mx2affine(getTransform());
    }
    
    public Statistics stats(Point2d[] from, Point2d[] to) {
        Point2d tmp = new Point2d();
        Statistics stats = new Statistics();
        for (int i = 0; i < from.length; i++) {
            transform(from[i], tmp);
            stats.add(tmp.distance(to[i]));
        }
        return stats;
    }

    /**
     * The Procrustes transform
     *
     * @return The 3x3 transform matrix which maps the <code>from</code> points
     *         in the constructor to the <code>to</code> points in a least squares fashion
     */
    public Matrix3d getTransform() {
        return transform;
    }

    /**
     * Transform a point by a matrix,  Here because javax.vecmath is defective.
     * The point is treated as if it were homogenous with homogenous coordinate 1.
     *
     * @param mx  The transform matrix.
     * @param in  The input point.
     * @param out The transformed point.  Can be the same point object as the
     *            input point.
     */
    public static void transform(Matrix3d mx, Point2d in, Point2d out) {
        double px = mx.m00 * in.x + mx.m01 * in.y + mx.m02;
        double py = mx.m10 * in.x + mx.m11 * in.y + mx.m12;
        double w = mx.m20 * in.x + mx.m21 * in.y + mx.m22;
        px /= w;
        py /= w;
        out.set(px, py);
    }

    /**
     * Transform a point by this Procrustes transform.
     *
     * @param in  Input point.
     * @param out POutput point.  Can be the same object as the input point.
     */
    public void transform(Point2d in, Point2d out) {
        transform(getTransform(), in, out);
    }
    
    @Override
    public Point2d transform(Point2d p) {
        Point2d out = new Point2d();
        transform(p, out);
        return out;
    }

    private void compute(Point2d[] from, Point2d[] to, boolean isScale) {
        final int LEN = from.length;

        // Do the translation
        Point2d fromCenter = center(from);
        Point2d toCenter = center(to);
        for (int i = 0; i < LEN; i++) {
            from[i].sub(fromCenter);
            to[i].sub(toCenter);
        }

        // Compute the scale if it's required
        double scale = 1.0;
        if (isScale) {
            double fromScale = getSize(from);
            double toScale = getSize(to);
            scale = toScale / fromScale;
        }

        double theta = arg(from, to);
        double cos = Math.cos(theta);
        double sin = Math.sin(theta);
        Matrix3d r = new Matrix3d(cos, -sin, 0, sin, cos, 0, 0, 0, 1);

        Matrix3d from2center = identity3d();
        Matrix3d center2to = identity3d();
        // (A) translate points to origin
        fromCenter.negate();
        setTranslation(from2center, fromCenter);
        // (C) we have the rotationmatrix r already
        // (D) Translate to the toCenter
        setTranslation(center2to, toCenter);
        // Finally, combine the transformations
        transform.mul(center2to);
        transform.mul(scaleMatrix(scale));
        transform.mul(r);
        transform.mul(from2center);
    }

    private double arg(Point2d[] from, Point2d[] to) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < from.length; i++) {
            Point2d f = from[i];
            Point2d t = to[i];
            x += f.x * t.x + f.y * t.y;
            y += f.x * t.y - f.y * t.x;
        }
        return Math.atan2(y, x);
    }

    private Point2d center(Tuple2d[] tuples) {
        Point2d out = new Point2d();
        for (Tuple2d p : tuples) {
            out.add(p);
        }
        double scale = (tuples.length == 0) ? 1 : (1.0 / (double) tuples.length);
        out.scale(scale);
        return out;
    }

    static Point2d[] copy(Point2d[] in) {
        Point2d[] out = new Point2d[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = new Point2d(in[i]);
        }
        return out;
    }

    private double getSize(Point2d[] points) {
        Point2d zero = new Point2d(0, 0);
        double sum = 0f;
        for (Point2d point : points) {
            sum += zero.distance(point);
        }
        return sum / points.length;
    }

    private Matrix3d identity3d() {
        Matrix3d mx = new Matrix3d();
        mx.setIdentity();
        return mx;
    }

    private void setTranslation(Matrix3d mx, Tuple2d t) {
        mx.m02 = t.x;
        mx.m12 = t.y;
    }

    private Matrix3d scaleMatrix(double s) {
        return new Matrix3d(s, 0, 0, 0, s, 0, 0, 0, 1);
    }
}

