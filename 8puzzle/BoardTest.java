import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class BoardTest {
    @Test
    public void test() {
        int [][] vals = vs(3,
                           "1 2 3 "
                             + "4 8 6 "
                             + "7 5 0");
        Board b = new Board(vals);
        assertEquals(2, b.hamming());
        assertEquals(2, b.manhattan());

        vals = vs(3, "8 1 3 4 0 2 7 6 5");
        b = new Board(vals);
        assertEquals(5, b.hamming());
        assertEquals(10, b.manhattan());

        vals = vs(3, "1 2 3 4 5 6 7 8 0");
        b = new Board(vals);
        assertTrue(b.isGoal());

    }

    @Test
    public void testTwinGeneration() {
        int [][] vals = vs(3,"1 2 3 4 5 6 7 8 0");
        Board b = new Board(vals);
        assertEquals(0, b.manhattan());
        for(int i = 0 ; i < 4000 ; i ++) {
            assertEquals(2, b.twin().manhattan());
        }
    }

    @Test
    public void testNeighbors() {
        int [][] vals = vs(3,"1 2 3 4 5 6 7 8 0");
        Board b = new Board(vals);
        /*
          1 2 3      1 2 3   1 2 3
          4 5 6  =>  4 5     4 5
          7 8        7 8 6 , 7   8
         */
        for(Board adj : b.neighbors()) {
            System.out.println(adj);
        }

        vals = vs(3, "1 2 3 4 0 5 6 7 8");
        b = new Board(vals);
        for(Board adj : b.neighbors()) {
            System.out.println(adj);
        }
    }

    private int[][] vs(int n, String val) {
        int [] v = Arrays.stream(val.split(" ")).mapToInt(s->Integer.parseInt(s)).toArray();

        int [][] arr = new int[n][n];
        for(int i = 0 ; i < v.length ; i ++) {
            int ir = i/n;
            int ic = i%n;
            arr[ir][ic] = v[i];
        }
        return arr;
    }
}