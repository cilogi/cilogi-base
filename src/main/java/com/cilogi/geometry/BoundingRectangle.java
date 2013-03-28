// Copyright (c) 2012 Tim Niblett. All Rights Reserved.
//
// File:        BoundingRectangle.java  (12/04/12)
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

import org.apache.log4j.Logger;

import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;

public class BoundingRectangle {
    static final Logger LOG = Logger.getLogger(BoundingRectangle.class);

    public static final int N_BOUNDARY_POINTS = 4;

    public static final int X = 0;
    public static final int Y = 1;

    private double minx;
    private double miny;

    private double maxx;
    private double maxy;

    public static final BoundingRectangle INFINITE_BOX =
            new BoundingRectangle(
                    new Point2d(Double.NEGATIVE_INFINITY,
                            Double.NEGATIVE_INFINITY),
                    new Point2d(Double.POSITIVE_INFINITY,
                            Double.POSITIVE_INFINITY));


    public BoundingRectangle() {
        minx = miny = Double.POSITIVE_INFINITY;
        maxx = maxy = Double.NEGATIVE_INFINITY;
    }

    public BoundingRectangle(BoundingRectangle b) {
        minx = b.minx;
        miny = b.miny;
        maxx = b.maxx;
        maxy = b.maxy;
    }

    public BoundingRectangle(Point2d min, Point2d max) {
        minx = min.x;
        miny = min.y;
        maxx = max.x;
        maxy = max.y;
    }

    public BoundingRectangle(double minx, double miny,
                             double maxx, double maxy) {
        this.minx = minx;
        this.miny = miny;
        this.maxx = maxx;
        this.maxy = maxy;
    }

    public void max(Point2d p) {
        p.set(maxx, maxy);
    }

    public void min(Point2d p) {
        p.set(minx, miny);
    }
    
    public void mid(Point2d p) {
        p.set((minx+maxx)/2.0, (miny+maxy)/2.0);
    }

    public void boundary(int which, Point2d p) {
        if (which < 0 || which > 3) {
            throw new IllegalArgumentException("bounds must be 0-3 inclusive, not " + which);
        }
        p.x = ((which & 1) == 0) ? minx : maxx;
        p.y = ((which & 2) == 0) ? miny : maxy;
    }

    public double max(int dim) {
        return get(dim, true);
    }

    public double min(int dim) {
        return get(dim, false);
    }

    public double get(int dim, boolean max) {
        switch (dim) {
            case X:
                return max ? maxx : minx;
            case Y:
                return max ? maxy : miny;
            default:
                assert false;
                return Double.NaN;
        }
    }

    private void set(int dim, boolean max, double val) {
        switch (dim) {
            case X:
                if (max) maxx = val; else minx = val;
                break;
            case Y:
                if (max) maxy = val; else miny = val;
                break;
            default:
                assert false;
        }
    }

    public BoundingRectangle split(int dim, double val, boolean isLess) {
        if (dim < X || dim > Y) {
            throw new IllegalArgumentException("bas dimension, " + dim);
        }
        BoundingRectangle out = new BoundingRectangle(this);
        double min = out.get(dim, false);
        double max = out.get(dim, true);
        if (min <= val || max >= val) {
            if (isLess) {
                out.set(dim, true, val);
            } else {
                out.set(dim, false, val);
            }
        }
        return out;
    }

    public BoundingRectangle union(BoundingRectangle bbox) {
        BoundingRectangle b = new BoundingRectangle(this);
        if (bbox.minx < b.minx) b.minx = bbox.minx;
        if (bbox.miny < b.miny) b.miny = bbox.miny;
        if (bbox.maxx > b.maxx) b.maxx = bbox.maxx;
        if (bbox.maxy > b.maxy) b.maxy = bbox.maxy;
        return b;
    }

    public BoundingRectangle intersection(BoundingRectangle bbox) {
        BoundingRectangle b = new BoundingRectangle(this);
        if (bbox.minx > b.minx) b.minx = bbox.minx;
        if (bbox.miny > b.miny) b.miny = bbox.miny;
        if (bbox.maxx < b.maxx) b.maxx = bbox.maxx;
        if (bbox.maxy < b.maxy) b.maxy = bbox.maxy;
        return b;
    }
    
    public boolean overlaps(BoundingRectangle bbox) {
        BoundingRectangle intersect = intersection(bbox);
        return intersect.maxx >= intersect.minx && intersect.maxy >= intersect.miny;
    }

