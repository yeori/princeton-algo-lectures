import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SolverTest {

    @Test
    public void test00() {
        Board b = board(3, "0 1 3 4 2 5 7 8 6");
        assertEquals(4, b.manhattan());

        Solver solver = new Solver(b);
        assertTrue(solver.isSolvable());

    }

    private Board board(int n, String nums) {
        int [] v = Arrays.stream(nums.split(" ")).mapToInt(s->Integer.parseInt(s)).toArray();

        int [][] arr = new int[n][n];
        for(int i = 0 ; i < v.length ; i ++) {
            int ir = i/n;
            int ic = i%n;
            arr[ir][ic] = v[i];
        }

        return new Board(arr);
    }
}