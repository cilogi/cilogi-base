// Copyright (c) 2000 Tim Niblett  ALL RIGHTS RESERVED.
//
// File:	Statistics.java  (8-May-00).
// Author:	Tim Niblett
// Version:	$Id: Statistics.java 4690 2010-05-05 22:17:44Z tim $
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
//

package com.cilogi.util;

import java.text.NumberFormat;

public class Statistics {

    protected static int defaultMaximumFractionDigits = 6;
    protected int n;
    protected double sum;
    protected double absSum;
    protected double var;
    protected double min;
    protected double max;
    /** No of fraction digits to display */
    protected int digits;

    public Statistics() {
        n = 0;
        sum = 0.0D;
        absSum = 0.0D;
        var = 0.0D;
        min = 0.0D;
        max = 0.0D;
        digits = defaultMaximumFractionDigits;
    }

    public Statistics(Statistics s) {
        this.n = s.n;
        this.sum = s.sum;
        this.absSum = s.absSum;
        this.var = s.var;
        this.min = s.min;
        this.max = s.max;
        this.digits = s.digits;
    }

    public Statistics(double[] vals) {
        this();
        for (int i = 0; i < vals.length; i++) {
            add(vals[i]);
        }
    }


    public synchronized static void setDefaultMaximumFractionDigits(int digits) {
        defaultMaximumFractionDigits = digits;
    }

    public synchronized static int getDefaultMaximumFractionalDigits() {
        return defaultMaximumFractionDigits;
    }

    public synchronized void setMaximumFractionDigits(int digits) {
        this.digits = digits;
    }

    public synchronized int getMaximumFractionalDigits() {
        return digits;
    }

    public synchronized double add(double val) {
        if (n == 0) {
            min = val;
            max = val;
        } else if (val < min) {
            min = val;
        } else if (val > max) {
            max = val;
        }
        sum += val;
        absSum += Math.abs(val);
        var += val * val;
        n++;
        return val;
    }

    public synchronized void add(Statistics s) {
        if(s.n == 0) {
            return;
        } else if(this.n == 0) {
            this.n = s.n;
            this.sum = s.sum;
            this.absSum = s.absSum;
            this.var = s.var;
            this.min = s.min;
            this.max = s.max;
            return;
        } else {
            n += s.n;
            sum += s.sum;
            absSum += s.absSum;
            var += s.var;
            min = s.min >= min ? min : s.min;
            max = s.max <= max ? max : s.max;
        }
    }

    public synchronized double getAbsMax() {
        double amax = Math.abs(max);
        double amin = Math.abs(min);
        return (amax > amin) ? amax : amin;
    }

    public synchronized double getMax() {
        return max;
    }

    public synchronized double getSum() {
        return sum;
    }

    public synchronized double getAbsSum() {
        return absSum;
    }

    public synchronized double getMean() {
        return n != 0 ? sum / (double)n : 0.0D;
    }

    public synchronized double getAbsMean() {
        return n != 0 ? absSum / (double)n : 0.0D;
    }

    public synchronized double getMin() {
        return min;
    }

    public synchronized int size() {
        return n;
    }

    public synchronized double getVariance() {
        double m = getMean();
        return (n > 0) ? ((var / (double)n) - (m * m)) : 0.0D;
    }

    public synchronized double getAbsVariance() {
        double m = getAbsMean();
        return (n > 0) ? ((var / (double)n) - (m * m)) : 0.0D;
    }

    public synchronized double getSD() {
        double var = getVariance();
        return (var <= 0.0d) ? 0.0d : Math.sqrt(getVariance());
    }

    public synchronized double getAbsSD() {
        double var = getAbsVariance();
        return (var <= 0.0d) ? 0.0d : Math.sqrt(getAbsVariance());
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Statistics)) {
            return false;
        }
        Statistics other = (Statistics)obj;
        if(this.n != other.n) {
            return false;
        }
        if(this.sum != other.sum) {
            return false;
        }
        if(this.absSum != other.absSum) {
            return false;
        }
        if(this.var != other.var) {
            return false;
        }
        if(this.min != other.min) {
            return false;
        }
        if(this.max != other.max) {
            return false;
        }
        return true;
    }
    
    public synchronized String toString() {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(digits);

        return "(mean: " + f.format(getMean())
                + " absMean: " + f.format(getAbsMean())
                + " sd: " + f.format(getSD())
                + " variance: " + f.format(getVariance())
                + " sum: " + f.format(getSum())
                + " min: " + f.format(min)
                + " max: " + f.format(max)
                + " num values: " + size() + ")";
    }
}
