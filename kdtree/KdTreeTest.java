import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Test;

import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class KdTreeTest {

    @Test
    public void test00() {
        KdTree  tree = tree("input1M.txt");
        RectHV rect = new RectHV(0.25, 0.3, 0.5, 0.7);
        List<Point2D> list = (List<Point2D>) tree.range(rect);
        System.out.println(list.size());
    }

    @Test
    public void test_contains() {
        KdTree  tree = tree("input00.txt");
        int size = tree.size();
        assertFalse(tree.contains(new Point2D(0,0)));
        assertTrue(tree.contains(new Point2D(0.7,0.2)));

        // existing point
        tree.insert(new Point2D(0.4, 0.7));
        assertEquals(size, tree.size());
    }

    @Test
    public void test_nearest() {
        KdTree  tree = tree("a00.txt");
        double dim = 10;
        Point2D query = new Point2D(5.5/dim, 3.5/dim);
        Point2D n = tree.nearest(query);
        System.out.println(n); // (5,4)

    }

    private KdTree tree(String fname) {
        Scanner sc= new Scanner(KdTreeTest.class.getResourceAsStream(fname), "utf-8");
        KdTree t = new KdTree();
        while (sc.hasNextLine()) {
            String [] xy = sc.nextLine().trim().split(" ");
            double x = Double.parseDouble(xy[0]);
            double y = Double.parseDouble(xy[1]);
            t.insert(new Point2D(x, y));
        }
        sc.close();
        return t;
    }


}