// Copyright (c) 2000 Tim Niblett  ALL RIGHTS RESERVED.
//
// File:	LeastSquaresColt.java  (12-Sep-01).
// Author:	Tim Niblett
// Version:	$$
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (The Author) and may not be used,
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


package com.cilogi.algebra.linear;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.QRDecomposition;
import org.apache.log4j.Logger;

/**
 * Least squares via QR decoposition.  Uses Colt library
 */
public class LeastSquaresColt {
    static final Logger LOG =
            Logger.getLogger(LeastSquaresColt.class);

    private double conditionNumberLimit = 1e38;

    /**
     * Can't create instances
     */
    public LeastSquaresColt() {
        this(1e38);
    }

    public LeastSquaresColt(double conditionNumberLimit) {
        if (conditionNumberLimit <= 0) {
            throw new IllegalArgumentException("condition number must be > 0, not " + conditionNumberLimit);
        }
        this.conditionNumberLimit = conditionNumberLimit;
    }

    /**
     * Solve for x in a x = b
     */
    public static void solve(double[][] a, double[] x, double[] b) {
        LeastSquaresColt ls = new LeastSquaresColt();
        ls.solveEquation(a, x, b);
    }

    public void solveEquation(double[][] a, double[] x, double[] b) {
        check(a, x, b);
        DenseDoubleMatrix2D A = new DenseDoubleMatrix2D(a);
        QRDecomposition qr = new QRDecomposition(A);
        DoubleMatrix2D X = null;
        try {
            X = qr.solve(createB(b));
        } catch (IllegalArgumentException e) {
            LOG.warn("Can't solve equation");
            throw e;
        }
        if (isBadlyConditioned(qr)) {
            throw new IllegalArgumentException("badly conditioned matrix, condition number is "
                    + conditionNumber(qr) + " limit is " + conditionNumberLimit);
        }
        recover(X, x);
    }

    private boolean isBadlyConditioned(QRDecomposition qr) {
        return conditionNumber(qr) > conditionNumberLimit;
    }

    private double conditionNumber(QRDecomposition qr) {
        DoubleMatrix2D r = qr.getR();
        Algebra alg = new Algebra();
        double conditionNumber = alg.cond(r);
        return conditionNumber;
    }

    private static void check(double[][] a, double[] x, double[] b) {
        if (a == null) throw new NullPointerException("A matrix is null");
        if (b == null) throw new NullPointerException("b vector is null");
        if (x == null) throw new NullPointerException("x vector is null");
        int nRows = a.length;
        if (nRows == 0) throw new IllegalArgumentException("A matrix has no rows");
        if (a[0] == null) throw new NullPointerException("A matrix has row " + 0 + " null");
        int nCols = a[0].length;
        if (nCols == 0) throw new IllegalArgumentException("A matrix has no cols");
        for (int i = 0; i < nRows; i++) {
            if (a[i] == null) throw new NullPointerException("A matrix has row " + i + " null");
            if (a[i].length != nCols) {
                throw new IllegalArgumentException("A matrix has row " + i + " of length " +
                        a[i].length + ", when it should be " +
                        nCols);
            }
        }
        if (x.length != nCols) throw new IllegalArgumentException("x has length " +
                x.length + " not " +
                nCols);
        if (b.length != nRows) throw new IllegalArgumentException("b has length " +
                b.length + " not " +
                nRows);
    }

    private static DenseDoubleMatrix2D createB(double[] b) {
        DenseDoubleMatrix2D mx = new DenseDoubleMatrix2D(b.length, 1);
        for (int i = 0; i < b.length; i++) {
            mx.setQuick(i, 0, b[i]);
        }
        return mx;
    }

    private static void recover(DoubleMatrix2D X, double[] x) {
        for (int i = 0; i < x.length; i++) {
            x[i] = X.getQuick(i, 0);
        }
    }
}
