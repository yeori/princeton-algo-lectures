import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class PointTest {

    @org.junit.Test
    public void case01() {
        /*
         * slopeTo(), where p and q have coordinates in [0, 500)
             Failed on trial 1 of 100000
             p                        = (305, 491)
             q                        = (115, 65)
             student   p.slopeTo(q) = 0.4460093896713615
             reference p.slopeTo(q) = 2.2421052631578946
         */
        Point p = new Point(305, 491);
        Point q = new Point(115, 65);
        double slope = 1.D*(491-65)/(305-115);
        System.out.println(slope);
    }

    @org.junit.Test
    public void testArrayImmutability() {
        List<String> list = Arrays.asList("A", "B", "C");
        String [] arr = new String[list.size()];
        list.toArray(arr);
        System.out.println(Arrays.toString(arr));
        arr[1] = "X";

        String [] cloned = new String[list.size()];
        list.toArray(cloned);
        System.out.println(Arrays.toString(cloned));
    }

    @org.junit.Test
    public void tc01() {
        Point [] ps = {
            new Point(4,4),
            new Point(2,4),
            new Point(3,3),
            new Point(8,4),
            new Point(0,0),
            new Point(6,6),
            new Point(1,1),
        };
        Comparator<Point> slopeSorter = ps[0].slopeOrder();
        Arrays.sort(ps, slopeSorter);
        for (int i = 0; i < ps.length; i++) {
            System.out.println(ps[i].toString());
        }
        // assertTrue ( ps[0].slopeTo(ps[1]) == ps[4].slopeTo(ps[0]));
        // FastCollinearPoints fc = new FastCollinearPoints(ps);
        // assertEquals(1, fc.numberOfSegments());
    }

    @org.junit.Test
    public void testYXSorter() {
        Point [] ps = {
            new Point(0,0),
            new Point(2,2),
            new Point(1,3),
            new Point(1,1),
            new Point(3,1),
            new Point(4, 4),
        };
        /*
        Point base = ps[1]; // (2, 2)
        FastCollinearPoints.YXSorter sorter = new FastCollinearPoints.YXSorter(base);
        Arrays.sort(ps, sorter);
        for (int i = 0; i < ps.length; i++) {
            System.out.println(ps[i].toString());
        }
         */
        FastCollinearPoints fs = new FastCollinearPoints(ps);
        fs.segments();
    }

}