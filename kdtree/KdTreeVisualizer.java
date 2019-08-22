/******************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeVisualizer {

    public static void main(String[] args) {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.enableDoubleBuffering();
        KdTree kdtree = new KdTree();
        // kdtree.insert(new Point2D(0.5, 0.5));
        // kdtree.insert(new Point2D(0.25, 0.25));
        // kdtree.insert(new Point2D(0.75, 0.8));
        // kdtree.insert(new Point2D(0.4, 0.65));
        // PointSET kdtree = new PointSET();
        Point2D recent = null;
        System.setIn(KdTree.class.getResourceAsStream("input10.txt"));
        Runnable input = () -> {
            while(true) {
                StdOut.print("loc(x y): ");
                String line = StdIn.readLine().trim();
                if ("q".equals(line)){
                    break;
                }
                String [] xy = line.split(" ");
                double x= Double.parseDouble(xy[0]);
                double y = Double.parseDouble(xy[1]);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
                StdDraw.clear();
                kdtree.draw();
                StdDraw.show();
            }
            StdOut.println("good bye!");
        };
        Thread t = new Thread(input);
        t.start();
        /*
        while (true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                // StdOut.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p) && !p.equals(recent)) {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    recent = p;
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }

            }
            // kdtree.draw();
            // StdDraw.show();
            StdDraw.pause(40);
        }


         */


    }

}
