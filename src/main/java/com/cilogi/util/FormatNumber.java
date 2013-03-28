// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        FormatNumber.java  (09/07/12)
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


package com.cilogi.util;

import javax.vecmath.*;
import java.text.NumberFormat;
import java.text.ParseException;

@SuppressWarnings({"HardcodedLineSeparator"})
public class FormatNumber {

    private static final ThreadLocal<NumberFormat> ftl = new ThreadLocal<NumberFormat>() {
        protected NumberFormat initialValue() {
            NumberFormat f = NumberFormat.getInstance();
            f.setGroupingUsed(false);
            return f;
        }
    };

    private static NumberFormat f() {
        return ftl.get();
    }

    /**
     * Can't instantiate class
     */
    private FormatNumber() {
    }


    public static double parseDouble(String s) {
        try {
            Number n = f().parse(s);
            return n.doubleValue();
        } catch (ParseException e) {
            return Double.NaN;
        }
    }

    public static String fmt(double d, int frac) {
        f().setMaximumFractionDigits(frac);
        return Double.isNaN(d) ? "NaN" : f().format(d);
    }

    public static String fmt(int[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(data[i]);
            if (i < data.length - 1) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

    public static String fmt(double[] data, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(Double.isNaN(data[i]) ? "NaN" : f().format(data[i]));
            if (i < data.length - 1) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

    public static String fmt(float[] data, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(f().format(data[i]));
            if (i < data.length - 1) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

    public static String fmt(float number, int frac) {
        f().setMaximumFractionDigits(frac);
        return f().format(number);
    }

    public static String fmt(Tuple3d tup, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        buf.append("(");
        buf.append(f().format(tup.x));
        buf.append(',');
        buf.append(f().format(tup.y));
        buf.append(',');
        buf.append(f().format(tup.z));
        buf.append(")");
        return buf.toString();
    }

    public static String fmt(Tuple2d tup, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        buf.append(f().format(tup.x));
        buf.append(',');
        buf.append(f().format(tup.y));
        buf.append(')');
        return buf.toString();
    }

    public static String fmt(Tuple2d[] data, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(f().format(data[i].x));
            buf.append(' ');
            buf.append(f().format(data[i].y));
            if (i < data.length - 1) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }

    public static String fmt(Matrix4d mx, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        buf.append(f().format(mx.m00));
        buf.append(',');
        buf.append(f().format(mx.m01));
        buf.append(',');
        buf.append(f().format(mx.m02));
        buf.append(',');
        buf.append(f().format(mx.m03));
        buf.append('\n');

        buf.append(f().format(mx.m10));
        buf.append(',');
        buf.append(f().format(mx.m11));
        buf.append(',');
        buf.append(f().format(mx.m12));
        buf.append(',');
        buf.append(f().format(mx.m13));
        buf.append('\n');

        buf.append(f().format(mx.m20));
        buf.append(',');
        buf.append(f().format(mx.m21));
        buf.append(',');
        buf.append(f().format(mx.m22));
        buf.append(',');
        buf.append(f().format(mx.m23));
        buf.append('\n');

        buf.append(f().format(mx.m30));
        buf.append(',');
        buf.append(f().format(mx.m31));
        buf.append(',');
        buf.append(f().format(mx.m32));
        buf.append(',');
        buf.append(f().format(mx.m33));
        buf.append('\n');

        return buf.toString();
    }

    public static String fmt(Tuple3f tup, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        buf.append(f().format(tup.x));
        buf.append(',');
        buf.append(f().format(tup.y));
        buf.append(',');
        buf.append(f().format(tup.z));
        buf.append(')');
        return buf.toString();
    }

    public static String fmt(Tuple2f tup, int frac) {
        f().setMaximumFractionDigits(frac);
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        buf.append(f().format(tup.x));
        buf.append(',');
        buf.append(f().format(tup.y));
        buf.append(')');
        return buf.toString();
    }
}

