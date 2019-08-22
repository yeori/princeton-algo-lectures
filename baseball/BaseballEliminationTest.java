import org.junit.Test;

import static org.junit.Assert.assertEquals;
/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class BaseballEliminationTest {

    @Test
    public void testfile4() {
        /*
        4
        Atlanta       83 71  8  0 1 6 1
        Philadelphia  80 79  3  1 0 0 2
        New_York      78 78  6  6 0 0 0
        Montreal      77 82  3  1 2 0 0
         */
        BaseballElimination be = new BaseballElimination("teams4.txt");
        assertEquals(83, be.wins("Atlanta"));
        assertEquals(71, be.losses("Atlanta"));
        assertEquals(8, be.remaining("Atlanta"));

        assertEquals(78, be.wins("New_York"));
        assertEquals(78, be.losses("New_York"));
        assertEquals(6, be.remaining("New_York"));

        /* reamaining games btw teams*/
        assertEquals(6, be.against("Atlanta", "New_York"));
        assertEquals(6, be.against("New_York", "Atlanta"));

        be.isEliminated("Philadelphia");

    }

    @Test
    public void testfile4a () {
        /*
        4
        CIA       3 3 3 0 1 0 2
        Ghaddafi  2 5 2 1 0 0 1
        Bin_Ladin 3 6 0 0 0 0 0
        Obama     4 2 3 2 1 0 0

        Detroit     49 86 28   5  8 10  5 0
         */
        BaseballElimination be = new BaseballElimination("teams4a.txt");
        be.isEliminated("Obama");

    }

    @Test
    public void testfile5a () {
        /*
        5
        New_York    75 59 28   0 4  10  9 5
        Baltimore   71 63 28   4 0   5 11 8
        Boston      69 66 27  10 5   0  2 10
        Toronto     63 72 27   9 11  2  0 5
        Detroit     49 86 28   5  8 10  5 0
         */
        BaseballElimination be = new BaseballElimination("teams5a.txt");
        be.isEliminated("Boston");

    }

    @Test
    public void testfile7() {
        /*
        7
        U.S.A.    14  5  9    0 1 2 3 1 2 0
        England   12  3  7    1 0 2 1 2 1 0
        France    16  2  7    2 2 0 1 1 1 0
        Germany   13  3  5    3 1 1 0 0 0 0
        Ireland   11  3  5    1 2 1 0 0 1 0 << elimiated
        Belgium   12  4  7    2 1 1 0 1 0 2
        China     13  2  2    0 0 0 0 0 2 0
         */
        BaseballElimination be = new BaseballElimination("teams7.txt");
        be.isEliminated("Ireland");

    }

}