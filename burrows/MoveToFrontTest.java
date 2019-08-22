import org.junit.Test;

import java.util.Arrays;

/**
  j    Original Suffixes       |  i  Sorted Suffixes       t   index[i]  next[i]
 --    ----------------------- + -- ------------------------   --------
  0    A B R A C A D A B R A ! |  0  ! A B R A C A D A B R A    11
  1    B R A C A D A B R A ! A |  1  A ! A B R A C A D A B R    10
  2    R A C A D A B R A ! A B |  2  A B R A ! A B R A C A D     7
 *3    A C A D A B R A ! A B R | *3  A B R A C A D A B R A !    *0        7
  4    C A D A B R A ! A B R A |  4  A C A D A B R A ! A B R     3
  5    A D A B R A ! A B R A C |  5  A D A B R A ! A B R A C     5
 >6    D A B R A ! A B R A C A |  6  B R A ! A B R A C A D A     8
  7    A B R A ! A B R A C A D |  7  B R A C A D A B R A ! A     1       11
  8    B R A ! A B R A C A D A |  8  C A D A B R A ! A B R A     4
  9    R A ! A B R A C A D A B | >9  D A B R A ! A B R A C A     6        2
 10    A ! A B R A C A D A B R | 10  R A ! A B R A C A D A B     9
 11    ! A B R A C A D A B R A | 11  R A C A D A B R A ! A B     2        4
 --    ----------------------- + -- ------------------------   --------

 case) j == 6,
      index[i] == j, i := 9    #  If the j-th original suffix (original string, shifted j characters to the left) is the i-th row in the sorted order,
       next[i] := 7

  i  s  t   next[i]
  0  !  A    3
  1  A  R    0
  2  A  D    6
 *3  A  !    7
  4  A  R    8
  5  A  C    9
  6  B  A   10
  7  B  A   11
  8  C  A    5
  9  D  A    2
 10  R  B    1
 11  R  B    4

  3 !
  7 A
 11 B
  4 R
 */


public class MoveToFrontTest {

    @Test
    public void table() {

        for (int i = 0; i < 256; i++) {
            System.out.println("[" + (char) i + "] " + Integer.toHexString(i));
        }
    }

    @Test
    public void abra_txt() {

        /*
        ABRACADABRA!

         i  SUFFIX
        -- ------------
         0 ABRACADABRA!
         1 BRACADABRA!
         2 RACADABRA!
         3 ACADABRA!
         4 CADABRA!
         5 ADABRA!
         6 DABRA!
         7 ABRA!
         8 BRA!
         9 RA!
        10 A!
        11 !

         */
        // String s = "ABRACADABRA!"; // A5 B2 C1 D1 R2 !1
                                   // A5 B2 C1 D1 R2 !1
        // String s = "LA LA LAND AND LA DODGERS";
        String s = "DOWNTOWN BROWN";
        Object[][] origin = new Object[s.length()][3];
        for (int i = 0; i < s.length(); i++) {
            origin[i] = new Object[]{s.substring(i), s.substring(0, i), i}; // suffix, prefix, index
        }

        for (int i = 0; i < origin.length; i++) {
            System.out.printf("%d) %14s at %d\n", i, origin[i][0], ((Integer)origin[i][2]).intValue());
        }

        Arrays.sort(origin, (a,b) -> {
            String sa = (String)a[0];
            String sb = (String)b[0];
            return sa.compareTo(sb);
        });
        System.out.println("[AFTER]");
        for (int i = 0; i < origin.length; i++) {
            System.out.printf("%d) %s + %s at %d\n", i, origin[i][0], origin[i][1], ((Integer)origin[i][2]).intValue());
        }
    }
}