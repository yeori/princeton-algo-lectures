
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNN(p);
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNN(p);
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNN(rect);
        ArrayList<Point2D> result = new ArrayList<>();
        for (Point2D each : points) {
            if (rect.contains(each)) {
                result.add(each);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNN(p);
        if (isEmpty()) {
            return null;
        }
        Point2D nearest = points.first();
        double minDist = p.distanceSquaredTo(nearest);
        for (Point2D dst : points) {
            if (p.distanceSquaredTo(dst) < minDist) {
                nearest = dst;
                minDist = p.distanceSquaredTo(dst);
            }
        }
        return nearest;
    }

    private void checkNN(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

    }
}
