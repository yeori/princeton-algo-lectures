import org.junit.Test;

import static org.junit.Assert.*;

public class PercolationBottom {

    @Test
    public void bottom() {
        Percolation pc = new Percolation(3);
        /*
        . . .
        . . .
        . . .
         */
        pc.open(1, 1);
        /*
        O . .
        . . .
        . . .
         */
        pc.open(3, 3);
        /*
        O . .
        . . .
        . . E
         */
        pc.open(2,1);
        pc.open(3,1);
        /*
        O . .
        O . .
        O . E
         */
        assertFalse(pc.isFull(3,3));
    }
}