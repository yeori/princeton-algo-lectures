/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static final int DIR_X = 0;
    private static final int DIR_Y = 1;

    private Node root;
    private int size;
    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNN(p);
        if (root == null) {
            root = new Node(DIR_X, p);
            size++;
        } else {
            if(root.insert(p)) {
                size++;
            }
        }
    }

    private void checkNN(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        checkNN(p);
        if (root == null) {
            return false;
        }
        RectHV rect = new RectHV(p.x(), p.y(), p.x(), p.y());
        Iterable<Point2D> itr = range(rect);
        return itr.iterator().hasNext();
    }

    // draw all points to standard draw
    public void draw() {
        if (root != null) {
            root.draw(new int[]{'A'-1},0, 0, 1.0, 1.0);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNN(rect);
        List<Point2D> holder = new ArrayList<>();
        rangeSearch(rect, holder, root);
        return holder;
    }

    private void rangeSearch(RectHV rect, List<Point2D> holder, Node node) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            holder.add(node.p);
        }

        if (lessThan(rect, node)) {
            rangeSearch(rect, holder, node.nodeLT);
        }

        if (greaterThan(rect, node)) {
            rangeSearch(rect, holder, node.nodeGT);
        }

    }

    private static boolean lessThan(RectHV rect, Node node) {
        if (node.isDirX()) {
            return rect.xmin() <= node.p.x();
        } else  {
            return rect.ymin() <= node.p.y();
        }
    }

    private static boolean greaterThan(RectHV rect, Node node) {
        if (node.isDirX()) {
            return rect.xmax() >= node.p.x();
        } else {
            return rect.ymax() >= node.p.y();
        }
    }


    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNN(p);
        if (isEmpty()) {
            return null;
        }

        Nearest nrt = new Nearest();
        nrt.query = new RectHV(p.x(), p.y(), p.x(), p.y());
        nrt.nearest = null;
        nrt.distance = Double.POSITIVE_INFINITY;

        findNearest(nrt, root);
        return nrt.nearest.p;
    }

    private void findNearest(Nearest nrt, Node node) {
        if (node == null) {
            return;
        }

        double perpendicularDist = node.perpendicularDist(nrt.query);
        // if (nrt.distance < perpendicularDist ) {
        //     return;
        // }
        double dist = nrt.query.distanceTo(node.p);
        if (dist < nrt.distance) {
            nrt.distance = dist;
            nrt.nearest = node;
        }


        if (lessThan(nrt.query, node)) {
            // left(down) first
            findNearest(nrt, node.nodeLT);
            // check if GT need to be visited
            // double pdist = node.nodeGT == null ? Double.POSITIVE_INFINITY : node.nodeGT.perpendicularDist(nrt.query);
            if (perpendicularDist < nrt.distance) {
                findNearest(nrt, node.nodeGT);
            }

        } else {
            // right(up) first
            findNearest(nrt, node.nodeGT);
            // check if LT need to be visited
            // double pdist = node.nodeLT == null ? Double.POSITIVE_INFINITY : node.nodeLT.perpendicularDist(nrt.query);
            if (perpendicularDist < nrt.distance) {
                findNearest(nrt, node.nodeLT);
            }
            // if (perpendicularDist < nrt.distance ) {
            // }
        }
    }

    private static class Nearest {
        RectHV query;
        Node nearest;
        double distance;
    }
    private static class Node {
        private int direction; // DIR_X(0) or DIR_Y(1)
        private Point2D p;
        private Node nodeLT; // left or down
        private Node nodeGT; // right or up

        public Node(int direction, Point2D p) {
            this.direction = direction;
            this.p = p;
        }

        boolean insert(Point2D child) {
            if(p.equals(child)) {
                return false; // the point already exists
            }
            boolean lessThan;
            if (isDirX()) {
                lessThan = Double.compare(child.x(), this.p.x()) <= 0;
            } else {
                lessThan = Double.compare(child.y(), this.p.y()) <= 0;
            }

            int childDir = this.childDirection();
            boolean added = true;
            if (lessThan) {
                if (nodeLT == null) {
                    nodeLT = new Node(childDir, child);
                } else {
                    added = nodeLT.insert(child);
                }

                // insertTo(nodeLT, child);
            } else {
                if (nodeGT == null) {
                    nodeGT = new Node(childDir, child);
                } else {
                    added = nodeGT.insert(child);
                }
            }
            return added;
        }

        private int childDirection() {
            return isDirX() ? DIR_Y : DIR_X;
        }

        public void draw(int [] ch, double minX, double minY, double maxX, double maxY) {
            Color lineColor = isDirX() ? Color.RED : Color.BLUE;
            StdDraw.setPenColor(lineColor);
            StdDraw.setPenRadius(0.003);
            if (isDirX()) {
                StdDraw.line(p.x(), minY, p.x(), maxY);
            } else {
                StdDraw.line(minX, p.y(), maxX, p.y());
            }
            ch[0]++;
            char c = (char) ch[0];
            if (nodeLT != null) {
                if (direction == DIR_X) {
                    nodeLT.draw(ch, minX, minY, p.x(), maxY);
                } else {
                    nodeLT.draw(ch, minX, minY, maxX, p.y());
                }
            }
            if (nodeGT != null) {
                if (direction == DIR_X) {
                    nodeGT.draw(ch, p.x(), minY, maxX, maxY);
                } else {
                    nodeGT.draw(ch, minX, p.y(), maxX, maxY);
                }
            }
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(p.x(), p.y());
            StdDraw.setFont(new Font("Courier New", Font.PLAIN, 11));
            StdDraw.text(p.x(), p.y(), String.format("%s (%.3f, %.3f)", c, p.x(), p.y()));
        }

        public boolean isDirX() {
            return direction == DIR_X; // or (direction+1) % 2;
        }

        public boolean isDirY() {
            return direction == DIR_Y;
        }

        public double perpendicularDist(RectHV rect) {
            if (isDirX()) {
                return Math.abs(p.x() - rect.xmin());
            } else {
                return Math.abs(p.y() - rect.ymin());
            }
        }

        @Override
        public String toString() {
            return p.toString() + " DIR_" + (isDirX() ? 'X' : 'Y');
        }
    }

    public static void main(String[] args) {
    }

}
