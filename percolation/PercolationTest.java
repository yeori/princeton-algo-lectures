import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationTest {

    @Test
    public void test_toUfIndex () {
        int n = 3;
        /*
          T
        x x x x
        x . . .
        x . . .
        x . . .
          B
         */
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(1+n+1);
        int row = 2, col = 1;
        // 2 * 4 + 1
        Percolation pc = new Percolation(n);
//        assertEquals(1, pc.toUfIndex(1, 1));
//        assertEquals(4, pc.toUfIndex(2, 1));

    }
    @org.junit.Test
    public void test_open() {
        Percolation pc = new Percolation(3);
        pc.open(1,1);
        /*
         *  T T T
         *  -----
         *  O . .  open(1,1)
         *  . . .
         *  . . .
         *  -----
         *  B B B
         *
         */
        assertTrue(pc.isOpen(1,1));
        assertTrue(pc.isFull(1, 1));

        pc.open(3,3);
        /*
         *  T T T
         *  -----
         *  O . .
         *  . . .
         *  . . E open(3,3)
         *  -----
         *  B B B
         */
        assertTrue(pc.isOpen(3,3));
        assertFalse(pc.isFull(3, 3));

        pc.open(2,2);
        /*
         *  T T T
         *  -----
         *  O . .
         *  . E . open(2,2)
         *  . . E
         *  -----
         *  B B B
         */
        assertTrue(pc.isOpen(2,2));
        assertFalse(pc.isFull(2, 2));
        assertFalse(pc.percolates());

        pc.open(2, 1);
        /*
         *  T T T
         *  -----
         *  O . .
         *  O O . open(2,1)
         *  . . E
         *  -----
         *  B B B
         */
        assertTrue(pc.isOpen(2,1));
        assertTrue(pc.isFull(2, 1));
        assertTrue(pc.isFull(2, 2));
        assertFalse(pc.percolates());

        pc.open(2,3);
        /*
         *  T T T
         *  -----
         *  O . .
         *  O O O open(2,3)
         *  . . O
         *  -----
         *  B B B
         */
        assertTrue(pc.isFull(2,3));
        assertTrue(pc.isFull(3,3));
        assertTrue(pc.percolates());


    }

    @Test
    public void test_input4() {
        Percolation pc = new Percolation(4);
        pc.open(4,1);
        /*
        T T T T
        . . . .
        . . . .
        . . . .
        E . . .
        B B B B
        */
        assertEmpty(pc,4, 1);

        pc.open(3,1);
        pc.open(2,1);
        /*
        T T T T
        . . . .
        E . . .
        E . . .
        E . . .
        B B B B
        */
        assertEmpty(pc, 4,1, 3, 1, 2,1 );

        pc.open(1,1);
        /*
        T T T T
        O . . .
        O . . .
        O . . .
        O . . .
        B B B B
        */
        assertFull(pc, 4,1, 3, 1, 2,1 );
        assertTrue(pc.percolates());


    }

    static void assertEmpty(Percolation pc, int ... ps) {
        int len = ps.length;
        for (int i = 0 ; i < len ; i += 2) {
            int r = ps[i];
            int c = ps[i+1];
            assertTrue(String.format("not opened at(%d,%d)", r, c), pc.isOpen(r, c));
            assertFalse(String.format("expected not full, but at(%d,%d)", r, c), pc.isFull(r, c));
        }
    }

    static void assertFull(Percolation pc, int ... ps) {
        int len = ps.length;
        for (int i = 0 ; i < len ; i += 2) {
            int r = ps[i];
            int c = ps[i+1];
            assertTrue(String.format("not opened at(%d,%d)", r, c), pc.isOpen(r, c));
            assertTrue(String.format("not full at(%d,%d)", r, c), pc.isFull(r, c));
        }
    }
}