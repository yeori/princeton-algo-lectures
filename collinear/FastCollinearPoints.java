/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private final Point[] points;
    // private LineSegment [] lines;
    private List<LineSegment> lines;
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
           throw new IllegalArgumentException();
        }
        if(points[0] == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int k = i+1; k < points.length; k++) {
                if (points[k]== null) {
                    throw new IllegalArgumentException();
                }
                if (points[i].compareTo(points[k]) == 0) {
                    // same points
                    throw new IllegalArgumentException();
                }
            }
        }
        // this.points = points;
        Point[] copied = new Point[points.length];
        System.arraycopy(points, 0, copied, 0, points.length);
        this.points = copied;
    }
    
    // the number of line segments
    public LineSegment[] segments()  {
        if (this.lines != null) {
            LineSegment [] clone = new LineSegment[lines.size()];
            lines.toArray(clone);
            return clone;
        }
        // Comparator<Point> sortByYX = new YXSorter();
        List<LineSegment> lines = new ArrayList<>();
        Point [] ps = new Point[points.length];
        System.arraycopy(points, 0, ps, 0, points.length);
        double [] slopes = new double[points.length];

        for (int i = 0; i < points.length; i++) {
            Point base = points[i];

            YXSorter sorter = new YXSorter(base);
            Arrays.sort(ps, sorter);

            // init slopes
            for (int k = 0; k < ps.length; k++) {
                slopes[k] = base.slopeTo(ps[k]);
            }

            // [base, P1, P2, P3, ... , Pn-1]
            int k = 1;
            while (k < ps.length) {
                int len = countSameSlops(slopes, k);
                if (len + 1 >= 4 && base.compareTo(ps[k]) < 0) {
                    // 1. len + 1(plus base)
                    // 2. base should be a lead point
                    lines.add(new LineSegment(base, ps[k+len-1]));
                }
                k += len;
            }
        }
        this.lines = lines;
        LineSegment [] arr = new LineSegment[lines.size()];
        lines.toArray(arr);
        return arr;
    }

    private int countSameSlops(double[] slopes, int offset) {
        double value = slopes[offset];
        int cnt = 0;
        for (int i = offset; i < slopes.length; i++) {
            if (value == slopes[i]) {
                cnt++;
            } else {
                break;
            }
        }
        return cnt;
    }

    private static class YXSorter implements Comparator<Point> {
        private Comparator<Point> slopeSorter;

        private YXSorter(Point base) {
            slopeSorter = base.slopeOrder();
        }

        @Override
        public int compare(Point o1, Point o2) {
            int order = slopeSorter.compare(o1, o2);
            if (order != 0) {
                return order;
            } else {
                return o1.compareTo(o2);
            }
        }
    }
    // the line segments
    public int numberOfSegments() {
        if (lines == null) {
            segments();
        }
        return lines.size();
    }
}