    /**
     * Intersection of this box with another.
     * @param bbox The box this is intersected with
     * @param out The resultant intersection of this box and bbox.
     */
    public void intersection(BoundingRectangle bbox, BoundingRectangle out) {
        out.minx = (bbox.minx > minx) ? bbox.minx : minx;
        out.miny = (bbox.miny > miny) ? bbox.miny : miny;
        out.maxx = (bbox.maxx < maxx) ? bbox.maxx : maxx;
        out.maxy = (bbox.maxy < maxy) ? bbox.maxy : maxy;
    }

    public boolean contains(Point2d p) {
        return p.x >= minx && p.x <= maxx &&
                p.y >= miny && p.y <= maxy;

    }

    /**
     * Find the closest point in the box or on its surface to a point
     * @param p A point in space
     * @param closest The closest point in or on the box to the point.
     */
    public void closest(Point2d p, Point2d closest) {
        closest.set(p);
        if (p.x < minx) {
            closest.x = minx;
        } else if (p.x > maxx) {
            closest.x = maxx;
        }
        if (p.y < miny) {
            closest.y = miny;
        } else if (p.y > maxy) {
            closest.y = maxy;
        }
    }

    /** Distance to the box from a point */
    public double distance(Point2d p) {
        double xdist = 0;
        double ydist = 0;
        if (p.x < minx) {
            xdist = p.x - minx;
        } else if (p.x > maxx) {
            xdist = p.x - maxx;
        }
        if (p.y < miny) {
            ydist = p.y - miny;
        } else if (p.y > maxy) {
            ydist = p.y - maxy;
        }

        return Math.sqrt(xdist * xdist + ydist * ydist);
    }

    public boolean intersectsSphere(Point2d center, double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("radiuss must be non-negative, not " + radius);
        }
        double rsq = radius * radius;
        Point2d p = new Point2d(minx, miny);
        if (p.distanceSquared(center) <= rsq) {
            return true;
        }
        for (int x = 0; x < 2; x++) {
            p.x = (x == 0) ? minx : maxx;
            for (int y = 0; y < 2; y++) {
                p.y = (y == 0) ? miny : maxy;
                if (p.distanceSquared(center) < rsq) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addPoint(Tuple2d p) {
        if (minx > p.x) minx = p.x;
        if (miny > p.y) miny = p.y;
        if (maxx < p.x) maxx = p.x;
        if (maxy < p.y) maxy = p.y;
    }

    public Point2d getMin() {
        return new Point2d(minx, miny);
    }

    public Point2d getMax() {
        return new Point2d(maxx, maxy);
    }

    public Point2d getCenter() {
        Point2d center = new Point2d(minx + maxx, miny + maxy);
        center.scale(0.5);
        return center;
    }

    public void getCenter(Tuple2d p) {
        p.set((minx + maxx) / 2, (miny + maxy) / 2);
    }

    public final double getDiagonalLength() {
        double dx = maxx - minx;
        double dy = maxy - miny;

        return 0.5 * Math.sqrt(dx * dx + dy * dy);
    }

    public double xRadius() {
        return 0.5 * (maxx - minx);
    }

    public double yRadius() {
        return 0.5 * (maxy - miny);
    }

    public boolean equals(Object o) {
        if (o instanceof BoundingRectangle) {
            BoundingRectangle b = (BoundingRectangle)o;
            return b.minx == minx && b.miny == miny &&
                    b.maxx == maxx && b.maxy == maxy;
        }
        return false;
    }

    public int hashCode() {
        long bits = (((Double.doubleToLongBits(minx) * 31 +
                Double.doubleToLongBits(miny)) * 31 +
                Double.doubleToLongBits(maxx)) * 31 +
                Double.doubleToLongBits(maxy));
        return (int)(bits ^ (bits >>> 32));
    }


    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("((");
        buf.append(minx);
        buf.append(",");
        buf.append(miny);

        buf.append(") -> (");
        buf.append(maxx);
        buf.append(",");
        buf.append(maxy);

        buf.append("))");
        return buf.toString();
    }

    public BoundingRectangle expand(double fraction) {
        Point2d center = getCenter();
        Point2d min = getMin();
        Point2d max = getMax();
        Point2d diff = new Point2d();
        diff.sub(max, min);
        diff.scale(0.5 * fraction);
        max.add(center, diff);
        min.sub(center, diff);
        return new BoundingRectangle(min, max);
    }
}
