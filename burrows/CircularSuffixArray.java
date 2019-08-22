/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class CircularSuffixArray {

    private final Integer[] suffixes;

    // circular suffix array of s
    private String s ;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        this.s = s;
        final int len = s.length();
        Integer[] suffixes = new Integer[len];
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = i;  // index in origin suffix array
        }
        Arrays.sort(suffixes, (offsetA, offsetB) -> {
            // offset: start index of each suffix
            int diff = 0;
            for(int cnt = 0 ; cnt < len ; cnt ++) {
                int ia = (offsetA + cnt);
                if (ia >= len) {
                    ia -= len;
                }
                int ib = (offsetB + cnt);
                if (ib >= len) {
                    ib -= len;
                }

                diff = s.charAt(ia) - s.charAt(ib);
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        });

        this.suffixes = suffixes;
    }

    // length of s
    public int length() {
        return s.length();
    }

    /**
     * returns index at origin suffix of i-th sorted suffix
      * @param i index at sorted suffix
     * @return index at origin suffix of the given sorted suffix
     */
    public int index(int i) {
        if (i < 0 || i >= s.length()) {
            throw new IllegalArgumentException();
        }

        return suffixes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
