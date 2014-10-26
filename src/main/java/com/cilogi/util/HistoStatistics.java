// Copyright (c) 2000 Tim Niblett  ALL RIGHTS RESERVED.
//
// File:	HistoStatistics.java  (30-April-01).
// Author:	Tim Niblett
// Version:	$Id: HistoStatistics.java 4690 2010-05-05 22:17:44Z tim $ 
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

import lombok.EqualsAndHashCode;
import lombok.Synchronized;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

@EqualsAndHashCode(callSuper=true)
public class HistoStatistics extends Statistics {

    protected static final int DEFAULT_INITIAL_SIZE = 256;

    protected ArrayList<Double> vals;
    protected ArrayList<Double> copy;
    private boolean isSorted;

    public HistoStatistics() {
        this(DEFAULT_INITIAL_SIZE);
    }

    public HistoStatistics(int initialSize) {
        super();
        isSorted = false;
        vals = new ArrayList<Double>(initialSize);
    }

    @SuppressWarnings({"unchecked"})
    public HistoStatistics(HistoStatistics s) {
        super(s);
        isSorted = s.isSorted;
        vals = (ArrayList<Double>) s.vals.clone();
    }

    public HistoStatistics(double[] values) {
        super(values);
        isSorted = false;
    }

    @Synchronized
    public double add(double val) {
        return addNoSync(val);
    }

    protected double addNoSync(double val) {
        super.addNoSync(val);
        vals.add(val);
        isSorted = false;
        return val;
    }

    @Synchronized
    public void add(HistoStatistics s) {
        super.add(s);
        vals.addAll(s.vals);
        isSorted = false;
    }

    public double getMedian() {
        return quant(0.5);
    }

    @Synchronized
    private boolean isSorted() {
        return isSorted;
    }

    private int clip(int index) {
        if (index < 0) return 0;
        if (index > n - 1) return n - 1;
        return index;
    }

    /** Get the number of elements whose value is less than or equal to <code>v</code>.
     * @param v
     * @return number of elements whose value is less than or equal to <code>v</code>
     */
    @Synchronized
    public int nLE(double v) {
        return n - nGT(v);
    }
    
    @Synchronized
    public int nLT(double v) {
        return n - nGE(v);
    }

    @SuppressWarnings({"unchecked"})
    @Synchronized
    public int nGE(double v) {
        if (n == 0) {
            return 0;
        }
        if (!isSorted()) {
            copy = (ArrayList<Double>)vals.clone();
            Collections.sort(copy);
            isSorted = true;
        }
        if(copy.get(n-1) < v) {
            return 0;
        }
        if(v <= copy.get(0)) {
            return n;
        }
        // n > 1 must hold by now.
        return nGE(0, n-1, v);
    }
    
    private int nGE(int minIndex, int maxIndex, double v) {
        if(maxIndex - minIndex == 1) {
            return n - maxIndex;
        }
        int midIndex = (minIndex + maxIndex) / 2;
        double midV = copy.get(midIndex);
        if(midV < v) {
            minIndex = midIndex;
        } else {
            maxIndex = midIndex;
        }
        return nGE(minIndex, maxIndex, v);
    }

    @SuppressWarnings({"unchecked"})
    public int nGT(double v) {
        if (n == 0) {
            return 0;
        }
        if (!isSorted()) {
            copy = (ArrayList<Double>)vals.clone();
            Collections.sort(copy);
            isSorted = true;
        }
        if(copy.get(n-1) <= v) {
            return 0;
        }
        if(v < copy.get(0)) {
            return n;
        }
        // n > 1 must hold by now.
        return nGT(0, n-1, v);
    }
    
    private int nGT(int minIndex, int maxIndex, double v) {
        if(maxIndex - minIndex == 1) {
            return n - maxIndex;
        }
        int midIndex = (minIndex + maxIndex) / 2;
        double midV = copy.get(midIndex);
        if(midV <= v) {
            minIndex = midIndex;
        } else {
            maxIndex = midIndex;
        }
        return nGT(minIndex, maxIndex, v);
    }

    @SuppressWarnings({"unchecked"})
    @Synchronized
    public double quant(double q) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return vals.get(0);
        }
        if (!isSorted()) {
            copy = (ArrayList<Double>)vals.clone();
            Collections.sort(copy);
            isSorted = true;
        }
        if (q > 1.0d || q < 0.0d) {
            return 0;
        } else {
            double indexFloat = n * q - 1;
            int index = (int) Math.floor(indexFloat);
            double a = indexFloat - index;
            int lo = clip(index);
            int hi = clip(index+1);
            return (1-a) * copy.get(lo) + a * copy.get(hi);
        }
    }
    
    @Synchronized
    public double[] quantCentered(double q) {
        double half = (1.0-q)*0.5;
        return new double[]{quant(half), quant(1-half)};                
    }
    
    /** Gets a histogram of size <code>nBuckets</code>.
     * The domain of the histogram is evenly divided between the minimum and maximum of the data.
     * @param nBuckets number of buckets. Must be positive.
     * @return the histogram where double[0] is the domain and double[1] is the count.
     */
    @SuppressWarnings({"unchecked"})
    @Synchronized
    public double[][] histo(int nBuckets) {
        if(nBuckets <= 0) {
            throw new IllegalArgumentException("nBuckets must be positive but is " + nBuckets);
        }
        if (n == 0) {
            return null;
        }
        if (!isSorted()) {
            copy =(ArrayList<Double>) vals.clone();
            Collections.sort(copy);
            isSorted = true;
        }
        int size = copy.size();
        double min = copy.get(0);
        double max = copy.get(size-1);
        double interval = (max-min)/(double)nBuckets;
        double[] domain = new double[nBuckets];
        double[] range = new double[nBuckets];
        if(interval == 0.0d) {
            for (int i = 0; i < nBuckets; i++) {
                domain[i] = min;
                range[i] = i==0? size: 0;
            }
        } else {
            int lo = 0;
            int hi = 0;
            for (int i = 0; i < nBuckets; i++) {
                domain[i] = min + (i + 0.5)*interval;

                if(i==nBuckets-1) {
                    hi = size;
                } else {
                    double hiValue = min + (i + 1.0)*interval;
                    hi = findLoBound(hiValue, 0, size-1) + 1;
                }

                range[i] = hi-lo;
                lo = hi;
            }
        }
        return new double[][]{domain, range};
    }
    
    private int findLoBound(double v, int lo, int hi) {
        if(lo+1 == hi) {
            return lo;
        }
        int mid = (int)(Math.round((lo + hi) * 0.5));
        if(copy.get(mid) <= v) {
            return findLoBound(v, mid, hi);
        } else {
            return findLoBound(v, lo, mid);
        }
    }

    @Synchronized
    public final double get(int i) {
        return vals.get(i);
    }

    @Synchronized
    public String toString() {
        NumberFormat f = NumberFormat.getInstance();
        f.setMaximumFractionDigits(digits);

        return "(mean: " + f.format(getMean())
                + " absmean: " + f.format(getAbsMean())
                + " sd: " + f.format(getSD())
                + " median: " + f.format(getMedian())
                + " variance: " + f.format(getVariance())
                + " sum: " + f.format(getSum())
                + " min: " + f.format(min)
                + " max: " + f.format(max)
                + " num values: " + size() + ")";
    }
}
