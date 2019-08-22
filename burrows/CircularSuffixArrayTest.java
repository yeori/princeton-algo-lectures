import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class CircularSuffixArrayTest {

    @Test
    public void abra_txt () {
        /*
         i       Original Suffixes           Sorted Suffixes         index[i]
        --    -----------------------     -----------------------    --------
         0    A B R A C A D A B R A !     ! A B R A C A D A B R A    11
         1    B R A C A D A B R A ! A     A ! A B R A C A D A B R    10
         2    R A C A D A B R A ! A B     A B R A ! A B R A C A D    7
         3    A C A D A B R A ! A B R     A B R A C A D A B R A !    0
         4    C A D A B R A ! A B R A     A C A D A B R A ! A B R    3
         5    A D A B R A ! A B R A C     A D A B R A ! A B R A C    5
         6    D A B R A ! A B R A C A     B R A ! A B R A C A D A    8
         7    A B R A ! A B R A C A D     B R A C A D A B R A ! A    1
         8    B R A ! A B R A C A D A     C A D A B R A ! A B R A    4
         9    R A ! A B R A C A D A B     D A B R A ! A B R A C A    6
        10    A ! A B R A C A D A B R     R A ! A B R A C A D A B    9
        11    ! A B R A C A D A B R A     R A C A D A B R A ! A B    2
         */
        String s = "ABRACADABRA!";
        CircularSuffixArray arr = new CircularSuffixArray(s);
        assertEquals(11, arr.index(0));
        assertEquals(10, arr.index(1));
        assertEquals(7, arr.index(2));
        assertEquals(0, arr.index(3));
        assertEquals(3, arr.index(4));
        assertEquals(5, arr.index(5));
        assertEquals(8, arr.index(6));
        assertEquals(1, arr.index(7));
        assertEquals(4, arr.index(8));
        assertEquals(6, arr.index(9));
        assertEquals(9, arr.index(10));
        assertEquals(2, arr.index(11));

    }

    @Test
    public void largeString() {
        /*
            B L I C A B L I C A
            i         j
              i         j
                i         j
                  i         j
                    i         j
            j         i

         */
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < 4096000 ; i++) {
            sb.append((char) StdRandom.uniform(256));
        }

        String in = sb.toString();
        long t = System.nanoTime();
        CircularSuffixArray arr = new CircularSuffixArray(in);

        t = System.nanoTime() - t;
        System.out.printf("%.3f secs", t / 1_000_000_000.0);
    }

    @Test
    public void comparingString() {
        Integer[] suffixes = new Integer[4096000];
        for(int i = 0 ; i < 4096000 ; i++) {
            suffixes[i] = StdRandom.uniform(256);
        }

        long t = System.nanoTime();
        Arrays.sort(suffixes, (a,b) -> a - b);
        t = System.nanoTime() - t;
        System.out.printf("%.3f secs", t / 1_000_000_000.0);

    }
}