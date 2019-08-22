/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final Point[] points;
    private List<LineSegment> lines;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
           throw new IllegalArgumentException();
        }
        if (points[0] == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int k = i+1; k < points.length; k++) {
                if (points[k] == null) {
                    throw new IllegalArgumentException();
                }
                if (points[i].compareTo(points[k]) == 0) {
                    // same points
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] copied = new Point[points.length];
        System.arraycopy(points, 0, copied, 0, points.length);
        this.points = copied;
    }
    
    // the number of line segments
    public LineSegment[] segments()  {
        if (lines != null) {
            LineSegment [] clone = new LineSegment[lines.size()];
            lines.toArray(clone);
            return clone;
        }
        Arrays.sort(this.points);
        List<LineSegment> lineSegs = new ArrayList<>();
        for (int p = 0; p < points.length; p++) {
            for (int q = p+1; q < points.length; q++) {
                double slopePQ = points[p].slopeTo(points[q]);
                for (int r = q+1; r < points.length; r++) {
                    double slopeQR = points[q].slopeTo(points[r]);
                    if (slopePQ != slopeQR) {
                        continue;
                    }
                    for (int s = r+1; s < points.length; s++) {
                        double slopeRS = points[r].slopeTo(points[s]);
                        if (slopeRS == slopeQR) {
                            lineSegs.add(new LineSegment(points[p], points[s]));
                        }
                    }
                }
            }
        }
        this.lines = lineSegs;
        LineSegment [] arr = new LineSegment[lineSegs.size()];
        lineSegs.toArray(arr);
        return arr;
    }
    // the line segments
    public int numberOfSegments() {
        if (lines == null) {
            segments();
        }
        return lines.size();
    }
}
