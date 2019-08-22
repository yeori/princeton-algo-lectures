/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int dy = that.y - this.y;
        int dx = that.x - this.x;
        if (dx == 0 && dy == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if ( dy == 0) {
            return 0;
        } else if ( dx == 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return 1D*dy/dx;
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int ydiff= this.y - that.y;
        return ydiff != 0 ? ydiff : this.x - that.x;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new CompBySlope(this);
    }

    private static class CompBySlope implements Comparator<Point> {
        private final static int NEGATIVE_INF = -1;
        private final static int NOT_INFINITY =  0;
        private final static int POSITIVE_INF = +1;
        private final Point p0;

        CompBySlope (Point p) {
            p0 = p;
        }
        private int classify(double value) {
            if (value == Double.NEGATIVE_INFINITY) {
                return NEGATIVE_INF;
            } else if ( value == Double.POSITIVE_INFINITY) {
                return POSITIVE_INF;
            } else {
                return NOT_INFINITY;
            }
        }

        @Override
        public int compare(Point p1, Point p2) {
            /*
            n_inf, [valid], p_inf
               -1        0     +1

            -----  -----  -----  --------------
            p1     p2     p1-p2  ordering
            -----  -----  -----  --------------
            n_inf  n_inf      0  determined (p1 = p2)
            p_inf  p_inf      0  determined (p1 = p2)
            range  range      0  not determined
            n_inf  p_inf     -2  determined (p1 < p2)
            n_inf  range     -1  determined (p1 < p2)
            p_inf  n_inf     +2  determined (p1 > p2)
            p_inf  range     +1  determined (p1 > p2)
            range  n_inf     +1  determined (p1 > p2)
            range  p_inf     -1  determined (p1 < p2)
            -----  -----  -----  --------------

             */
            double slope1 = p0.slopeTo(p1);
            double slope2 = p0.slopeTo(p2);
            int c1 = classify(slope1);
            int c2 = classify(slope2);
            int classDiff = c1 - c2; // value of p1-p2
            if (classDiff != 0) {
                // class(p1) != class(p2)
                return classDiff;
            } else if (slope1 == Double.NEGATIVE_INFINITY) {
                // both are negative infs
                return 0;
            } else if (slope1 == Double.POSITIVE_INFINITY) {
                // both are positive infs
                return 0;
            } else {
                // both are not infnity, compare the slopes
                return Double.compare(slope1, slope2);
            }
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        /*
        StdDraw.setCanvasSize(600, 600);
        StdDraw.setScale(0.0, 600);

        int L = 1;
        for (int i = 0 ; i < 600 ; i +=3) {
            LineSegment seg = new LineSegment(new Point(0, 0), new Point(i, i));
            seg.draw();
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */
    }
}
