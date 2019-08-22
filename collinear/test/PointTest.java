package test;

import static junit.framework.TestCase.assertTrue;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class PointTest {

    @org.junit.Test
    public void testDoubleValue() {
        assertTrue(Double.NEGATIVE_INFINITY == Double.NEGATIVE_INFINITY);
        // System.out.println(Math.log);

    }

    /*
    n_inf, [..., 0, ...], p_inf
     -1           0         1
    p1     p2      p1 - p2
    n_inf  n_inf    0
    n_inf  p_inf   -2
    n_inf  range   -1
    p_inf  n_inf   +2
    p_inf  p_inf    0
    p_inf  range   +1
    range  n_inf   +1




     */
}